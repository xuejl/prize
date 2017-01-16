package com.aoshi.controller;

import Decoder.CharacterEncoder;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * ModelController
 *
 * @author zf
 * @date 2016/12/31
 */
@Controller
public class ModelController {

    Logger logger = LoggerFactory.getLogger(ModelController.class);

    private static final String ZIP_FILE_NAME = "model.zip";

    @RequestMapping("download")
    public String downLoadFile(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {

        String path = session.getServletContext().getRealPath("/") + File.separator + "test2"+"/";
        File file = getModelFile(session);
//        List<String> contents = getDataFromDataBase();

        List<List<String>> contents = getDataFromDataBase(path);

        String fileContent = getModelFileToString(file);
        fileContent = replaceModelTag(fileContent, contents);
        //生成模板新的html
        byte tag_bytes[] = fileContent.getBytes();
        // 根据"new"+模板名得到文件名。
//        String fileame = "new"+ file.getName().substring(0,file.getName().indexOf(".")) + ".html";
        String fileame =  file.getName().substring(0,file.getName().indexOf(".")) + ".html";
        // 生成的html文件保存路径。
        fileame = session.getServletContext().getRealPath("/") + File.separator + "test2" +"/"+ fileame;
        FileOutputStream fileoutputstream = new FileOutputStream(fileame);// 建立文件输出流
        fileoutputstream.write(tag_bytes);
        fileoutputstream.close();
        //复制文件
//        copyFolder(path,newPath);
        //GenZipFileInLocal(session, fileContent);

        //压缩zip文件
        fileToZip(session.getServletContext().getRealPath("/") + File.separator + "test2" ,
                session.getServletContext().getRealPath("/") + File.separator + "file", ZIP_FILE_NAME);
        try {
            getClientFile(response, ZIP_FILE_NAME, session);
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException" + "===> " + e.getMessage());
            e.printStackTrace();

        } catch (IOException e) {
            logger.error("IOException" + "===> " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 替换掉里面的标签
     * @param fileContent
     * @param contents
     */
    private String replaceModelTag(String fileContent, List<List<String>> contents) throws IOException {
        for (int i = 0; i < contents.size(); i++) {
            fileContent = fileContent.replace(String.format("{%s}", String.valueOf(i)), contents.get(i).get(0))//店铺图片
                                    .replace(String.format("[%s]", String.valueOf(i)), contents.get(i).get(1))//店名
                                    .replace(String.format("(%s)", String.valueOf(i)), contents.get(i).get(2))//经营项目
                                    .replace(String.format("<%s>", String.valueOf(i)), contents.get(i).get(3))//店铺位置
                                    .replace(String.format("{%s)", String.valueOf(i)), contents.get(i).get(4))//网址
//                                    .replace(String.format("(%s}", String.valueOf(i)), contents.get(i).get(5))//图片
                                    .replace(String.format("<%s}", String.valueOf(i)), contents.get(i).get(5));//背景
        }
        return fileContent;
    }


    /**
     * 下载文件到客户端
     * @param response
     */
    private void getClientFile(HttpServletResponse response, String fileName, HttpSession session) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
        File file = new File(session.getServletContext().getRealPath("/") + File.separator + "file" + File.separator + ZIP_FILE_NAME);
        InputStream inputStream = new FileInputStream(file);
        OutputStream os = response.getOutputStream();
        byte[] b = new byte[2048];
        int length;
        while ((length = inputStream.read(b)) > 0 ) {
            os.write(b, 0, length);
        }
        os.close();
        inputStream.close();
    }

    /**
     * 替换html的标签
     */
    private String getModelFileToString(File file) throws IOException {
        Assert.notNull(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();
    }

    /**
     * 产生zip包在本地
     * @param htmlTranslate
     * @throws IOException
     */
    private void GenZipFileInLocal(HttpSession session, final String htmlTranslate) throws IOException {
        File zipFile = new File(session.getServletContext().getRealPath("/") + File.separator +"file" + File.separator + ZIP_FILE_NAME);

        if (!zipFile.exists())
            zipFile.createNewFile();

        final ByteArrayInputStream is = new ByteArrayInputStream(htmlTranslate.getBytes());
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
        zipOutputStream.putNextEntry(new ZipEntry(zipFile.getName()));
        int temp ;
        while ((temp = is.read()) != -1) {
            zipOutputStream.write(temp);
        }
        is.close();
        zipOutputStream.close();

    }

    /**
     * 从数据库里面获取数据
     * @return
     */
    public List<List<String>> getDataFromDataBase(String path) {
        Map map = new HashMap();
        List list = new ArrayList();
        List list1 = new ArrayList();
        String url = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
//        String url2 = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
        String url3 = "http://pic64.nipic.com/file/20150414/19328392_141611475424_2.jpg";
        String img = downloadFromUrl(url, path);
//        String img2 = downloadFromUrl(url2, path);
        String img3 = downloadFromUrl(url3, path);
            list.add(img);
            list.add("店铺");
            list.add("经营项目");
            list.add("位置");
            list.add("https://www.baidu.com/");
//            list.add(img2);
            list.add(img3);

        for(int i=0;i<40;i++){
            list1.add(list);
        }
        return list1;
//        return new ArrayList<String>(){{
//            add("android移动开发");
//            add("IOS移动开发");
//            add("Html5前端开发");
//            String url = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
//            String img = downloadFromUrl(url, path);
//            add(img);
//        }};
    }

    /**
     * 获取模板文件
     * @return
     */
    public File getModelFile(HttpSession session) {
        String path = session.getServletContext().getRealPath("/") + File.separator;
        logger.debug("app", "path == > " + path);
        return new File(path + "model" + File.separator + "sdzs-01.html");
    }

    /**
     * 复制文件
     */
    public void copyFolder(String oldPath, String newPath) {

        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a=new File(oldPath);
            String[] file=a.list();
            File temp=null;
            for (int i = 0; i < file.length; i++) {
                if(oldPath.endsWith(File.separator)){
                    temp=new File(oldPath+file[i]);
                }
                else{
                    temp=new File(oldPath+File.separator+file[i]);
                }

                if(temp.isFile()){
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ( (len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if(temp.isDirectory()){//如果是子文件夹
                    copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]);
                }
            }
        }
        catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();

        }

    }

    public static boolean fileToZip(String sourceFilePath,String zipFilePath,String fileName){
        boolean flag = false;
        File sourceFile = new File(sourceFilePath);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        ZipOutputStream zos = null;

        if(sourceFile.exists() == false){
            System.out.println("待压缩的文件目录："+sourceFilePath+"不存在.");
        }else{
            try {
                File zipFile = new File(zipFilePath + "/" + fileName);
                if(zipFile.exists()){
                    System.out.println(zipFilePath + "目录下存在名字为:" + fileName +"打包文件.");
                }else{
                    File[] sourceFiles = sourceFile.listFiles();
                    if(null == sourceFiles || sourceFiles.length<1){
                        System.out.println("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");
                    }else{
                        fos = new FileOutputStream(zipFile);
                        zos = new ZipOutputStream(new BufferedOutputStream(fos));
                        byte[] bufs = new byte[1024*10];
                        for(int i=0;i<sourceFiles.length;i++){
                            //创建ZIP实体，并添加进压缩包
                            ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
                            zos.putNextEntry(zipEntry);
                            //读取待压缩的文件并写进压缩包里
                            fis = new FileInputStream(sourceFiles[i]);
                            bis = new BufferedInputStream(fis, 1024*10);
                            int read = 0;
                            while((read=bis.read(bufs, 0, 1024*10)) != -1){
                                zos.write(bufs,0,read);
                            }
                        }
                        flag = true;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally{
                //关闭流
                try {
                    if(null != bis) bis.close();
                    if(null != zos) zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
        return flag;
    }

    private String downloadFromUrl(String url,String dir) {
        String fileName = "";
        try {
            URL httpurl = new URL(url);
             fileName = getFileNameFromUrl(url);
            File f = new File(dir + fileName);
            FileUtils.copyURLToFile(httpurl, f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    private String getFileNameFromUrl(String url){
        String name = new Long(System.currentTimeMillis()).toString() + ".X";
        int index = url.lastIndexOf("/");
        if(index > 0){
            name = url.substring(index + 1);
            if(name.trim().length()>0){
                return name;
            }
        }
        return name;
    }
}
