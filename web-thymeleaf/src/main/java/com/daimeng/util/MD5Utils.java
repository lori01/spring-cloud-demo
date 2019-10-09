package com.daimeng.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

public class MD5Utils {


	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		String md51 = getMD5One("D:/Picture/HP/5a1f9f4fe323e.jpg");
		String md52 = getMD5Two("D:/Picture/HP/5a1f9f4fe323e.jpg");
		String md53 = getMD5Two("D:/Picture/HP/5a1f9f4fe323e.jpg");
		String md54 = DigestUtils.md5Hex(new FileInputStream(
				"D:/Picture/HP/5a1f9f4fe323e.jpg"));
		System.out.println(md51);
		System.out.println(md52);
		System.out.println(md53);
		System.out.println(md54);
		System.out.println(md51.length());
		System.out.println(md52.length());
		System.out.println(md53.length());
		System.out.println(md54.length());
	}

	private final static String[] strHex = { "0", "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public static String getMD5One(String path) {
		StringBuffer sb = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] b = md.digest(FileUtils.readFileToByteArray(new File(path)));
			for (int i = 0; i < b.length; i++) {
				int d = b[i];
				if (d < 0) {
					d += 256;
				}
				int d1 = d / 16;
				int d2 = d % 16;
				sb.append(strHex[d1] + strHex[d2]);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static String getMD5Two(String path) {
		StringBuffer sb = new StringBuffer("");
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(FileUtils.readFileToByteArray(new File(path)));
			byte b[] = md.digest();
			int d;
			for (int i = 0; i < b.length; i++) {
				d = b[i];
				if (d < 0) {
					d = b[i] & 0xff;
					// 与上一行效果等同
					// i += 256;
				}
				if (d < 16)
					sb.append("0");
				sb.append(Integer.toHexString(d));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static String getMD5Three(String path) {
		BigInteger bi = null;
		try {
			byte[] buffer = new byte[8192];
			int len = 0;
			MessageDigest md = MessageDigest.getInstance("MD5");
			File f = new File(path);
			FileInputStream fis = new FileInputStream(f);
			while ((len = fis.read(buffer)) != -1) {
				md.update(buffer, 0, len);
			}
			fis.close();
			byte[] b = md.digest();
			bi = new BigInteger(1, b);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bi.toString(16);
	}

}
