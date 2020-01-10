package com.chengxuxiaoba.video.util;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletResponse;
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
     *
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

    public static void download(String path, HttpServletResponse response) {
        if (response == null) {
            return;
        }

        InputStream fis = null;
        OutputStream toClient = null;
        try {
            File file = new File(path);

            String filename = file.getName();

            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (toClient != null) {
                try {
                    toClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
