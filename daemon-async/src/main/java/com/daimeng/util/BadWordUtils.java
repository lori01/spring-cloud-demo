package com.daimeng.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 
* @功能描述: 敏感词过滤
* @名称: BadWordUtils.java 
* @路径 com.daimeng.util 
* @作者 daimeng.fun
* @E-Mail sephy9527@qq.com
* @创建时间 2019年9月27日 下午5:20:24 
* @version V1.0
 */
public class BadWordUtils {

	public static String filePath = "D:\\java_test\\BadWordTxt.txt";//敏感词库文件路径
	public static Set<String> words;
	public static Map<String,String> wordMap;
	public static int minMatchTYpe = 1;      //最小匹配规则
	public static int maxMatchType = 2;      //最大匹配规则
	static{
		BadWordUtils.words = readTxtByLine(filePath);
		addBadWordToHashMap(BadWordUtils.words);
	}
	 public static Set<String> readTxtByLine(String path){  
    	Set<String> keyWordSet = new HashSet<String>();
        File file=new File(path);  
        if(!file.exists()){      //文件流是否存在
        	return keyWordSet;
        }
        BufferedReader reader=null;  
        String temp=null;  
        //int line=1;  
        try{  
            //reader=new BufferedReader(new FileReader(file));这样在web运行的时候，读取会乱码 
            reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));  
            while((temp=reader.readLine())!=null){  
                //System.out.println("line"+line+":"+temp);  
                keyWordSet.add(temp);
                //line++;  
            }  
        } catch(Exception e){  
            e.printStackTrace();  
        } finally{  
            if(reader!=null){  
                try{  
                    reader.close();  
                }catch(Exception e){  
                    e.printStackTrace();  
                }  
            }  
        }
        return keyWordSet;
    }
	/**
     * 检查文字中是否包含敏感字符，检查规则如下：<br>
     * 从map中查询第一个字，如果存在，则查找第二个字，如果不存在，则不是敏感字
     * 查找第二个字，如果存在，并且isend=1，则整个敏感词找到，不过不存在，则不是敏感字，如果isend！=1，则继续查找下个字，知道isend=1或为空
     * @param txt
     * @param beginIndex
     * @param matchType
     * @return，如果存在，则返回敏感词字符的长度，不存在返回0
     * @version 1.0
     */
	/**
	 * {红={客={isEnd=1}, isEnd=0}, 联={isEnd=0, 盟={isEnd=1}}, 
	 * 法={isEnd=0, 轮={isEnd=0, 功={isEnd=1}}}, 
	 * 粉={饰={太={平={isEnd=1}, isEnd=0}, isEnd=0}, isEnd=0}, 
	 * 三={级={片={isEnd=1}, isEnd=0}, isEnd=0}, 
	 * 自={杀={isEnd=1}, isEnd=0}, 
	 * 基={地={isEnd=1}, isEnd=0}, 
	 * 个={人={崇={拜={isEnd=1}, isEnd=0}, isEnd=0}, isEnd=0}}
	 */
    @SuppressWarnings({ "rawtypes"})
    public static int checkBadWord(String txt,int beginIndex,int matchType){
        boolean  flag = false;    //敏感词结束标识位：用于敏感词只有1位的情况
        int matchFlag = 0;     //匹配标识数默认为0
        char word = 0;
        Map nowMap = wordMap;
        for(int i = beginIndex; i < txt.length() ; i++){
            word = txt.charAt(i);
            nowMap = (Map) nowMap.get(word);     //获取指定key
            if(nowMap != null){     //存在，则判断是否为最后一个
                matchFlag++;     //找到相应key，匹配标识+1 
                if("1".equals(nowMap.get("isEnd"))){       //如果为最后一个匹配规则,结束循环，返回匹配标识数
                    flag = true;       //结束标志位为true   
                    if(minMatchTYpe == matchType){    //最小规则，直接返回,最大规则还需继续查找
                        break;
                    }
                }
            }
            else{     //不存在，直接返回
                break;
            }
        }
        /*“粉饰”匹配词库：“粉饰太平”竟然说是敏感词
         * “个人”匹配词库：“个人崇拜”竟然说是敏感词
         * if(matchFlag < 2 && !flag){     
            matchFlag = 0;
        }*/
        if(!flag){     
            matchFlag = 0;
        }
        return matchFlag;
    }
    
    /**
	 * 判断文字是否包含敏感字符
	 * @param txt  文字
	 * @param matchType  匹配规则 1：最小匹配规则，2：最大匹配规则
	 * @return 若包含返回true，否则返回false
	 * @version 1.0
	 */
	public static boolean isContaintBadWord(String txt,int matchType){
		boolean flag = false;
		for(int i = 0 ; i < txt.length() ; i++){
			int matchFlag = checkBadWord(txt, i, matchType); //判断是否包含敏感字符
			if(matchFlag > 0){    //大于0存在，返回true
				flag = true;
			}
		}
		return flag;
	}
    
    /**
	 * 替换敏感字字符
	 * @param txt
	 * @param matchType
	 * @param replaceChar 替换字符，默认*
	 * @version 1.0
	 */
	public static String replaceBadWord(String txt,int matchType,String replaceChar){
		String resultTxt = txt;
		Set<String> set = getBadWord(txt, matchType);     //获取所有的敏感词
		Iterator<String> iterator = set.iterator();
		String word = null;
		String replaceString = null;
		while (iterator.hasNext()) {
			word = iterator.next();
			replaceString = getReplaceChars(replaceChar, word.length());
			resultTxt = resultTxt.replaceAll(word, replaceString);
		}
		
		return resultTxt;
	}
	/**
	 * 获取文字中的敏感词
	 * @param txt 文字
	 * @param matchType 匹配规则 1：最小匹配规则，2：最大匹配规则
	 * @return
	 * @version 1.0
	 */
	public static Set<String> getBadWord(String txt , int matchType){
		Set<String> sensitiveWordList = new HashSet<String>();
		
		for(int i = 0 ; i < txt.length() ; i++){
			int length = checkBadWord(txt, i, matchType);    //判断是否包含敏感字符
			if(length > 0){    //存在,加入list中
				sensitiveWordList.add(txt.substring(i, i+length));
				i = i + length - 1;    //减1的原因，是因为for会自增
			}
		}
		
		return sensitiveWordList;
	}
	
	/**
	 * 获取替换字符串
	 * @param replaceChar
	 * @param length
	 * @return
	 * @version 1.0
	 */
	private static String getReplaceChars(String replaceChar,int length){
		String resultReplace = replaceChar;
		for(int i = 1 ; i < length ; i++){
			resultReplace += replaceChar;
		}
		
		return resultReplace;
	}
	
	/**
	 * TODO 将我们的敏感词库构建成了一个类似与一颗一颗的树，这样我们判断一个词是否为敏感词时就大大减少了检索的匹配范围。
	 * @param keyWordSet 敏感词库
	 * @author yqwang0907
	 * @date 2018年2月28日下午5:28:08
	 */
	/**
	 * {红={客={isEnd=1}, isEnd=0}, 联={isEnd=0, 盟={isEnd=1}}, 
	 * 法={isEnd=0, 轮={isEnd=0, 功={isEnd=1}}}, 
	 * 粉={饰={太={平={isEnd=1}, isEnd=0}, isEnd=0}, isEnd=0}, 
	 * 三={级={片={isEnd=1}, isEnd=0}, isEnd=0}, 
	 * 自={杀={isEnd=1}, isEnd=0}, 
	 * 基={地={isEnd=1}, isEnd=0}, 
	 * 个={人={崇={拜={isEnd=1}, isEnd=0}, isEnd=0}, isEnd=0}}
	 */
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void addBadWordToHashMap(Set<String> keyWordSet) {
        wordMap = new HashMap(keyWordSet.size());     //初始化敏感词容器，减少扩容操作
        String key = null;  
        Map nowMap = null;
        Map<String, String> newWorMap = null;
        //迭代keyWordSet
        Iterator<String> iterator = keyWordSet.iterator();
        while(iterator.hasNext()){
            key = iterator.next();    //关键字
            nowMap = wordMap;
            for(int i = 0 ; i < key.length() ; i++){
                char keyChar = key.charAt(i);       //转换成char型
                Object wordMap = nowMap.get(keyChar);       //获取
                
                if(wordMap != null){        //如果存在该key，直接赋值
                    nowMap = (Map) wordMap;
                }
                else{     //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<String,String>();
                    newWorMap.put("isEnd", "0");     //不是最后一个
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }
                
                if(i == key.length() - 1){
                    nowMap.put("isEnd", "1");    //最后一个
                }
            }
        }
        System.out.println("=====wordMap=====");
        System.out.println(wordMap);
        System.out.println("=====wordMap=====");
    }
	
	
	public static void main(String[] args) {
		Set<String> s = BadWordUtils.words;
		Map<String,String> map = BadWordUtils.wordMap;
		
		System.out.println("敏感词的数量：" + BadWordUtils.wordMap.size());
		String string = "这就是人生，这里是高楼大厦，这里是高楼，也是大厦。可能是人力，可能是资源。"
						+ "基地的人生，基础的人生，都是人生。"
						+ "福州福建赋值肤质。";
		System.out.println("待检测语句字数：" + string.length());
		long beginTime = System.currentTimeMillis();
		Set<String> set = BadWordUtils.getBadWord(string, 2);
		Boolean i = BadWordUtils.isContaintBadWord(string, 2);
		Boolean i2 = BadWordUtils.isContaintBadWord("粉饰太平", 2);
		Boolean i22 = BadWordUtils.isContaintBadWord("粉饰太平", 1);
		Boolean i3 = BadWordUtils.isContaintBadWord("粉饰", 2);
		Boolean i33 = BadWordUtils.isContaintBadWord("粉饰", 1);
		Boolean i4 = BadWordUtils.isContaintBadWord("太平", 2);
		Boolean i44 = BadWordUtils.isContaintBadWord("太平", 1);
		Boolean i5 = BadWordUtils.isContaintBadWord("个人崇拜", 2);
		Boolean i55 = BadWordUtils.isContaintBadWord("个人崇拜", 1);
		Boolean i6 = BadWordUtils.isContaintBadWord("个人", 2);
		Boolean i66 = BadWordUtils.isContaintBadWord("个人", 1);
		Boolean i7 = BadWordUtils.isContaintBadWord("崇拜", 2);
		Boolean i77 = BadWordUtils.isContaintBadWord("崇拜", 1);
		long endTime = System.currentTimeMillis();
		System.out.println("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);
		for(String str : set) {
			string = string.replace(str, "**");
		}
		System.out.println("最终句子为："+string);
		System.out.println("总共消耗时间为：" + (endTime - beginTime));
	}
}
