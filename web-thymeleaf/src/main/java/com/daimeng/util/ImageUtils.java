package com.daimeng.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class ImageUtils {
	
	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();
		File file = new File("D:/java_test/base64/7dbed655c1f2491fdcbb1d89bdf309ec_r.jpg");
		String base64 = file2Base64(file);
		base64ToFile(base64, "D:/java_test/base64/7dbed655c1f2491fdcbb1d89bdf309ec_r4444.jpg");
		long t2 = System.currentTimeMillis();
		Constants.println(t2-t1);
	}

	public static String file2Base64(File file) {
        if(file==null) {
            return null;
        }
        String base64 = null;
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);
            byte[] buff = new byte[fin.available()];
            fin.read(buff);
            base64 = Base64.encode(buff);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Constants.println(base64.length());
        return base64;
    }
	
	public static File base64ToFile(String base64,String filePath) {
        if(base64==null||"".equals(base64)) {
            return null;
        }
        byte[] buff=Base64.decode(base64);
        File file=null;
        FileOutputStream fout=null;
        try {
            file = new File(filePath);
            fout=new FileOutputStream(file);
            fout.write(buff);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fout!=null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }
}
