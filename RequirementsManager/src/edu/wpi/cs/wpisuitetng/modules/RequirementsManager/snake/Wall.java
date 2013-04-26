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

public class Wall {

	public static ArrayList<Wall> all = new ArrayList<Wall>();
	
	
	public Spot spot;
	public int score;
	
	/**
	 * Instantiates a new wall.
	 *
	 * @param spot the spot
	 */
	public Wall(Spot spot) {
		all.add(this);
		this.spot = spot;
		this.score = 1;
	}
}
