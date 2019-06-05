package TankGame;

public class Ball implements Runnable{
	int x;
	int y;
	int direct;
	int speed=2;
	boolean isLive=true;
	public Ball(int x,int y,int direct){
		this.x=x;
		this.y=y;
		this.direct=direct;
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
	@Override
	public void run() {
		while(true){
			try{
				Thread.sleep(50);
			}catch(Exception e){}
			switch(this.direct){
			case 0:
				this.y-=speed;
				break;
			case 1:
				this.x+=speed;
				break;
			case 2:
				this.y+=speed;
				break;
			case 3:
				this.x-=speed;
				break;
			}
			if(this.x<0||this.x>500||this.y<0||this.y>400){
				this.isLive=false;break;
			}
			//System.out.println("y="+y);
		}
		
	}
	
}
