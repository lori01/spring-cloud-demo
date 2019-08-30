package com.daimeng.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

public class WaterMarkUtils {

	private static int FONT_DEF_SIZE = 22;
	private static int PIC_DEF_WIDTH = 500;
	
	public static void main(String[] args) {
        //Font font = new Font("微软雅黑", Font.PLAIN, 50);                     //水印字体
        String srcImgPath="D:/java_test/picture/金圆通海报.png"; //源图片地址
        //String tarImgPath="D:/watermark_test/88_1_wm.png"; //待存储的地址
        String waterMarkContent="亲爱的乌云元：\n        您好！\n        今日是您的生日！\n        金圆集团特此献上美好的祝福！\n        祝您生日快乐！\n此致！\n         敬礼！";  //水印内容
        //Color color=new Color(255,255,255,255);                               //水印图片色彩以及透明度
        //new WaterMarkUtils().addWaterMark(srcImgPath, tarImgPath, waterMarkContent, null,null);
        Constants.println(WaterMarkUtils.addWaterMark(srcImgPath, waterMarkContent));

    }
	/**
	 * 
	* @功能描述: 添加水印
	* @方法名称: addWaterMark 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月25日 下午2:26:00 
	* @version V1.0   
	* @param srcImgPath
	* @param waterMarkContent
	* @return 
	* @return String
	 */
	public static String addWaterMark(String srcImgPath, String waterMarkContent){
		return addWaterMark(srcImgPath, null, waterMarkContent, null, null);
	}
	
	/**
	 * 
	* @功能描述: TODO 
	* @方法名称: addWaterMark 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月25日 下午2:22:18 
	* @version V1.0   
	* @param srcImgPath 源图片路径
	* @param tarImgPath 保存的图片路径  如果为空,则保存在srcImgPath的目录下面
	* @param waterMarkContent 水印内容
	* @param markContentColor 水印颜色
	* @param font 水印字体
	* @return 
	* @return String
	 */
    public static String addWaterMark(String srcImgPath, String tarImgPath, String waterMarkContent,Color markContentColor,Font font) {

        try {
        	// 读取原图片信息
        	File srcImgFile = new File(srcImgPath);//得到文件
            Image srcImg = ImageIO.read(srcImgFile);//文件转化为图片
            int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
            int srcImgHeight = srcImg.getHeight(null);//获取图片的高
            
            int size = FONT_DEF_SIZE;
            if(markContentColor == null){
        		markContentColor=new Color(100,100,100);
        	}
        	if(font == null){
        		size = srcImgWidth * FONT_DEF_SIZE / PIC_DEF_WIDTH;
        		Constants.println(size);
        		font = new Font("微软雅黑", Font.PLAIN, size);
        	}
        	String suffix = srcImgPath.substring(srcImgPath.lastIndexOf(".")+1, srcImgPath.length());
        	if(tarImgPath == null || "".equals(tarImgPath)){
        		tarImgPath = srcImgPath.substring(0, srcImgPath.lastIndexOf(".")) + "_wm_" + System.currentTimeMillis() + "." + suffix;
        	}
        	//Constants.println(suffix);
        	//Constants.println(tarImgPath);
            
            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            g.setColor(markContentColor); //根据图片的背景设置水印颜色
            g.setFont(font);              //设置字体

            //设置水印的坐标
            //右下角水印
            //int x = srcImgWidth - 2*getWatermarkLength(waterMarkContent, g);  
            //int y = srcImgHeight - 2*getWatermarkLength(waterMarkContent, g);
            
            //居中
            waterMarkContent = HtmlUtils.replaceEnter(waterMarkContent);
            String[] waterMarkContents = waterMarkContent.split("<br>");
            /*int maxLeng = 0;
            for(String msg : waterMarkContents){
            	if(msg.length() > maxLeng){
            		maxLeng = msg.length();
            		int noEnLeng = msg.replace(" ", "").length();
            		maxLeng = maxLeng - noEnLeng/2;
            	}
            }
            int x = (srcImgWidth - font.getSize() * maxLeng) / 2;*/  
            int x = srcImgWidth / 6;  
            int y = srcImgHeight / 7;  
            
            for(String msg : waterMarkContents){
            	g.drawString(msg, x, y);  //画出水印
            	y += (size + 10);
            }
            
            g.dispose();  
            // 输出图片  
            FileOutputStream outImgStream = new FileOutputStream(tarImgPath);  
            ImageIO.write(bufImg, suffix, outImgStream);
            Constants.println("添加水印完成");  
            outImgStream.flush();  
            outImgStream.close();  
            return tarImgPath;
        } catch (Exception e) {
            // TODO: handle exception
        	return null;
        }
    }
    public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {  
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());  
    }  
    
}
