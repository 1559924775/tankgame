package TankGame;
import java.awt.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 *
 * @author 15599
 *
 */
public class Game extends JFrame{
	MyPanel mp=null;
	public static void main(String[] args){
		Game g=new Game();
	}
	public Game(){
		mp=new MyPanel();
		Thread t=new Thread(mp);
		t.start();
		this.add(mp);
		this.setTitle("坦克小游戏");
		this.setSize(620,540);
		this.setLocation(200,100);
		this.addKeyListener(mp);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
class MyPanel extends JPanel implements KeyListener,Runnable{
	boolean relive;
	ExecutorService exe=Executors.newCachedThreadPool();
	int row=9;
	int line=1;	
	Hero h=null;
	EnemyTank et=null;
	ConcurrentHashMap<EnemyTank,Vector<Ball>> ets=new ConcurrentHashMap<>();
	Vector<Wall> wss=new Vector<Wall>();
	//ǿ�����С����
	int x=80;
	int y=20;
	int k=0;
	Wall[][] ws=new Wall[x][y];
	public MyPanel(){	
		relive=false;
		//�ҷ�̹��
		h=new Hero(300,400);
		//�з�̹��
		for(int i=0;i<row;i++){
			for(int j=0;j<line;j++){
				et=new EnemyTank((i+1)*45,(j)*35);
				Thread t=new Thread(et);
			
				exe.execute(t);
				ets.put(et,et.ss);			
			}
		}
		//����һ��ǽ
		for(int i=0;i<x;i++){
			for(int j=0;j<y;j++){
				k=j+20;
				Wall w=new Wall((i+1)*5,(k+1)*7);
				ws[i][j]=w;
				wss.add(w);
			}
		}
	}
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(Color.black);
		g.fillRect(0,0,500,400);
		//���ҷ�̹��
		if(h.isLive){
			this.drawTank(h.getX(),h.getY(),g,h.getDirect(),0);
		}
		//���ҷ��ӵ�
		for(int i=0;i<h.bs.size();i++){
			Ball b=h.bs.get(i);
			if(b.isLive){
				g.setColor(Color.red);
				g.draw3DRect(b.getX(),b.getY(),1,1,false);	
			}else {
				h.bs.remove(b);
			}
		}
		//���з�̹��
		int p=0;
		for(EnemyTank et:ets.keySet()){
			
//		for(int i=0;i<ets.size();i++){
//			EnemyTank et=ets.get(i);
			if(et.isLive){
				this.drawTank(et.getX(),et.getY(),g,et.getDirect(),1);
				for(int j=0;j<et.ss.size();j++){
				Ball s=et.ss.get(j);
				if(s.isLive){
					//���з��ӵ�
					g.setColor(Color.cyan);
					g.draw3DRect(s.getX(),s.getY(),1,1,false);	
				}else {
					et.ss.remove(s);
					
				}
				}
			}else {
				ets.remove(et);
				boolean f=false;
				for(EnemyTank et1:ets.keySet()){
					f=f||et1.isLive;
				}
				if(!f){
					
					//�ڴ���9��̹��
					for(int k=0;k<row;k++){
						for(int j=0;j<line;j++){
							et=new EnemyTank((p+1)*45,(j)*35);
							Thread t=new Thread(et);
							
							exe.execute(t);
							ets.put(et,et.ss);					
						}
					}
				}
				
			}
			p++;
		}
		//��ǽ
		
		for(int i=0;i<wss.size();i++){
			Wall w=wss.get(i);
			if(w.isLive){
					g.setColor(Color.blue);
					g.fill3DRect(w.x,w.y,5,7,false);
				}else{
					wss.remove(w);
				}
			}
		
	}
	public void drawTank(int x,int y,Graphics g,int direct,int type){
		switch(type){
		case 0:
			g.setColor(Color.green);break;
		case 1:
			g.setColor(Color.yellow);break;
		}
		switch(direct){
		case 0:
			g.fill3DRect(x,y,5,30,false);
			g.fill3DRect(x+5,y+5,10,20,false);
			g.fill3DRect(x+15,y,5,30,false);
			g.fillOval(x+5,y+10,10,10);
			g.drawLine(x+10,y+15,x+10,y);
			break;
		case 1:
			g.fill3DRect(x-5,y+5,30,5,false);
			g.fill3DRect(x-5+5,y+5+5,20,10,false);
			g.fill3DRect(x-5,y+5+15,30,5,false);
			g.fillOval(x-5+10,y+5+5,10,10);
			g.drawLine(x-5+15,y+5+10,x-5+30,y+5+10);
			break;
		case 2:
			g.fill3DRect(x,y,5,30,false);
			g.fill3DRect(x+5,y+5,10,20,false);
			g.fill3DRect(x+15,y,5,30,false);
			g.fillOval(x+5,y+10,10,10);
			g.drawLine(x+10,y+15,x+10,y+30);
			break;
		case 3:
			g.fill3DRect(x-5,y+5,30,5,false);
			g.fill3DRect(x-5+5,y+5+5,20,10,false);
			g.fill3DRect(x-5,y+5+15,30,5,false);
			g.fillOval(x-5+10,y+5+5,10,10);
			g.drawLine(x-5+15,y+5+10,x-5,y+5+10);
			break;
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {

		if(e.getKeyCode()==KeyEvent.VK_W){
			h.setDirect(0);
			h.moveUp();
			
		}
		if(e.getKeyCode()==KeyEvent.VK_D){
			h.setDirect(1);
			h.moveRight();
		}
		if(e.getKeyCode()==KeyEvent.VK_S){
			h.setDirect(2);
			h.moveDown();
		}
		if(e.getKeyCode()==KeyEvent.VK_A){
			h.setDirect(3);
			h.moveLeft();
		}
		if(e.getKeyCode()==KeyEvent.VK_B){
			if(h.bs.size()<5){
				h.shot();
			}
		}
		//��������
		if(e.getKeyCode()==KeyEvent.VK_O){
			relive=true;
		}
		//�ر�������
		if(e.getKeyCode()==KeyEvent.VK_P){
			relive=false;
		}
		if(relive){
			if(e.getKeyCode()==KeyEvent.VK_N){
				h.isLive=true;
				h.setX(300);h.setY(400);h.setDirect(0);
			}
		}
	}
	public void hitEnemyTank(Ball b,EnemyTank et){
		switch(et.getDirect()){
		case 0:
		case 2:
			if(b.x>=et.x&&b.x<=et.x+20&&b.y>=et.y&&b.y<=et.y+30){
				b.isLive=false;
				et.isLive=false;
			}
			break;
		case 1:
		case 3:
			if(b.x>=et.x&&b.x<=et.x+30&&b.y>=et.y&&b.y<=et.y+20){
				b.isLive=false;
				et.isLive=false;
			}
			break;
		}
	}
	public void hitMe(Ball b,Hero h){
		switch(h.getDirect()){
		case 0:
		case 2:
			if(b.x>=h.x&&b.x<=h.x+20&&b.y>=h.y&&b.y<=h.y+30){
				b.isLive=false;
				h.isLive=false;
			}
			break;
		case 1:
		case 3:
			if(b.x>=h.x&&b.x<=h.x+30&&b.y>=h.y&&b.y<=h.y+20){
				b.isLive=false;
				h.isLive=false;
			}
			break;
		}
	}
	public void hitWall(Ball b,Wall w){	
		if(b.x>=w.x&&b.x<=w.x+5&&b.y>=w.y&&b.y<=w.y+7){
			b.isLive=false;
			w.isLive=false;
		}	
	}
	public void ethitWall(Ball s,Wall w){
		if(s.x>=w.x&&s.x<=w.x+5&&s.y>=w.y&&s.y<=w.y+7){
			s.isLive=false;
			w.isLive=false;
		}	
	}

	public void judge(){
		for(int i=0;i<h.bs.size();i++){
			Ball b=h.bs.get(i);
			if(b.isLive){
				for(EnemyTank et:ets.keySet()){
//				for(int j=0;j<ets.size();j++){
//					EnemyTank et=ets.get(j);
					if(et.isLive){
						this.hitEnemyTank(b,et);
					}
				}
			}			
		}
		for(EnemyTank et:ets.keySet()){
//		for(int i=0;i<ets.size();i++){
//			EnemyTank et=ets.get(i);
			if(et.isLive){
				for(int j=0;j<et.ss.size();j++){
					Ball b=et.ss.get(j);
					if(b.isLive&&h.isLive){
						this.hitMe(b, h);
					}
				}
			}
		}
		for(int i=0;i<h.bs.size();i++){
			Ball b=h.bs.get(i);
			if(b.isLive){				
				for(int j=0;j<wss.size();j++){
					Wall w=wss.get(j);
					if(w.isLive){
						this.hitWall(b, w);
					}
				}				
			}			
		}
		for(EnemyTank et:ets.keySet()){
//		for(int i=0;i<ets.size();i++){
//			EnemyTank et=ets.get(i);
			if(et.isLive){
				for(int j=wss.size()-1;j>0;j--){
					//System.out.println(wss.size());
					Wall w=wss.get(j);
					if(w.isLive){
						this.isWall(w, et);
						//this.hIsWall(w, h);
					}

				}
			}
		}
		for(EnemyTank et:ets.keySet()){
//		for(int i=0;i<ets.size();i++){
//			EnemyTank et=ets.get(i);
			if(et.isLive){
				for(int j=0;j<et.ss.size();j++){
					Ball s=et.ss.get(j);
					if(s.isLive){
						for(int k=0;k<wss.size();k++){
							Wall w=wss.get(k);
							if(w.isLive){
								this.ethitWall(s, w);
							}	
						}
					}
				}
			}
		}
		for(EnemyTank et1:ets.keySet()){
//		for(int i=0;i<ets.size();i++){
//			EnemyTank et1=ets.get(i);
			if(et1.isLive){
				for(EnemyTank et2:ets.keySet()){
//				for(int j=i+1;j<ets.size();j++){
//					EnemyTank et2=ets.get(j);
					if(et2.isLive){
						this.isetTank(et1,et2);
					}
				}
			}
		}
	}

	//�жϵз�̹���Ƿ�ײǽ
	public void isWall(Wall w,EnemyTank et){
			
			switch(et.getDirect()){
			case 0:
				if(et.x>w.x-19&&et.x<w.x+4&&et.y>w.y&&et.y<w.y+7){
					et.direct=2;
				}
				break;
			case 3:
				if(et.x>w.x&&et.x<w.x+9&&et.y>w.y-22&&et.y<w.y+9){
					et.direct=1;
				}
				break;
			case 2:
				if(et.x>w.x-19&&et.x<w.x+4&&et.y<w.y-22&&et.y>w.y-30){
					et.direct=0;
				}
				break;	
			case 1:
				if(et.x>w.x-32&&et.x<w.x-23&&et.y>w.y-22&&et.y<w.y+9){
					et.direct=3;
					//System.out.println("sgsgsggdgd");
				}
				break;
			}
		}
	public void hIsWall(Wall w,Hero h){
				
				switch(h.getDirect()){
				case 0:
					if(h.x>w.x-20&&h.x<w.x+5&&h.y>w.y&&h.y<w.y+7){
						h.moveUp=false;
						System.out.println("qqqqqqqqqqqqq");
					}else{
						//h.moveUp=true;
					}
			
					break;
				case 1:
					if(h.x>w.x&&h.x<w.x+5&&h.y>w.y-20&&h.y<w.y+7){
						h.moveRight=false;
					}
					break;
				case 2:
					if(h.x>w.x-20&&h.x<w.x+5&&h.y<w.y-22&&h.y>w.y-30){
						h.moveDown=false;
					}
					break;	
				case 3:
					if(h.x>w.x-30&&h.x<w.x-25&&h.y<w.y-20&&h.y>w.y+7){
						h.moveLeft=false;
					}
					break;
				}
		}
	public void isetTank(EnemyTank et1,EnemyTank et2){
		switch(et1.getDirect()){
		case 0:
		case 2:
			switch(et2.getDirect()){
			case 0:
				if(et2.getX()>et1.getX()-22&&et2.getX()<et1.getX()+22&&et2.getY()>et1.getY()&&et2.getY()<et1.getY()+32){
					et2.setDirect(1);
				}break;
			case 1:
				if(et2.getX()>et1.getX()-32&&et2.getX()<et1.getX()&&et2.getY()>et1.getY()-22&&et2.getY()<et1.getY()+32){
					et2.setDirect(2);
				}break;
			case 2:
				if(et2.getX()>et1.getX()-22&&et2.getX()<et1.getX()+22&&et2.getY()>et1.getY()-30&&et2.getY()<et1.getY()-2){
					et2.setDirect(3);
				}break;
			case 3:
				if(et2.getX()>et1.getX()&&et2.getX()<et1.getX()+22&&et2.getY()>et1.getY()-22&&et2.getY()<et1.getY()+32){
					et2.setDirect(0);
				}break;				
			}
		case 1:
		case 3:
			switch(et2.getDirect()){
			case 0:
				if(et2.getX()>et1.getX()-22&&et2.getX()<et1.getX()+32&&et2.getY()>et1.getY()&&et2.getY()<et1.getY()+22){
					et2.setDirect(2);
				}break;
			case 1:
				if(et2.getX()>et1.getX()-30&&et2.getX()<et1.getX()-2&&et2.getY()>et1.getY()-22&&et2.getY()<et1.getY()+22){
					et2.setDirect(3);
				}break;
			case 2:
				if(et2.getX()>et1.getX()-22&&et2.getX()<et1.getX()+32&&et2.getY()>et1.getY()-30&&et2.getY()<et1.getY()-2){
					et2.setDirect(0);
				}break;
			case 3:
				if(et2.getX()>et1.getX()&&et2.getX()<et1.getX()+32&&et2.getY()>et1.getY()-22&&et2.getY()<et1.getY()+22){
					et2.setDirect(1);
				}break;				
			}
		}
	}
	@Override
 	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void run() {
		int flag=0;
		Thread t=new Thread(){
			public void run(){
				
				while(true){
					try {
						Thread.sleep(800);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					h.shot();
					
				}
			}
		};
		t.start();
		
		while(true){
			try{
				Thread.sleep(20);
			}catch(Exception e){
				e.printStackTrace();
			}
			this.judge();
			this.repaint();	
				for(EnemyTank et:ets.keySet()){
//				for(int i=0;i<ets.size();i++){			
					if(flag%100==0){
//						ets.get(i).shot();
						et.shot();
					}
					//System.out.println(ets.size());
				}		
		flag++;	
		System.out.println(ets.size());
		int count=0;
		for(EnemyTank et:ets.keySet()){
			if(et.isLive)count++;
		}
		System.out.println("����̹�˸���-----------------"+count);
		}
	}
	
}     
