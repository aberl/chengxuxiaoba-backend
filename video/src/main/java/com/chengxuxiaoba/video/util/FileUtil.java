package com.chengxuxiaoba.video.util;

import java.io.File;

public class FileUtil {
	/**
	 * check directory path is exist or not
	 * @param dirPath
	 * @param isCreate, if this parameter is true, and the directory is not exist. then create one new direcotry
	 * @return
	 */
	public static Boolean checkDirExist(String dirPath, Boolean isCreate)
	{
		File file =new File(dirPath);
		
		if(file.exists())
			return true;
		
		if(isCreate)
			file.mkdirs();
		
		return false;
	}
}
