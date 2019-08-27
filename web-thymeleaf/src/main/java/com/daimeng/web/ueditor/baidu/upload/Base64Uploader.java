package com.daimeng.web.ueditor.baidu.upload;

import com.daimeng.web.ueditor.baidu.PathFormat;
import com.daimeng.web.ueditor.baidu.define.AppInfo;
import com.daimeng.web.ueditor.baidu.define.BaseState;
import com.daimeng.web.ueditor.baidu.define.FileType;
import com.daimeng.web.ueditor.baidu.define.State;
import org.apache.commons.codec.binary.Base64;

import java.util.Map;

public final class Base64Uploader {

	public static State save(String content, Map<String, Object> conf) {
		
		byte[] data = decode(content);

		long maxSize = ((Long) conf.get("maxSize")).longValue();

		if (!validSize(data, maxSize)) {
			return new BaseState(false, AppInfo.MAX_SIZE);
		}

		String suffix = FileType.getSuffix("JPG");

		String savePath = PathFormat.parse((String) conf.get("savePath"),
				(String) conf.get("filename"));
		
		savePath = savePath + suffix;
		
		//百度缺陷，此处是bashPath，不是rootPath
		//bashPath:C:/Users/Sephy/AppData/Local/Temp/tomcat-docbase.6279166846519789172.80/
		//rootPath:D:/java_test/upload/
		//String physicalPath = (String) conf.get("rootPath") + savePath;
		System.out.println(conf.get("rootPath"));
		System.out.println(conf.get("basePath"));
		String physicalPath = (String) conf.get("basePath") + savePath;

		State storageState = StorageManager.saveBinaryFile(data, physicalPath);

		if (storageState.isSuccess()) {
			storageState.putInfo("url", PathFormat.format(savePath));
			storageState.putInfo("type", suffix);
			storageState.putInfo("original", "");
		}

		return storageState;
	}

	private static byte[] decode(String content) {
		return Base64.decodeBase64(content);
	}

	private static boolean validSize(byte[] data, long length) {
		return data.length <= length;
	}
	
}