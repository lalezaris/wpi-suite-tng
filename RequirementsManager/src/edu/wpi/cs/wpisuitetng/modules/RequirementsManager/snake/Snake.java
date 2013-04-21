package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.snake;

import java.util.ArrayList;

public class Snake {

	public static int LEFT = 37;
	public static int UP = 38;
	public static int RIGHT = 39;
	public static int DOWN = 40;
	
	
	ArrayList<Spot> spots;
	int direction;
	int length;
	int turnsToGrow;
	Controller controller;
	
	public Snake(Spot start) {
		reset(start);
	}
	
	public void reset(Spot start){
		spots = new ArrayList<Spot>();
		
		spots.add(start);
		this.direction = Snake.RIGHT;		
		this.turnsToGrow = 2;
	}

	public void setController(Controller controller){
		this.controller = controller;
	}
	
	public void setDirection(int direction){
		this.direction = direction;
	}
	
	public Spot getHead(){
		return spots.get(spots.size()-1);
	}
	
	public void move(){
		Spot head = this.getHead();
		if (this.direction == LEFT){
			this.spots.add(new Spot(head.x - 1, head.y));
		} else if (this.direction == UP){
			this.spots.add(new Spot(head.x, head.y - 1));
		} else if (this.direction == RIGHT){
			this.spots.add(new Spot(head.x + 1, head.y));
		} else if (this.direction == DOWN){
			this.spots.add(new Spot(head.x, head.y + 1));
		}
		
		head = this.getHead();
		ArrayList<Food> eatenFood = new ArrayList<Food>();
		for (int i = 0 ; i < Food.all.size(); i ++){
			if (head.equals(Food.all.get(i).spot)){
				this.turnsToGrow += Food.all.get(i).score;
				controller.eatFood(Food.all.get(i));
				eatenFood.add(Food.all.get(i));
				
			}
		}
		for (int i = 0 ; i < eatenFood.size(); i ++)
			Food.all.remove(eatenFood.get(i));
		
	
		if (head.x > controller.getXMax())
			head.x = 0;
		if (head.x < 0)
			head.x = controller.getXMax();
		if (head.y > controller.getYMax())
			head.y = 0;
		if (head.y < 0)
			head.y = controller.getYMax();
		
		for (int i = 0 ; i < spots.size()-1 ; i ++){
			if (head.equals(spots.get(i)))
				controller.setGameRunning(false);
			for (int w = 0 ; w < Wall.all.size(); w ++)
				if (head.equals(Wall.all.get(w).spot))
					controller.setGameRunning(false);
		}
		
		
		if (this.turnsToGrow <= 0){
			this.spots.remove(0);
		}
		this.turnsToGrow --;
		if (this.turnsToGrow <= 0)
			this.turnsToGrow = 0;
	}
	
	/**
	 * @return the spots
	 */
	public ArrayList<Spot> getSpots() {
		return spots;
	}
	
	

}
