package com.thinkgem.jeesite.common.utils;
import java.io.File;
import java.util.Calendar;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

public class CommonPath {

	public static String DEFAULT_SAVE_PATH = null;
	
	static{
		
		//不是放"C:/litu";而是放项目发布目录的相对目录下面
		//String realPath = "C:/app";//ServletActionContext.getRequest().getSession().getServletContext().getRealPath("");
		
//		
//					final String RESOURCES_IMAGE_PATH ="/resource/litu_uploaded_iamges";
//											Calendar cal = Calendar.getInstance();
//											String generatePath = cal.get(Calendar.YEAR) 
//													+ "/" + (cal.get(Calendar.MONTH) + 1)
//													+ "/" + cal.get(Calendar.DAY_OF_MONTH);
//					String destFolder = RESOURCES_IMAGE_PATH + "/" + generatePath;
//		String fullFileDirectory = realPath + destFolder + "/";
//		DEFAULT_SAVE_PATH = fullFileDirectory;
//		
//		//创建一个文件进去,主要是为了创建这个目录
//		File dest = new File(fullFileDirectory + UUID.randomUUID().toString());
//		if (!dest.isDirectory())  dest.getParentFile().mkdirs();
		
		//		if (!dest.getParentFile().isDirectory())  dest.getParentFile().mkdirs();
		//		try {
		//			if (!dest.isFile())dest.createNewFile();
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		}
	}

	private static boolean inited = false;
	private static String DEST_FOLDER = null;
	public static String getDestFolder(HttpServletRequest request) {
		if(!inited){
			//String realPath = request.getSession().getServletContext().getRealPath("");
			String path = request.getContextPath();
			//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + path + "/";
			
			//统一访问地址：http://112.74.84.69:8180/litu/resource/images/2015/08/26/a.jpg
			final String RESOURCES_IMAGE_PATH ="/resource/images";//这里不能以app为前缀
			Calendar cal = Calendar.getInstance();
			String generatePath = cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH);
			DEST_FOLDER = RESOURCES_IMAGE_PATH + "/" + generatePath;
			String fullFileDirectory = path + DEST_FOLDER + "/";
			//创建一个文件进去,主要是为了创建这个目录
			File dest = new File(fullFileDirectory + UUID.randomUUID().toString());
			if (!dest.isDirectory())  dest.getParentFile().mkdirs();
			inited = true;
		}
		return DEST_FOLDER;
	}


}
