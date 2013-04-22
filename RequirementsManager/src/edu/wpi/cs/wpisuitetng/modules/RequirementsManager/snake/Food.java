package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.snake;

import java.util.ArrayList;

public class Food {

	public static ArrayList<Food> all = new ArrayList<Food>();
	
	
	public Spot spot;
	public int score;
	public Food(Spot spot) {
		all.add(this);
		this.spot = spot;
		this.score = 1;
	}

}
