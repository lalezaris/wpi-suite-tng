package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.snake;

public class Spot {

	public int x, y;
	
	public Spot(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Spot(){
		this.x = 0;
		this.y = 0;
	}
	
	@Override
	public boolean equals(Object obj){
		
		if (obj instanceof Spot){
			Spot other = (Spot)obj;
			if (other.x == x && other.y == y)
				return true;
		}
		
		return false;
	}
	

}
