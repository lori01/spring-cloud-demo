package com.daimeng.test.javabug;

public class Bug {

	//该bug在1.8已解决
	//如果是之前版本，这会打印i=/
	public void test(){
		int i = 8;
		while((i-=3)>0);
		System.out.println("i="+i);
	}
	public static void main(String[] args) {
		Bug bug = new Bug();
		for(int i=0;i<5_000;i++){
			System.out.println("main i="+i);
			bug.test();
		}
	}
}
