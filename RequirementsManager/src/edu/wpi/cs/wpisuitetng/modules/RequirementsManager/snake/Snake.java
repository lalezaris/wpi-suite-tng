/**************************************************
 * This file was developed for CS3733: Software Engineering
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html 
 *
 * Contributors:
 *  Chris Hanna
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.snake;

import java.util.ArrayList;

public class Snake {

	public static int LEFT = 37;
	public static int UP = 38;
	public static int RIGHT = 39;
	public static int DOWN = 40;
	
	
	ArrayList<Spot> spots;
	int direction;
	int nextDirection = 0;
	boolean hasMoved = true;
	int length;
	int turnsToGrow;
	Controller controller;
	
	/**
	 * Instantiates a new snake.
	 *
	 * @param start the start
	 */
	public Snake(Spot start) {
		reset(start);
	}
	
	/**
	 * Reset.
	 *
	 * @param start the start
	 */
	public void reset(Spot start){
		spots = new ArrayList<Spot>();
		
		spots.add(start);
		direction = Snake.RIGHT;		
		turnsToGrow = 2;
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
	
	/**
	 * Move.
	 */
	public void move(){
		Spot head = this.getHead();
		if (direction == LEFT){
			spots.add(new Spot(head.x - 1, head.y));
		} else if (direction == UP){
			spots.add(new Spot(head.x, head.y - 1));
		} else if (direction == RIGHT){
			spots.add(new Spot(head.x + 1, head.y));
		} else if (direction == DOWN){
			spots.add(new Spot(head.x, head.y + 1));
		}
		
		if(nextDirection == 0){
			hasMoved = true;
		}
		if(nextDirection != 0){
			direction = nextDirection;
			nextDirection = 0;
		}
		
		head = this.getHead();
		ArrayList<Food> eatenFood = new ArrayList<Food>();
		for (int i = 0 ; i < Food.all.size(); i ++){
			if (head.equals(Food.all.get(i).spot)){
				turnsToGrow += Food.all.get(i).score;
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
		
		
		if (turnsToGrow <= 0){
			spots.remove(0);
		}
		turnsToGrow --;
		if (turnsToGrow <= 0)
			turnsToGrow = 0;
	}
	
	/**
	 * @return the spots
	 */
	public ArrayList<Spot> getSpots() {
		return spots;
	}
	
	public void setHasMoved(boolean value){
		hasMoved = value;
	}
	
	public boolean isHasMoved(){
		return hasMoved;
	}
	
	public void setNextDirection(int nextDirection){
		this.nextDirection = nextDirection;
	}
	
	public int getNextDirection(){
		return nextDirection;
	}
}
