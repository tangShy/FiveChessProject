package com.tang.game.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class FiveChessFrame extends JFrame implements MouseListener,Runnable {
	
	//获取屏幕的宽度
	int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	//获取屏幕的高度
	int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	//背景图片
	BufferedImage bgImage = null;
	//保存棋子的坐标
	int x = 0;
	int y = 0;
	//保存之前下过的所有棋子的坐标。其中数据内容 0：表示这个点没有棋子；1：表示这个点是黑子； 2：表示这个点是白子
	int [][] allChess = new int [19][19];
	//标识当前应该是黑棋还是白棋落下一步棋 
	boolean isBlack = true;	//默认为黑棋
	//标识当前游戏是否可以继续
	boolean canPlay = true;
	//保存显示的提示信息
	String message = "黑方先行";
	//保存最多拥有多少时间(秒)
	int maxTime = 0;
	//做倒计时的线程类
	Thread t = new Thread(this);
	//用来保存黑方与白方的剩余时间
	int blackTime = 0;
	int whiteTime = 0;
	//用来保存双方剩余时间的显示信息
	String blackMessage = "无限制";
	String whiteMessage = "无限制";
	
	
	public FiveChessFrame() {
		//设置标题
		this.setTitle("五子棋");
		this.setSize(500,500);
		this.setLocation((width - 500)/2, (height - 500)/2);
		//窗体大小设置为不可变
		this.setResizable(false);
		//将窗体的关闭方式设置为默认关闭后程序结束
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//为窗体加入监听器
		this.addMouseListener(this);
		//将窗体显示出来
		this.setVisible(true);
		
		/*try {
			bgImage = ImageIO.read(new File("background.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		String imagePath = "" ;
		try {
			imagePath = System.getProperty("user.dir")+"/bin/image/background.jpg" ;
			bgImage = ImageIO.read(new File(imagePath.replaceAll("\\\\", "/")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		t.start();
		t.suspend();
		//刷新屏幕，防止开始游戏时出现无法显示的情况。
		this.repaint();
		
	}
	
	public void paint(Graphics g) {
		//双缓冲技术防止屏幕闪烁
		BufferedImage bi = new BufferedImage(500,500,BufferedImage.TYPE_INT_ARGB);
		Graphics g2 = bi.createGraphics();
		
		//绘制背景
		g2.drawImage(bgImage, 3, 24, this);
		//输出标题信息
		g2.setColor(Color.black);
		g2.setFont(new Font("黑体",Font.BOLD,20));
		g2.drawString("游戏信息：" + message, 120, 60);
		//输出时间信息
		g2.setFont(new Font("黑体",0,14));
		g2.drawString("黑方时间："+ blackMessage, 30, 470);
		g2.drawString("白方时间："+ whiteMessage, 250, 470);
		
		//绘制棋盘
		for(int i=0;i<19;i++){
			g2.drawLine(12, 74+i*20, 372, 74+i*20);
			g2.drawLine(12+i*20, 74, 12+i*20, 434);
		}
		//标注点位
		g2.fillOval(70, 132, 4, 4);
		g2.fillOval(70, 252, 4, 4);
		g2.fillOval(70, 372, 4, 4);
		g2.fillOval(190, 132, 4, 4);
		g2.fillOval(190, 252, 4, 4);
		g2.fillOval(190, 372, 4, 4);
		g2.fillOval(310, 132, 4, 4);
		g2.fillOval(310, 252, 4, 4);
		g2.fillOval(310, 372, 4, 4);
		
/*		//绘制棋子
		x = (x-12)/20*20+12;
		y = (y-72)/20*20+72;
		//黑子
//		g.fillOval(x-7, y-7, 14, 14);
		//白子
		g.setColor(Color.WHITE);
		g.fillOval(x-7, y-7, 14, 14);
		g.setColor(Color.BLACK);
		g.drawOval(x-7, y-7, 14, 14);*/
		
		//绘制全部棋子
		for(int i=0;i<19;i++){
			for(int j=0;j<19;j++){
				if(allChess[i][j] == 1){
					//黑子
					int tempX = i*20 + 12;
					int tempY = j*20 + 72;
					g2.fillOval(tempX - 7, tempY - 7, 14, 14);
				}
				if(allChess[i][j] == 2){
					//白子
					int tempX = i*20 + 12;
					int tempY = j*20 + 72;
					g2.setColor(Color.WHITE);
					g2.fillOval(tempX - 7, tempY - 7, 14, 14);
					g2.setColor(Color.BLACK);
					g2.drawOval(tempX - 7, tempY - 7, 14, 14);
				}
			}
		}
		g.drawImage(bi, 0, 0, this);
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		/*System.out.println("x:"+e.getX());
		System.out.println("y:"+e.getY());*/
		if (canPlay == true){
			x = e.getX();
			y = e.getY();
			if(x>=10 && x <= 374 && y>=72 && y<=434){
				x = (x-10) / 20;
				y = (y-70) / 20; 
				if(allChess[x][y] == 0){
					//判断当前要下的是什么颜色的棋子
					if(isBlack == true){	//黑棋
						allChess[x][y] = 1;
						isBlack = false;
						message = "轮到白方";
					} else {				//白棋
						allChess[x][y] = 2;
						isBlack = true; 
						message = "轮到黑方";
					}
					
					//判断这个棋子是否和其他的棋子连成5连，即判断游戏是否结束。
					boolean winFlag = this.checkWin();
					if(winFlag == true) {
						JOptionPane.showMessageDialog(this, "游戏结束，" + 
								(allChess[x][y] == 1 ? "黑方":"白方") + "获胜！");
						canPlay = false;
					}
				} else {
					JOptionPane.showMessageDialog(this, "当前位置已有棋子，请重新落子~");
				}
				this.repaint();
			}
		}
		
//		System.out.println("x:"+e.getX()+"y:"+e.getY());
		//点击 开始游戏  按钮 
		if (e.getX() >= 400 && e.getX() <= 470 && e.getY() >= 75 && e.getY() <= 105) {
			int result = JOptionPane.showConfirmDialog(this, "是否重新开始游戏？");
			if (result == 0) {//重新开始
				//1.清空棋盘--allChess这个数组中全部数据归零
				allChess = new int[19][19];
				//或者用for循环情况数组
				/*for (int i=0; i<19; i++) {
					for(int j=0; j<19; j++){
						allChess[i][j] = 0;
					}
				}*/
				//2.将 游戏信息的显示改回到开始位置
				//3.将下一步下棋改为黑方
				message = "黑方先行";
				isBlack = true;
				canPlay = true;
				blackTime = maxTime;
				whiteTime = maxTime;
				if (maxTime > 0) {
					blackMessage = maxTime/3600 + ":" + (maxTime/60 - maxTime/3600 * 60 ) + ":" + (maxTime - maxTime/60 * 60);
					whiteMessage = maxTime/3600 + ":" + (maxTime/60 - maxTime/3600 * 60 ) + ":" + (maxTime - maxTime/60 * 60);
					t.resume();
				}else {
					blackMessage = "无限制";
					whiteMessage = "无限制";
				}
				this.repaint();
			}
		}
		
		//点击 游戏设置  按钮
		if (e.getX() >= 400 && e.getX() <= 470 && e.getY() >= 125 && e.getY() <= 155) {
			String input = JOptionPane.showInputDialog("请输入游戏的最大时间（单位：分钟），\n如果输入0，表示没有时间限制~");
			try {
				maxTime = Integer.parseInt(input) * 60;
				if (maxTime < 0) {
					JOptionPane.showMessageDialog(this, "请输入正确信息，不允许输入负数！");
				}
				if (maxTime == 0) {
					int result = JOptionPane.showConfirmDialog(this, "设置完成,是否重新开始游戏？");
					if (result == 0){
						//1.清空棋盘--allChess这个数组中全部数据归零
						allChess = new int[19][19];
						//2.将 游戏信息的显示改回到开始位置
						//3.将下一步下棋改为黑方
						message = "黑方先行";
						isBlack = true;
						canPlay = true;
						blackTime = maxTime;
						whiteTime = maxTime;
						blackMessage = "无限制";
						whiteMessage = "无限制";
						this.repaint();
					}
				}
				if (maxTime > 0) {
					int result = JOptionPane.showConfirmDialog(this, "设置完成,是否重新开始游戏？");
					if (result == 0){
						//1.清空棋盘--allChess这个数组中全部数据归零
						allChess = new int[19][19];
						//2.将 游戏信息的显示改回到开始位置
						//3.将下一步下棋改为黑方
						message = "黑方先行";
						isBlack = true;
						canPlay = true;
						blackTime = maxTime;
						whiteTime = maxTime;
						blackMessage = maxTime/3600 + ":" + (maxTime/60 - maxTime/3600 * 60 ) + ":" + (maxTime - maxTime/60 * 60);
						whiteMessage = maxTime/3600 + ":" + (maxTime/60 - maxTime/3600 * 60 ) + ":" + (maxTime - maxTime/60 * 60);
						t.resume();
						this.repaint();
					}
				}
				
			} catch (NumberFormatException e1) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(this, "请正确输入信息~");
			}
 			
		}
		
		//点击 游戏说明  按钮
		if (e.getX() >= 400 && e.getX() <= 470 && e.getY() >= 175 && e.getY() <= 205) {
			JOptionPane.showMessageDialog(this, "这是一个五子棋小游戏，游戏规则：\n黑白双方轮流落子，先连成5个在一条直线上者即胜出！");
		}
		
		//点击 认输  按钮
		if (e.getX() >= 400 && e.getX() <= 470 && e.getY() >= 275 && e.getY() <= 305) {
			int result = JOptionPane.showConfirmDialog(this, "是否确认认输？");
			if (result == 0) {//确认认输
				if (isBlack) {
					JOptionPane.showMessageDialog(this, "黑方已经认输，游戏结束！");
				} else {
					JOptionPane.showMessageDialog(this, "白方已经认输，游戏结束！");
				}
				canPlay = false;
			}
		} 
		
		//点击 关于 按钮
		if (e.getX() >= 400 && e.getX() <= 470 && e.getY() >= 325 && e.getY() <= 355) {
			JOptionPane.showMessageDialog(this, "本游戏作者：akon，\n如遇问题请访问www.akon.com");
		}
		
		//点击 退出 按钮
		if (e.getX() >= 400 && e.getX() <= 470 && e.getY() >= 375 && e.getY() <= 405) {
			JOptionPane.showMessageDialog(this, "退出游戏");
			System.exit(0);
		}		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	
	private boolean checkWin() {
		boolean flag = false;
		int count = 1;	//保存同颜色的棋子相连个数
		int color = allChess[x][y];//保存当前所下棋子的颜色
		
	/*	if(color == allChess[x+1][y]) {
			count++;
			if(color == allChess[x+2][y]) {
				count++;
				//if()...
			}
		}*/
		
		//通过循环来做棋子相连的判断
		/*//横向的判断
		int i = 1;
		while(color == allChess[x+i][y]) {
			count++;
			i++;
		}
		i =1;
		while(color == allChess[x-i][y]) {
			count++;
			i++;
		}
		if(count >= 5) {
			flag = true;	//游戏结束标志
		}
		
		//纵向的判断
		int i2 = 1;
		while (color == allChess[x][y+i2]) {
			count++;
			i2++;
		}
		i2 = 1;
		while (color == allChess[x][y-i2]) {
			count++;
			i2++;
		}
		if (count >= 5) {
			flag = true;
		}
		//斜方向的判断（右上）实则右上是x+1,y-1
		int i3 = 1;
		while (color == allChess[x+i3][y-i3]) {
			count++;
			i3++;
		}
		i3 = 1;
		while (color == allChess[x-i3][y+i3]) {
			count++;
			i3++;
		}
		if (count >= 5) {
			flag = true;
		}
		//斜方向的判断（右下）实则右下是x+1,y+1
		int i4 = 1;
		while (color == allChess[x+i4][y+i4]) {
			count++;
			i4++;
		}
		i4 = 1;
		while (color == allChess[x-i4][y-i4]) {
			count++;
			i4++;
		}
		if (count >= 5) {
			flag = true;
		}*/
		
		count = checkCount(1,0,color);//判断横向
		if (count >= 5) {
			flag = true;
		} else {
			count = checkCount(0,1,color);//判断纵向
			if (count >= 5) {
				flag = true;
			} else {
				count = checkCount(1,-1,color);//判断右上、左下
				if (count >= 5) {
					flag = true;
				} else {
					count = checkCount(1,1,color);//判断左上、右下
					if (count >= 5) {
						flag = true;
					}
				}
			}
		}
		return flag;
	}
	
	//计算相连的同颜色棋子个数
	private  int checkCount(int xChange,int yChange,int color) {
		int count = 1;
		int tempX = xChange;
		int tempY = yChange;
		while (x+xChange >= 0 && x+xChange <=18 && y+yChange >=0 && y+yChange <=18 
				&& color == allChess[x+xChange][y+yChange]) {
			count++;
			if (xChange != 0){
				xChange++;
			}
			if (yChange != 0){
				if (yChange > 0)
					yChange++;
				else
					yChange--;
			}
		}
		xChange = tempX;
		yChange = tempY;
		while (x-xChange >= 0 && x-xChange <=18 && y-yChange >=0 && y-yChange <=18 
				&& color == allChess[x-xChange][y-yChange]) {
			count++;
			if (xChange != 0){
				xChange++;
			}
			if (yChange != 0){
				if (yChange > 0)
					yChange++;
				else 
					yChange--;
			}
		}
		return count;
	}

	@Override
	public void run() {
		//判断是否有时间限制
		if (maxTime > 0) {
			while(true) {
				if (isBlack) {//黑方
					blackTime--;
					if (blackTime == 0) {
						JOptionPane.showMessageDialog(this, "黑方超时，游戏结束！");
					}
				}else {		//白方
					whiteTime--;
					if (whiteTime == 0) {
						JOptionPane.showMessageDialog(this, "白方超时，游戏结束！");
					}
				}
				blackMessage = blackTime / 3600 + ":" + (blackTime/60 - blackTime/3600*60) + ":" + (blackTime - blackTime/60*60);
				whiteMessage = whiteTime/3600 + ":" + (whiteTime/60 - whiteTime/3600*60) + ":" + (whiteTime - whiteTime/60*60);
				this.repaint();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
