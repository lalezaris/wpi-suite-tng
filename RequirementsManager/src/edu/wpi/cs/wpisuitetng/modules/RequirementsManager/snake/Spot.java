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

public class Spot {

	public int x, y;
	
	/**
	 * Instantiates a new spot.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public Spot(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Instantiates a new spot.
	 */
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
