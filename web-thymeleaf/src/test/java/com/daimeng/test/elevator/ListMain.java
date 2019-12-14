package com.daimeng.test.elevator;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;


public class ListMain {

	public static void main(String[] args) {
		JFrame frame = new ListFrame();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setTitle("电梯模拟器");
		frame.setLocation(50, 50);
		frame.setSize(900, 560);
		frame.setResizable(false);
		frame.show();
	}
}
