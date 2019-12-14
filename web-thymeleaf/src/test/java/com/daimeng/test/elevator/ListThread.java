package com.daimeng.test.elevator;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

public class ListThread extends JPanel implements Runnable {
	private final int UP = 1, DN = -1, ABORT = 0; // 电梯的状态
	private static int floorNum; // 楼层数
	private int direction; // 电梯运行的方向或停止
	private int curPos; // 电梯目前处于的楼层位置
	private boolean[] numState; // 电梯内部数字键的状态
	private int tarPos; // 电梯到达的目标位置
	private Thread thread; // 自身线程
	private int j = 0; // 电梯内人数
	private String n = "0";

	private Color numColor0 = new Color(200, 200, 200),
			numColor1 = Color.yellow;
	private Color floorColor0 = new Color(140, 140, 140),
			floorColor1 = Color.yellow;
	Color unPressedColor = new Color(220, 220, 220);
	Color ziColor = new Color(100, 100, 100);

	JButton[] listButton; // 代表电梯的数组
	JButton[] numButton; // 电梯内部数字按钮
	JLabel dispState, dispFloor;

	JTextField dispN;

	public ListThread() {
		floorNum = ListFrame.getFloorNum();
		direction = ABORT;
		curPos = 0;
		tarPos = 0;

		// 对电梯内部的数字键进行状态初始化
		numState = new boolean[floorNum];
		for (int i = 0; i < numState.length; i++) {
			numState[i] = false;
		}

		// 产生自身线程
		thread = new Thread(this);

		// 面板布局
		setLayout(new GridLayout(floorNum + 3, 2));
		this.setBorder(new MatteBorder(2, 2, 2, 2, Color.black));
		listButton = new JButton[floorNum];
		numButton = new JButton[floorNum];

		dispFloor = new JLabel("FLOOR", SwingConstants.CENTER);
		dispFloor.setForeground(new Color(217, 123, 2));
		dispState = new JLabel("STOP", SwingConstants.CENTER);
		dispState.setForeground(new Color(217, 123, 2));
		// 电梯人数显示
		dispN = new JTextField("0", SwingConstants.CENTER);
		dispN.setEditable(false);
		JLabel dispNum = new JLabel("Number", SwingConstants.CENTER);
		// JLabel dispN = new JLabel(n,SwingConstants.CENTER);

		// 电梯进出人按钮
		JButton dispI = new JButton("IN");
		JButton dispO = new JButton("OUT");
		dispI.setForeground(ziColor);
		dispI.setBackground(unPressedColor);
		dispO.setForeground(ziColor);
		dispO.setBackground(unPressedColor);

		MouseListener numListener = new NumButtonAction();
		MouseListener inListener = new InButtonAction();
		MouseListener outListener = new OutButtonAction();

		dispI.addMouseListener(inListener);
		dispO.addMouseListener(outListener);

		this.add(dispFloor);
		this.add(dispState);
		this.add(dispNum);
		this.add(dispN);
		this.add(dispI);
		this.add(dispO);

		// 设置属性
		for (int i = listButton.length - 1; i >= 0; i--) {
			numButton[i] = new JButton(String.valueOf(i + 1));
			numButton[i].addMouseListener(numListener);
			// numButton[i].setForeground(numColor0);
			numButton[i].setBackground(numColor0);
			listButton[i] = new JButton();
			listButton[i].setEnabled(false);
			listButton[i].setBackground(floorColor0);
			this.add(numButton[i]);
			this.add(listButton[i]);
		}
		listButton[curPos].setBackground(floorColor1);
	}

	// 电梯进人的监听器类
	class InButtonAction extends MouseAdapter implements MouseListener {
		public void mousePressed(MouseEvent e) {
			if (j < 15) {
				j++;
				n = Integer.toString(j);
				// JLabel dispN = new JLabel(n,SwingConstants.CENTER);
				dispN.setText(n + "");

			} else{
				JOptionPane.showMessageDialog(null, "已达最大人数15人", "超载",
						JOptionPane.ERROR_MESSAGE);
			}	 
		}
	}

	// 电梯出人的监听器类
	class OutButtonAction extends MouseAdapter implements MouseListener {
		public void mousePressed(MouseEvent e) {
			
			if (j > 0) {
				j--;
				n = Integer.toString(j);
//				JLabel dispN = new JLabel(n, SwingConstants.CENTER);
				dispN.setText(n+"");
			} else
				JOptionPane.showMessageDialog(null, "电梯已为空", "空载",
						JOptionPane.ERROR_MESSAGE);

		}

	}

