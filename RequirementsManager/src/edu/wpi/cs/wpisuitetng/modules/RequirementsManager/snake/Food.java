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

import java.awt.Color;
import java.util.ArrayList;

/**
 * The Class Food.
 */
public class Food {

	public static ArrayList<Food> all = new ArrayList<Food>();
	
	protected Spot spot;
	protected int score;
	protected Color color;
	
	/**
	 * Instantiates a new food.
	 *
	 * @param spot the spot
	 */
	public Food(Spot spot) {
		all.add(this);
		this.spot = spot;
		score = 1;
	}

	/**
	 * Sets the color.
	 *
	 * @param newColor the new color
	 */
	public void setColor(Color newColor){
		color = newColor;
	}
	
	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public Color getColor(){
		return color;
	}
}
