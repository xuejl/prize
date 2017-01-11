package com.aoshi.controller;

import com.aoshi.dao.CalNumRecordMapper;
import com.aoshi.dao.CalNumSetMapper;
import com.aoshi.dao.CalPrizeLevelMapper;
import com.aoshi.dao.CalPrizeMapper;
import com.aoshi.domain.CalNumRecord;
import com.aoshi.domain.CalNumSet;
import com.aoshi.domain.CalPrize;
import com.aoshi.domain.CalPrizeLevel;
import com.aoshi.util.Const;
import com.aoshi.util.FtpConManager;
import com.aoshi.util.PropertyUtils;
import com.aoshi.util.UuidUtil;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        CalNumSet calNumSet = calNumSetMapper.selectByPrimaryKey(calNumId);
        List<CalNumRecord> calNumRecords = calNumRecordMapper.selectAll(calNumId);

        List<Integer> allRecords = removePrizeNum(calNumSet, calNumRecords);

        if (allRecords.size() == 0) {
            Map<String, Object> data = new HashMap<>();
            data.put("errorCode", 1001);
            data.put("errorMsg", "活动已经结束");
            return data;
        }
        CalPrize calPrize = calPrizeMapper.selectByPrimaryKey(prizeId);
        if (calPrize.getRemainTime() <= 0) {
            Map<String, Object> data = new HashMap<>();
            data.put("errorCode", 1001);
            data.put("errorMsg", "该奖品已经被抽完");
            return data;
        }
        Map<String, Object> data = new HashMap<>();
        List<String> codes = new ArrayList<>();
        if (calNumId == 1) {
            if (calPrize.getRemainTime() > 4) {
                int count = calPrize.getRemainTime();
                for (int i = 0; i < count; i++) {
                    createCode(calNumId, prizeId, allRecords, calPrize, data, codes);
                }
            } else {
                createCode(calNumId, prizeId, allRecords, calPrize, data, codes);

            }
            data.put("errorMsg", codes);
            data.put("errorCode", 200);
        } else {
            List<Integer> luckCode = new ArrayList<>();
//            抽取30桌
            if (prizeId == 11) {
                for (int i = 0; i < 30; i++) {
                    Integer code = genLuckCode(calNumId, prizeId, allRecords, calPrize);
                    luckCode.add(code);
                }

                data.put("errorCode", 200);
                data.put("errorMsg", luckCode);
//                抽取40桌
            } else if (prizeId == 12) {
                for (int i = 0; i < 20; i++) {
                    Integer code = genLuckCode(calNumId, prizeId, allRecords, calPrize);
                    luckCode.add(code);
                }
                data.put("errorCode", 200);
                data.put("errorMsg", luckCode);
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
        CalNumSet calNumSet = calNumSetMapper.selectByPrimaryKey(1);
        List<CalNumRecord> calNumRecords = calNumRecordMapper.selectAll(1);

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
     * @param prizeLevelId
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
        Map<String, Object> data = new HashMap<>();
        data.put("errorCode", 200);
        data.put("data", calNumSets);
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

    @RequestMapping(value = "testKeep", produces = { "application/json" }, method =RequestMethod.POST)
    @ResponseBody
    public String testKeep(CalPrize calPrize, @RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request) {
        boolean flag = false;
        String ftpFile = UuidUtil.get32UUID() + ".jpg";;
        String ftpDir = Const.LOGOFILEPATHIMG;
        if(!StringUtils.isEmpty(file.getOriginalFilename())){
            try {
                flag = ftpUpload(file.getBytes(), ftpFile,ftpDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(flag){
            calPrize.setPrizeimg(ftpDir+"/"+ftpFile);
        }
        return null;
    }

    private boolean ftpUpload(byte[] bytes,String ftpFile,String ftpDir){
        String path = Const.FILEPATHIMGDIR + ftpDir;
        //byte[] bytes = Base64.decode(goodsImg);

        FtpConManager ftpConManager = FtpConManager.getInstance();
        try {
            ftpConManager.login(PropertyUtils.getFTP_URL(),
                    PropertyUtils.getFTP_USERNAME(),
                    PropertyUtils.getFTP_PASS());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ftpConManager.uploadFileByInputStream(new ByteArrayInputStream(bytes), path, ftpFile);
    }
}

    /**
     * 获取抽奖次数
     *
     * @param file 删除抽奖号码
     * @return
     */
    @CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST})
    @RequestMapping("uploadFile")
    @ResponseBody
    public Map<String, Object> uploadFile(MultipartFile file) {
        return new HashMap<String, Object>(){{
            put("a","a");
        }};

    }

}
