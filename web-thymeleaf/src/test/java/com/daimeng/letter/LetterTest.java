package com.daimeng.letter;

public class LetterTest {

	public static void main(String[] args) {
		String word = "abcarsbcebrefgfghsth";
		System.out.println(getNoRepeatLetter(word));
		System.out.println(getNoRepeatLetter2(word));
	}
	//获取单词内第一个不重复的字母
	public static String getNoRepeatLetter(String word){
		if(null == word || "".equals(word)){
			return "";
		}else{
			for(int i = 0; i < word.length(); i ++){
				for(int j = 0; j < word.length(); j++){
					if(i == j){
						continue;
					}
					String stri = word.substring(i, i+1);
					String strj = word.substring(j, j+1);
					if(stri.equals(strj)){
						break;
					}
					if(j == (word.length()-1)){
						return stri;
					}
				}
			}
		}
		return "";
	}
	//获取单词内第一个不重复的字母
	public static String getNoRepeatLetter2(String word){
		if(null == word || "".equals(word)){
			return "";
		}else{
			String newWord = word;
			for(int i = 0; i < word.length(); i++){
				String newWord2 = word.substring(i+1);
				String letter = word.substring(i, i+1);
				if(newWord2.indexOf(letter) > -1){
					newWord = newWord.replace(letter, "");
				}
			}
			if(newWord != null && !"".equals(newWord)){
				return newWord.substring(0,1);
			}
		}
		return "";
	}

}
