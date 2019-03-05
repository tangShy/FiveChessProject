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
	
	//��ȡ��Ļ�Ŀ��
	int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	//��ȡ��Ļ�ĸ߶�
	int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	//����ͼƬ
	BufferedImage bgImage = null;
	//�������ӵ�����
	int x = 0;
	int y = 0;
	//����֮ǰ�¹����������ӵ����ꡣ������������ 0����ʾ�����û�����ӣ�1����ʾ������Ǻ��ӣ� 2����ʾ������ǰ���
	int [][] allChess = new int [19][19];
	//��ʶ��ǰӦ���Ǻ��廹�ǰ�������һ���� 
	boolean isBlack = true;	//Ĭ��Ϊ����
	//��ʶ��ǰ��Ϸ�Ƿ���Լ���
	boolean canPlay = true;
	//������ʾ����ʾ��Ϣ
	String message = "�ڷ�����";
	//�������ӵ�ж���ʱ��(��)
	int maxTime = 0;
	//������ʱ���߳���
	Thread t = new Thread(this);
	//��������ڷ���׷���ʣ��ʱ��
	int blackTime = 0;
	int whiteTime = 0;
	//��������˫��ʣ��ʱ�����ʾ��Ϣ
	String blackMessage = "������";
	String whiteMessage = "������";
	
	
	public FiveChessFrame() {
		//���ñ���
		this.setTitle("������");
		this.setSize(500,500);
		this.setLocation((width - 500)/2, (height - 500)/2);
		//�����С����Ϊ���ɱ�
		this.setResizable(false);
		//������Ĺرշ�ʽ����ΪĬ�Ϲرպ�������
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Ϊ������������
		this.addMouseListener(this);
		//��������ʾ����
		this.setVisible(true);
		
		/*try {
			bgImage = ImageIO.read(new File("background.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		String imagePath = "" ;
		try {
			imagePath = System.getProperty("user.dir")+"/background.jpg" ;
			bgImage = ImageIO.read(new File(imagePath.replaceAll("\\\\", "/")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		t.start();
		t.suspend();
		//ˢ����Ļ����ֹ��ʼ��Ϸʱ�����޷���ʾ�������
		this.repaint();
		
	}
	
	public void paint(Graphics g) {
		//˫���弼����ֹ��Ļ��˸
		BufferedImage bi = new BufferedImage(500,500,BufferedImage.TYPE_INT_ARGB);
		Graphics g2 = bi.createGraphics();
		
		//���Ʊ���
		g2.drawImage(bgImage, 3, 24, this);
		//���������Ϣ
		g2.setColor(Color.black);
		g2.setFont(new Font("����",Font.BOLD,20));
		g2.drawString("��Ϸ��Ϣ��" + message, 120, 60);
		//���ʱ����Ϣ
		g2.setFont(new Font("����",0,14));
		g2.drawString("�ڷ�ʱ�䣺"+ blackMessage, 30, 470);
		g2.drawString("�׷�ʱ�䣺"+ whiteMessage, 250, 470);
		
		//��������
		for(int i=0;i<19;i++){
			g2.drawLine(12, 74+i*20, 372, 74+i*20);
			g2.drawLine(12+i*20, 74, 12+i*20, 434);
		}
		//��ע��λ
		g2.fillOval(70, 132, 4, 4);
		g2.fillOval(70, 252, 4, 4);
		g2.fillOval(70, 372, 4, 4);
		g2.fillOval(190, 132, 4, 4);
		g2.fillOval(190, 252, 4, 4);
		g2.fillOval(190, 372, 4, 4);
		g2.fillOval(310, 132, 4, 4);
		g2.fillOval(310, 252, 4, 4);
		g2.fillOval(310, 372, 4, 4);
		
/*		//��������
		x = (x-12)/20*20+12;
		y = (y-72)/20*20+72;
		//����
//		g.fillOval(x-7, y-7, 14, 14);
		//����
		g.setColor(Color.WHITE);
		g.fillOval(x-7, y-7, 14, 14);
		g.setColor(Color.BLACK);
		g.drawOval(x-7, y-7, 14, 14);*/
		
		//����ȫ������
		for(int i=0;i<19;i++){
			for(int j=0;j<19;j++){
				if(allChess[i][j] == 1){
					//����
					int tempX = i*20 + 12;
					int tempY = j*20 + 72;
					g2.fillOval(tempX - 7, tempY - 7, 14, 14);
				}
				if(allChess[i][j] == 2){
					//����
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
					//�жϵ�ǰҪ�µ���ʲô��ɫ������
					if(isBlack == true){	//����
						allChess[x][y] = 1;
						isBlack = false;
						message = "�ֵ��׷�";
					} else {				//����
						allChess[x][y] = 2;
						isBlack = true; 
						message = "�ֵ��ڷ�";
					}
					
					//�ж���������Ƿ����������������5�������ж���Ϸ�Ƿ������
					boolean winFlag = this.checkWin();
					if(winFlag == true) {
						JOptionPane.showMessageDialog(this, "��Ϸ������" + 
								(allChess[x][y] == 1 ? "�ڷ�":"�׷�") + "��ʤ��");
						canPlay = false;
					}
				} else {
					JOptionPane.showMessageDialog(this, "��ǰλ���������ӣ�����������~");
				}
				this.repaint();
			}
		}
		
//		System.out.println("x:"+e.getX()+"y:"+e.getY());
		//��� ��ʼ��Ϸ  ��ť 
		if (e.getX() >= 400 && e.getX() <= 470 && e.getY() >= 75 && e.getY() <= 105) {
			int result = JOptionPane.showConfirmDialog(this, "�Ƿ����¿�ʼ��Ϸ��");
			if (result == 0) {//���¿�ʼ
				//1.�������--allChess���������ȫ�����ݹ���
				allChess = new int[19][19];
				//������forѭ���������
				/*for (int i=0; i<19; i++) {
					for(int j=0; j<19; j++){
						allChess[i][j] = 0;
					}
				}*/
				//2.�� ��Ϸ��Ϣ����ʾ�Ļص���ʼλ��
				//3.����һ�������Ϊ�ڷ�
				message = "�ڷ�����";
				isBlack = true;
				canPlay = true;
				blackTime = maxTime;
				whiteTime = maxTime;
				if (maxTime > 0) {
					blackMessage = maxTime/3600 + ":" + (maxTime/60 - maxTime/3600 * 60 ) + ":" + (maxTime - maxTime/60 * 60);
					whiteMessage = maxTime/3600 + ":" + (maxTime/60 - maxTime/3600 * 60 ) + ":" + (maxTime - maxTime/60 * 60);
					t.resume();
				}else {
					blackMessage = "������";
					whiteMessage = "������";
				}
				this.repaint();
			}
		}
		
		//��� ��Ϸ����  ��ť
		if (e.getX() >= 400 && e.getX() <= 470 && e.getY() >= 125 && e.getY() <= 155) {
			String input = JOptionPane.showInputDialog("��������Ϸ�����ʱ�䣨��λ�����ӣ���\n�������0����ʾû��ʱ������~");
			try {
				maxTime = Integer.parseInt(input) * 60;
				if (maxTime < 0) {
					JOptionPane.showMessageDialog(this, "��������ȷ��Ϣ�����������븺����");
				}
				if (maxTime == 0) {
					int result = JOptionPane.showConfirmDialog(this, "�������,�Ƿ����¿�ʼ��Ϸ��");
					if (result == 0){
						//1.�������--allChess���������ȫ�����ݹ���
						allChess = new int[19][19];
						//2.�� ��Ϸ��Ϣ����ʾ�Ļص���ʼλ��
						//3.����һ�������Ϊ�ڷ�
						message = "�ڷ�����";
						isBlack = true;
						canPlay = true;
						blackTime = maxTime;
						whiteTime = maxTime;
						blackMessage = "������";
						whiteMessage = "������";
						this.repaint();
					}
				}
				if (maxTime > 0) {
					int result = JOptionPane.showConfirmDialog(this, "�������,�Ƿ����¿�ʼ��Ϸ��");
					if (result == 0){
						//1.�������--allChess���������ȫ�����ݹ���
						allChess = new int[19][19];
						//2.�� ��Ϸ��Ϣ����ʾ�Ļص���ʼλ��
						//3.����һ�������Ϊ�ڷ�
						message = "�ڷ�����";
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
				JOptionPane.showMessageDialog(this, "����ȷ������Ϣ~");
			}
 			
		}
		
		//��� ��Ϸ˵��  ��ť
		if (e.getX() >= 400 && e.getX() <= 470 && e.getY() >= 175 && e.getY() <= 205) {
			JOptionPane.showMessageDialog(this, "����һ��������С��Ϸ����Ϸ����\n�ڰ�˫���������ӣ�������5����һ��ֱ�����߼�ʤ����");
		}
		
		//��� ����  ��ť
		if (e.getX() >= 400 && e.getX() <= 470 && e.getY() >= 275 && e.getY() <= 305) {
			int result = JOptionPane.showConfirmDialog(this, "�Ƿ�ȷ�����䣿");
			if (result == 0) {//ȷ������
				if (isBlack) {
					JOptionPane.showMessageDialog(this, "�ڷ��Ѿ����䣬��Ϸ������");
				} else {
					JOptionPane.showMessageDialog(this, "�׷��Ѿ����䣬��Ϸ������");
				}
				canPlay = false;
			}
		} 
		
		//��� ���� ��ť
		if (e.getX() >= 400 && e.getX() <= 470 && e.getY() >= 325 && e.getY() <= 355) {
			JOptionPane.showMessageDialog(this, "����Ϸ���ߣ�akon��\n�������������www.akon.com");
		}
		
		//��� �˳� ��ť
		if (e.getX() >= 400 && e.getX() <= 470 && e.getY() >= 375 && e.getY() <= 405) {
			JOptionPane.showMessageDialog(this, "�˳���Ϸ");
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
		int count = 1;	//����ͬ��ɫ��������������
		int color = allChess[x][y];//���浱ǰ�������ӵ���ɫ
		
	/*	if(color == allChess[x+1][y]) {
			count++;
			if(color == allChess[x+2][y]) {
				count++;
				//if()...
			}
		}*/
		
		//ͨ��ѭ�����������������ж�
		/*//������ж�
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
			flag = true;	//��Ϸ������־
		}
		
		//������ж�
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
		//б������жϣ����ϣ�ʵ��������x+1,y-1
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
		//б������жϣ����£�ʵ��������x+1,y+1
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
		
		count = checkCount(1,0,color);//�жϺ���
		if (count >= 5) {
			flag = true;
		} else {
			count = checkCount(0,1,color);//�ж�����
			if (count >= 5) {
				flag = true;
			} else {
				count = checkCount(1,-1,color);//�ж����ϡ�����
				if (count >= 5) {
					flag = true;
				} else {
					count = checkCount(1,1,color);//�ж����ϡ�����
					if (count >= 5) {
						flag = true;
					}
				}
			}
		}
		return flag;
	}
	
	//����������ͬ��ɫ���Ӹ���
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
		//�ж��Ƿ���ʱ������
		if (maxTime > 0) {
			while(true) {
				if (isBlack) {//�ڷ�
					blackTime--;
					if (blackTime == 0) {
						JOptionPane.showMessageDialog(this, "�ڷ���ʱ����Ϸ������");
					}
				}else {		//�׷�
					whiteTime--;
					if (whiteTime == 0) {
						JOptionPane.showMessageDialog(this, "�׷���ʱ����Ϸ������");
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
