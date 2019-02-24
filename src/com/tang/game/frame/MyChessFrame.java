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



public class MyChessFrame extends JFrame implements MouseListener{
	public MyChessFrame() {
		
		this.setTitle("五子棋小游戏");
		this.setSize(500, 400);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
//		System.out.println("宽:" + width + "高:" + height); 
		this.setLocation((width - 500)/2, (height - 400)/2);
		
		this.addMouseListener(this);
		
		this.setVisible(true);
	}
	

	public void paint(Graphics g) {
//		g.drawString("五子棋游戏",50,50);	
//		g.drawOval(20,40,40,40);
//		g.fillOval(100,100,40,40);
//		g.drawLine(50, 50, 100, 100);
//		g.drawRect(40, 40, 80, 100);
//		g.fillRect(100, 100, 50, 50);
		
		BufferedImage img = null;
		/*try{
			img = ImageIO.read(new File("壁纸01.jpg"));
		}catch(IOException e){
			e.printStackTrace();
		}*/
		g.drawImage(img, 0, 0, this);
		
		g.setColor(Color.red);
		g.fillRect(40, 40, 80, 100);
		g.setColor(Color.blue);
		g.setFont(new Font("楷体", Font.BOLD, 50));
		g.drawString("五子棋游戏",100,100);
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("鼠标点击");
		JOptionPane.showMessageDialog(this, "鼠标点击~");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("鼠标按下");
		System.out.println("点击位置：X-->" + e.getX());
		System.out.println("点击位置：Y-->" + e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("鼠标抬起");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		/*System.out.println("鼠标进入");
		JOptionPane.showMessageDialog(this, "鼠标进入~");*/
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		/*System.out.println("鼠标离开");
		JOptionPane.showMessageDialog(this, "鼠标离开~");*/
	}
}
