package com.daimeng.test.java8lambda;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class ArrayTest {
	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<String>();
		list.add("C");
		list.add("B");
		list.add("S");
		list.add("D");
		list.add("A");
		list.add("Z");
		list.add("a");
		list.forEach(System.out :: println);
		//Optional.ofNullable(list).orElse(new ArrayList<String>()).stream().sorted(Comparator.comparing());
	}
}
