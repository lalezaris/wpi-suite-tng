package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.snake;

import java.util.ArrayList;

public class Wall {

	public static ArrayList<Wall> all = new ArrayList<Wall>();
	
	
	public Spot spot;
	public int score;
	public Wall(Spot spot) {
		all.add(this);
		this.spot = spot;
		this.score = 1;
	}
}
