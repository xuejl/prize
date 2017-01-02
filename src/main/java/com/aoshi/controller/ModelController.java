package com.aoshi.controller;

import Decoder.CharacterEncoder;
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
import java.util.ArrayList;
import java.util.List;
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
        File file = getModelFile(session);
        List<String> contents = getDataFromDataBase();
        String fileContent = getModelFileToString(file);
        fileContent = replaceModelTag(fileContent, contents);
        GenZipFileInLocal(session, fileContent);
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
    private String replaceModelTag(String fileContent, List<String> contents) {
        for (int i = 0; i < contents.size(); i++) {
            fileContent = fileContent.replace(String.format("[%s]", String.valueOf(i+1)), contents.get(i));
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
        String line = "";
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
    public List<String> getDataFromDataBase() {
        return new ArrayList<String>(){{
            add("android移动开发");
            add("IOS移动开发");
            add("Html5前端开发");
        }};
    }

    /**
     * 获取模板文件
     * @return
     */
    public File getModelFile(HttpSession session) {
        String path = session.getServletContext().getRealPath("/") + File.separator;
        logger.debug("app", "path == > " + path);
        return new File(path + "model" + File.separator + "modelA.html");
    }
}
