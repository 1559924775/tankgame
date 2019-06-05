package TankGame;
import java.util.*;
public class Hero extends Tank{
	int direct=0;
	Ball b=null;
	boolean moveUp=true;
	boolean moveRight=true;
	boolean moveDown=true;
	boolean moveLeft=true;
	Vector<Ball> bs=new Vector<Ball>();
	public Hero(int x,int y){
		super(x,y);
		speed=4;
		this.x=x;
		this.y=y;
	}
	public void shot(){
		switch(this.direct){
		case 0:
			b=new Ball(this.getX()+10,this.getY(),0);break;
		case 1:
			b=new Ball(this.getX()-5+30,this.getY()+5+10,1);break;
		case 2:
			b=new Ball(this.getX()+10,this.getY()+30,2);break;
		case 3:
			b=new Ball(this.getX()-5,this.getY()+5+10,3);break;
		}
		Thread t=new Thread(b);
		t.start();
		bs.add(b);
	}
//	public void move(int i){
//		
//		switch(i){
//		case 0:
//			this.y-=speed;
//			break;
//		case 1:
//			this.x+=speed;
//			break;
//		case 2:
//			this.y+=speed;
//			break;
//		case 3:
//			this.x-=speed;
//			break;
//		}
//	}
	public void moveUp(){
		if(this.moveUp){
			this.y-=speed;
		}
	}
	public void moveRight(){
		if(this.moveRight){
			this.x+=speed;
		}
	}
	public void moveDown(){
		if(this.moveDown){
			this.y+=speed;
		}
	}
	public void moveLeft(){
		if(this.moveLeft){
			this.x-=speed;
		}
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getDirect() {
		return direct;
	}
	public void setDirect(int direct) {
		this.direct = direct;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
