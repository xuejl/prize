package com.aoshi.controller;

import com.aoshi.dao.*;
import com.aoshi.domain.CalNumRecord;
import com.aoshi.domain.CalNumSet;
import com.aoshi.domain.CalPrize;
import com.aoshi.domain.CalPrizeLevel;
import com.aoshi.util.UuidUtil;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * CalController
 * http://localhost:8080/redRain/app/goToSetCalNum
 *
 * @author zf
 * @date 10/27/16
 */
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST})
@Controller()
@RequestMapping("app")
public class CalController {
    @Autowired
    CalNumSetMapper calNumSetMapper;

    @Autowired
    CalNumRecordMapper calNumRecordMapper;

    @Autowired
    CalPrizeMapper calPrizeMapper;

    @Autowired
    CalPrizeLevelMapper calPrizeLevelMapper;

    @CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST})
    @RequestMapping("getRandomCode")
    @ResponseBody
    @Transactional
    public Object getRandomCode(Integer calNumId, Integer prizeId) {
        CalPrize calPrize = calPrizeMapper.selectByPrimaryKey(prizeId);
        Map<String, Object> data = new HashMap<>();
        if(calPrize!=null){
            if (calPrize.getRemainTime() <= 0) {
                data.put("errorCode", 1001);
                data.put("errorMsg", "该奖品已经被抽完");
                return data;
            }
            CalNumSet calNumSet = calNumSetMapper.selectByPrimaryKey(calPrize.getNumSetId());
            if(calNumSet!=null){
                //中奖的记录
                List<CalNumRecord> calNumRecords = calNumRecordMapper.selectAll(calNumSet.getNumSetId());
                //移除已中奖的
                List<Integer> allRecords = removePrizeNum(calNumSet, calNumRecords);
                int len = allRecords.size();
                Set<Object> list = new HashSet<Object>();
                Random random = new Random();
                if(calNumSet.getIsreject().equals(BoolCodeEnum.YES.toCode())){
                    //剔除尾号4
                    for(int i=0;i<allRecords.size();i++){
                        if (allRecords.get(i) %10 ==4){
                            allRecords.remove(i);
                        }
                    }
                    int len2 =len-allRecords.size();//计算剔除后的list大小
                    if (len-len2 == 0) {
                        data.put("errorCode", 1001);
                        data.put("errorMsg", "活动已经结束");
                        return data;
                    }
                }else {
                    //不剔除尾号4
                    if (len == 0) {
                        data.put("errorCode", 1001);
                        data.put("errorMsg", "活动已经结束");
                        return data;
                    }
                }
                Object[] array = allRecords.toArray();
                //未中奖的数组剩余的次数小于等于一次抽的次数，取未中奖的数组剩余的次数，否则取一次抽的次数
                if(allRecords.size()<=calNumSet.getTime()){
                    // 生成随机数字并存入list（从移除已中奖的数组之间产生随机数，取一次抽奖的次数）
                    for(int i=0;i<array.length;i++){
                        for(CalNumRecord record :calNumRecords){
                            if(record.getRecordNum() !=array[i]){
                                list.add(array[i]);
                            }
                        }
                    }
                }else {
                    // 生成随机数字并存入list（从移除已中奖的数组之间产生随机数，取一次抽奖的次数）
                    while (list.size()<calNumSet.getTime()){
                        int arrIdx = random.nextInt(array.length);
                        Object number =array[arrIdx];
                        if(!calNumRecords.isEmpty()){
                            for(CalNumRecord record :calNumRecords){
                                if(record.getRecordNum() !=number){
                                    list.add(number);
                                }
                            }
                        }else {
                            list.add(number);
                        }

                    }
                }
                //保存中奖记录
                Iterator<Object> it = list.iterator();
                while (it.hasNext()) {
                    CalNumRecord record = new CalNumRecord();
                    record.setRecordNum((Integer)it.next());
                    record.setPrizeId(prizeId);
                    record.setNumSetId(calNumSet.getNumSetId());
                    calNumRecordMapper.insert(record);
                }
                //更新剩余次数
                calPrize.setRemainTime(calPrize.getRemainTime() - 1);
                calPrizeMapper.updateByPrimaryKeySelective(calPrize);
                data.put("successCode", 1001);
                String type = calNumSet.getLotteryType().equals("0") ? "台号" : "个人号";
                data.put("successMsg", "中奖号码为："+ type + list);
            }
        }
        return data;
    }

    private void createCode(Integer calNumId, Integer prizeId, List<Integer> allRecords, CalPrize calPrize, Map<String, Object> data, List<String> codes) {
        Integer code = RandomUtils.nextInt(allRecords.size());
        code = allRecords.get(code);
        CalNumRecord record = new CalNumRecord();
        record.setRecordNum(code);
        record.setPrizeId(prizeId);
        record.setNumSetId(calNumId);
        calNumRecordMapper.insert(record);
        calPrize.setRemainTime(calPrize.getRemainTime() - 1);
        calPrizeMapper.updateByPrimaryKeySelective(calPrize);
        codes.add(String.format("%04d", code));
    }

    @CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST})
    @RequestMapping("getAllRecords")
    @ResponseBody
    @Transactional
    public Object getAllRecords() {
        CalNumSet calNumSet = calNumSetMapper.selectByPrimaryKey(4);
        List<CalNumRecord> calNumRecords = calNumRecordMapper.selectAll(0);

        List<Integer> allRecords = removePrizeNum(calNumSet, calNumRecords);
        return allRecords;
    }

    @CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST})
    @RequestMapping("addPrizeLevel")
    @ResponseBody
    @Transactional
    public void addPrizeLevel(String prizeLevelName) {
        calPrizeLevelMapper.insert(new CalPrizeLevel().setPrizeLevelName(prizeLevelName));
        return;
    }

    /**
     * 产生幸运码
     *
     * @param calNumId
     * @param prizeId
     * @param allRecords
     * @param calPrize
     * @return
     */
    private Integer genLuckCode(Integer calNumId, Integer prizeId, List<Integer> allRecords, CalPrize calPrize) {
        Integer code = RandomUtils.nextInt(allRecords.size());
        code = allRecords.get(code);
        CalNumRecord record = new CalNumRecord();
        record.setRecordNum(code);
        record.setPrizeId(prizeId);
        record.setNumSetId(calNumId);
        calNumRecordMapper.insert(record);
        allRecords.remove(code);
        calPrize.setRemainTime(calPrize.getRemainTime() - 1);
        calPrizeMapper.updateByPrimaryKeySelective(calPrize);
        return code;
    }

    /**
     * 移除中奖号码
     *
     * @param calNumSet
     * @param calNumRecords
     * @return
     */
    private List<Integer> removePrizeNum(CalNumSet calNumSet, List<CalNumRecord> calNumRecords) {
        List<Integer> allRecords = new ArrayList<>();
        for (int i = calNumSet.getMinNum(); i <= calNumSet.getMaxNum(); i++) {
            allRecords.add(i);
        }
        for (CalNumRecord calNumRecord : calNumRecords) {
            if (allRecords.contains(calNumRecord.getRecordNum())) {
                allRecords.remove(calNumRecord.getRecordNum());
            }
        }
        return allRecords;
    }


    @RequestMapping("goToSetCalNum")
    public String goToSetCalNum(ModelMap map) {
        List<CalNumSet> cals = calNumSetMapper.selectAll();
        map.put("data", cals);
        return "cal/setCalNum";
    }

    @Transactional
    @RequestMapping("numSave")
    public String numSave(ModelMap map, Integer maxNum0, Integer minNum0, Integer maxNum1, Integer minNum1) {
        CalNumSet calNumSet = new CalNumSet();
        calNumSet.setNumSetId(1);
        calNumSet.setMaxNum(maxNum0);
        calNumSet.setMinNum(minNum0);

        CalNumSet calNumSet2 = new CalNumSet();
        calNumSet2.setNumSetId(2);
        calNumSet2.setMaxNum(maxNum1);
        calNumSet2.setMinNum(minNum1);

        calNumSetMapper.updateByPrimaryKey(calNumSet2);
        calNumSetMapper.updateByPrimaryKey(calNumSet);
        map.put("data", calNumSetMapper.selectAll());
        map.put("alert1", "alert('保存成功')");
        return "cal/setCalNum";
    }

    /**
     * 获取所有的奖品等级
     *
     * @return
     */
    @CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST})
    @RequestMapping("getPrizeLevel")
    @ResponseBody
    public Map<String, Object> getPrizeLevel() {
        List<CalPrizeLevel> calPrizeLevels = calPrizeLevelMapper.selectAll();
        Map<String, Object> data = new HashMap<>();
        data.put("errorCode", 200);
        data.put("data", calPrizeLevels);
        return data;
    }


    /**
     * 根据奖品等级获取奖品
     *
     * @param prizeLevelId
     * @return
     */
    @CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST})
    @RequestMapping("getPrizeByLevelId")
    @ResponseBody
    public Map<String, Object> getPrizeByLevelId(Integer prizeLevelId) {
        List<CalPrize> calPrizes = calPrizeMapper.selectByLevelId(prizeLevelId);
        Map<String, Object> data = new HashMap<>();
        data.put("errorCode", 200);
        data.put("data", calPrizes);
        return data;
    }

    /**
     * 根据奖品等级获取奖品
     *
     * @param prizeId
     * @return
     */
    @CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST})
    @RequestMapping("getPrizeById")
    @ResponseBody
    public Map<String, Object> getPrizeById(Integer prizeId) {
        CalPrize calPrizes = calPrizeMapper.selectByPrimaryKey(prizeId);
        Map<String, Object> data = new HashMap<>();
        data.put("errorCode", 200);
        data.put("data", calPrizes);
        return data;
    }

    /**
     * 根据奖品等级获取奖品
     *
     * @return
     */
    @CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST})
    @RequestMapping("getCalNumSet")
    @ResponseBody
    public Map<String, Object> getCalNumSet() {
        List<CalNumSet> calNumSets = calNumSetMapper.selectAll();
        List<CalNumSet> calNumSetsShow = new ArrayList<>();
        for(CalNumSet c:calNumSets){
            String str = "一次抽" + c.getTime().toString() + "，最大值" + c.getMaxNum().toString() + "，最小值" + c.getMinNum().toString();
            c.setStr(str);
            calNumSetsShow.add(c);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("errorCode", 200);
        data.put("data", calNumSetsShow);
        return data;
    }

    @CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST})
    @RequestMapping("delNumSet")
    @ResponseBody
    public void delNumSet(Integer numSetId) {
        calNumSetMapper.deleteByPrimaryKey(numSetId);
    }

    @CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST})
    @RequestMapping("addNumSet")
    @ResponseBody
    public void addNumSet(CalNumSet numSet) {
        calNumSetMapper.insertSelective(numSet);
    }

    /**
     * 获取中奖列表
     *
     * @param
     * @return
     */
    @CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST})
    @RequestMapping("getPrizeList")
    @ResponseBody
    public List<Map<String, Object>> getPrizeList(ModelMap map) {
        List<Map<String, Object>> records = calNumRecordMapper.selectRecordsAll();
        return records;
    }

    /**
     * 重置
     *
     * @param
     * @return
     */
    @CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST})
    @RequestMapping("resetData")
    @ResponseBody
    public Map<String, Object> resetData() {
        calNumRecordMapper.cleanTable();
        for (int i = 0; i < 1512; i++) {
            String index = String.valueOf(i);
            if (index.substring(index.length() - 1, index.length()).equals("4")) {
                CalNumRecord calNumRecord = new CalNumRecord();
                calNumRecord.setPrizeId(0);
                calNumRecord.setRecordNum(i);
                calNumRecord.setNumSetId(0);
                calNumRecordMapper.insert(calNumRecord);
            }
        }
        calPrizeMapper.updateAllData();
        Map<String, Object> data = new HashMap<>();
        data.put("errorCode", 200);
        return data;
    }

    /**
     * 添加抽奖的计算num
     *
     * @param
     * @return
     */
    @CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST})
    @RequestMapping("addCalNum")
    @ResponseBody
    public Map<String, Object> addCalNumRecord(CalNumSet calNumSet) {
        if (calNumSet.getNumSetId() == null) {
            calNumSetMapper.insert(calNumSet);
        } else {
            calNumSetMapper.updateByPrimaryKey(calNumSet);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("errorCode", 200);
        return data;
    }

    /**
     * 获取抽奖次数
     *
     * @param id 删除抽奖号码
     * @return
     */
    @CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST})
    @RequestMapping("deleteCalNumRecord")
    @ResponseBody
    public Map<String, Object> deleteCalNulRecord(@RequestParam(value = "id[]") List<Integer> id) {
        for (Integer i : id) {
            calNumSetMapper.deleteByPrimaryKey(i);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("errorCode", 200);
        return data;
    }

    /**
     * 获取抽奖的方式.
     *
     * @return
     */
    @CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST})
    @RequestMapping("selectCalNum")
    @ResponseBody
    public Map<String, Object> selectCalNum() {
        Map<String, Object> data = new HashMap<>();
        data.put("errorCode", 200);
        data.put("data", calNumSetMapper.selectAll());
        return data;
    }

    /**
     * 获取奖品列表
     *
     * @return
     */
    @CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST})
    @RequestMapping("prizeList")
    @ResponseBody
    public Map<String, Object> prizeList() {
        Map<String, Object> data = new HashMap<>();
        data.put("errorCode", 200);
        data.put("data", calPrizeMapper.selectAll());
        return data;
    }

    /**
     * 添加抽奖的计算num
     *
     * @param
     * @return
     */
    @CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST})
    @RequestMapping("addPrize")
    @ResponseBody
    public Map<String, Object> addPrize(CalPrize prize) {
        if (prize.getPrizeId() == null) {
            calPrizeMapper.insert(prize);
        } else {
            calPrizeMapper.updateByPrimaryKey(prize);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("errorCode", 200);
        return data;
    }

    /**
     * 获取抽奖次数
     *
     * @param id 删除抽奖号码
     * @return
     */
    @CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST})
    @RequestMapping("delPrize")
    @ResponseBody
    public Map<String, Object> delPrize(@RequestParam(value = "id[]") List<Integer> id) {
        for (Integer i : id) {
            calPrizeMapper.deleteByPrimaryKey(i);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("errorCode", 200);
        return data;
    }

    /**
     * 获取抽奖次数
     *
     * @param id 删除抽奖号码
     * @return
     */
    @CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST})
    @RequestMapping("delPrizeLevel")
    @ResponseBody
    public Map<String, Object> delPrizeLevel(@RequestParam(value = "id") Integer id) {
        calPrizeLevelMapper.deleteByPrimaryKey(id);
        Map<String, Object> data = new HashMap<>();
        data.put("errorCode", 200);
        return data;
    }

    //新增或修改奖品
    @RequestMapping(value = "testKeep", produces = { "application/json" }, method =RequestMethod.POST)
    @ResponseBody
    public String testKeep(CalPrize calPrize, @RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request) {
        String fileName = UuidUtil.get32UUID() + ".jpg";;
        String path=request.getSession().getServletContext().getRealPath("upload");

        try {
                File targetFile = new File(path, fileName);
                //保存
                try {
                    file.transferTo(targetFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        calPrize.setPrizeimg("http://"+request.getRemoteAddr() +":" +request.getServerPort() +request.getContextPath() + File.separator + "upload" + File.separator + fileName);
        calPrize.setPrizeimg(request.getRequestURL().toString().replace(request.getRequestURI(), "")+request.getContextPath() + File.separator + "upload" + File.separator + fileName);
        if (calPrize.getPrizeId() == null) {
            calPrizeMapper.insertSelective(calPrize);
        } else {
            calPrizeMapper.updateByPrimaryKey(calPrize);
        }
        return "";
    }

    /**
     * 获取抽奖次数
     *
     * @param file 删除抽奖号码
     * @return
     */
    @CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST})
    @RequestMapping(value = "uploadFile", produces = { "application/json" }, method =RequestMethod.POST)
    @ResponseBody
    public String uploadFile(MultipartFile file, HttpServletRequest request ) {
        String fileName = "index.jpg";
        String path=request.getSession().getServletContext().getRealPath("upload");

        try {
                File indexFile = new File(path, fileName);
                if (indexFile.exists()) {
                    indexFile.delete();
                }
                //保存
                try {
                    file.transferTo(indexFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "200";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return null;
        }

    }
}
