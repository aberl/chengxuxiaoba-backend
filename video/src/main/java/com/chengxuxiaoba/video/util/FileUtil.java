package com.chengxuxiaoba.video.util;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;

public class FileUtil {
    /**
     * check directory path is exist or not
     *
     * @param dirPath
     * @param isCreate, if this parameter is true, and the directory is not exist. then create one new direcotry
     * @return
     */
    public static Boolean checkDirExist(String dirPath, Boolean isCreate) {
        File file = new File(dirPath);

        if (file.exists())
            return true;

        if (isCreate)
            file.mkdirs();

        return false;
    }

    public static String getSuffixName(String fileName) {
        String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1);

        return suffixName;
    }

    public static String getSuffixName(File file) {
        if (file == null)
            return null;

        String fileName = file.getName();

        return getSuffixName(fileName);
    }

    public static Boolean uploadFile(InputStream inputStream, String filePath, String fileName) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath + "/" + fileName);
            byte buffer[] = new byte[1024];

            int length = 0;

            while ((length = inputStream.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            //关闭输入流
            inputStream.close();
            //关闭输出流
            fos.close();

            return true;
        } catch (Exception ex) {
            return false;
        } finally {
            if (fos != null)
                fos.close();
        }
    }

    /**
     * convert MultipartFile to File, please config one multipartResolver object
     *
     * @param multipartFile
     * @return
     */
    public static File convertToFile(MultipartFile multipartFile) {
        if (multipartFile == null)
            return null;

        DiskFileItem diskFileItem = (DiskFileItem) ((CommonsMultipartFile) multipartFile).getFileItem();

        File file = diskFileItem.getStoreLocation();

        return file;
    }

    public static Boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists())
            return false;

        file.delete();
        return true;
    }

    /**
     * get directory path from file name
     * @param fileName
     * @return
     */
    public static String getDirectoryPath(String fileName) {
        try {
            Integer _index = fileName.lastIndexOf("/");

            if (_index < 0)
                _index = fileName.lastIndexOf("\\");

            if (_index < 0)
                return null;

            String path = fileName.substring(0, _index);
            return path;

        } catch (Exception ex) {

        }
        return null;
    }
}
