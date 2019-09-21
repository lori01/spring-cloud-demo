package com.daimeng.test;

import java.util.HashMap;

public class TestMain {

	public static void main(String[] args) {
		HashMap map = new HashMap();
		System.out.println(map.get("CST_ID"));
		System.out.println((String) map.get("CST_ID"));
	}
}
