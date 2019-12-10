package com.daimeng.util;

public class NumberUtils {

	public static Long getRandomNumber(int length){
		if(length <= 0){
			length = 1;
		}
		long multiplier = 1;
		for(int i = 0; i < length; i ++){
			multiplier *= 10;
		}
		return Double.valueOf(Math.random()*multiplier).longValue();
	}
	public static void main(String[] args) {
		System.out.println(getRandomNumber(0));
		System.out.println(getRandomNumber(1));
		System.out.println(getRandomNumber(2));
		System.out.println(getRandomNumber(3));
		System.out.println(getRandomNumber(4));
		System.out.println(getRandomNumber(10));
	}
}