	// 电梯内部数字键的监听器类
	class NumButtonAction extends MouseAdapter implements MouseListener {

		public void mousePressed(MouseEvent e) {
			for (int i = 0; i < numButton.length; i++) {
				if (e.getSource() == numButton[i]) {
					numState[i] = true;
					numButton[i].setBackground(numColor1);
					if (direction == ABORT) {
						tarPos = i;
					}

					if (direction == UP) {
						tarPos = getMaxPressedNum();
					}
					if (direction == DN) {
						tarPos = getMinPressedNum();
					}
				}
			}
		}
	}

	// 电梯线程的run()方法
	public void run() {
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
				
			if (tarPos > curPos) {
				direction = UP;
				dispState.setText("↑");
				moveUp();
				direction = ABORT;
				dispState.setText("STOP");
			} else if (tarPos < curPos) {
				direction = DN;
				dispState.setText("↓");
				moveDn();
				direction = ABORT;
				dispState.setText("STOP");
			}
			else 
				if(numState[curPos]){
					try{
						dispState.setText("OPEN");
						Thread.sleep(1000);

						dispState.setText("CLOSE");
						numButton[curPos].setBackground(numColor0);
						Thread.sleep(1000);
						numState[curPos]=false;
					}catch (InterruptedException e){
						e.printStackTrace();
					}
				}
		}
	}

	// 电梯向上运行
	public void moveUp() {
		int oldPos = curPos;
		for (int i = curPos + 1; i <= tarPos; i++) {
			try {
				dispState.setText("↑");
				Thread.sleep(600);
				listButton[i].setBackground(floorColor1);

				if (i > oldPos) {
					listButton[i - 1].setBackground(floorColor0);
				}

				if (numState[i]) {
					dispState.setText("OPEN");
					Thread.sleep(1000);

					dispState.setText("CLOSE");
					numButton[i].setBackground(numColor0);
					Thread.sleep(1000);
				}
				curPos = i;

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		clearState();
	}

	// 电梯向下运行
	public void moveDn() {
		int oldPos = curPos;
//	if(tarPos == oldPos){
//		dispState.setText("OPEN");
//	}else{
		for (int i = curPos - 1; i >= tarPos; i--) {
			try {
				Thread.sleep(600);
				listButton[i].setBackground(Color.yellow);

				if (i < oldPos) {
					listButton[i + 1].setBackground(floorColor0);
				}

				if (numState[i]) {
					dispState.setText("OPEN");
					Thread.sleep(2000);
					dispState.setText("CLOSE");
					numButton[i].setBackground(numColor0);
					Thread.sleep(800);
				}
				curPos = i;

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//}
		clearState();
	}

	public Thread getThread() {
		return thread;
	}

	private int getMinPressedNum() {
		int min = 0;
		for (int i = 0; i < numState.length; i++) {
			if (numState[i]) {
				min = i;
				break;
			}
		}
		return min;
	}

	private int getMaxPressedNum() {
		int max = 0;
		for (int i = numState.length - 1; i >= 0; i--) {
			if (numState[i]) {
				max = i;
				break;
			}
		}
		return max;
	}

	private void clearState() {
		for (int i = 0; i < numState.length; i++) {
			if (numState[i]) {
				numState[i] = false;
				numButton[i].setBackground(numColor0);
			}
		}
	}

	public int getDirection() {
		return direction;
	}

	public int getTarPos() {
		return tarPos;
	}

	public void setDirection(int i) {
		direction = i;
	}

	public void setTarPos(int i) {
		if (direction == ABORT) {
			tarPos = i;
			numState[i] = true;
			if (curPos > tarPos) {
				direction = DN;
			}
			if (curPos < tarPos) {
				direction = UP;
			}
		}

		if (direction == UP && i > tarPos) {
			tarPos = i;
			numState[i] = true;
		}

		if (direction == DN && i < tarPos) {
			tarPos = i;
			numState[i] = true;
		}
	}

	public boolean isUp() {
		return direction == UP;
	}

	public boolean isDown() {
		return direction == DN;
	}

	public boolean isAbort() {
		return direction == ABORT;
	}

	public int getCurPos() {
		return curPos;
	}

	public void setDirectionUp() {
		direction = UP;
	}

	public void setDirectionDn() {
		direction = DN;
	}

	public void setDirectionAbort() {
		direction = ABORT;
	}

}
