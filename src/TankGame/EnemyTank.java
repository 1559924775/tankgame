package TankGame;
import java.util.Vector;
public class EnemyTank extends Tank implements Runnable{
	boolean isLive=true;
//	byte[] b=new byte[1024*516*1];
	
	int direct=2;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + direct;
		result = prime * result + (isLive ? 1231 : 1237);
		result = prime * result + ((s == null) ? 0 : s.hashCode());
		result = prime * result + ((ss == null) ? 0 : ss.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnemyTank other = (EnemyTank) obj;
		if (direct != other.direct)
			return false;
		if (isLive != other.isLive)
			return false;
		if (s == null) {
			if (other.s != null)
				return false;
		} else if (!s.equals(other.s))
			return false;
		if (ss == null) {
			if (other.ss != null)
				return false;
		} else if (!ss.equals(other.ss))
			return false;
		return true;
	}
	Ball s=null;
	Vector<Ball> ss=new Vector<Ball>();
	public EnemyTank(int x,int y){
		super(x,y);
		speed=0;
		this.x=x;
		this.y=y;
	}
	public void shot(){
		
		switch(this.direct){
		case 0:
			s=new Ball(this.getX()+10,this.getY(),0);break;
		case 1:
			s=new Ball(this.getX()-5+30,this.getY()+5+10,1);break;
		case 2:
			s=new Ball(this.getX()+10,this.getY()+30,2);break;
		case 3:
			s=new Ball(this.getX()-5,this.getY()+5+10,3);break;
		}
		Thread t=new Thread(s);
		//System.out.println("y="+y);
		//System.out.println("x="+x);
		t.start();
		//if(ss.size()<100){
		ss.add(s);
		//}
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
	public void move(int i){
		switch(i){
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
	}
	@Override
	public void run() {
		int flag=0;
		while(true){
			try{
				Thread.sleep(50);
			}catch(Exception e){}
			flag++;
			switch(this.direct){
			case 0:
				this.move(0);break;
			case 1:
				this.move(1);break;
			case 2:
				this.move(2);break;
			case 3:
				this.move(3);break;
			}	
			//System.out.println(flag);
			if(flag%40==0){
				this.direct=(int)(Math.random()*4);
			}
			
		}
		
	}
}
