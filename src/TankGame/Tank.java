package TankGame;
	
	
public class Tank {
	int x;
	int y;
	int direct;
	int type;
	int speed=1;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + direct;
		result = prime * result + (isLive ? 1231 : 1237);
		result = prime * result + speed;
		result = prime * result + type;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tank other = (Tank) obj;
		if (direct != other.direct)
			return false;
		if (isLive != other.isLive)
			return false;
		if (speed != other.speed)
			return false;
		if (type != other.type)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	boolean isLive=true;
	public Tank(int x,int y){
		this.x=x;
		this.y=y;
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
}
