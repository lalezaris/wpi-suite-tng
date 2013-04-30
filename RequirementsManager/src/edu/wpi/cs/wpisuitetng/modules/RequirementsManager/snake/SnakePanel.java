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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The Class SnakePanel.
 */
@SuppressWarnings("serial")
public class SnakePanel extends JPanel{

	int gridSize = 15;
	ArrayList<Spot> spots;
	
	Color snakeColor, foodColor, wallColor;
	
	JPanel messagePanel;
	JLabel gameOver, resetText, helpText;
	
	/**
	 * Instantiates a new snake panel.
	 */
	public SnakePanel() {
		spots = new ArrayList<Spot>();
		this.setFocusable(true);
		this.setLayout( new GridBagLayout());
		
		messagePanel = new JPanel();
		messagePanel.setLayout(new GridBagLayout());
		messagePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		gameOver = new JLabel("GAME OVER");
		gameOver.setFont(new Font("serif", Font.PLAIN, 50));
		resetText = new JLabel("press space to reset");
		helpText = new JLabel("use arrow keys to turn");
		resetText.setFont(new Font("serif", Font.PLAIN, 20));
		helpText.setFont(new Font("serif", Font.PLAIN, 20));
		setGameOver(false);
		messagePanel.add(gameOver);
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 1;
		messagePanel.add(resetText,c);
		c.gridy = 2;
		messagePanel.add(helpText,c);
		
		this.add(messagePanel);
	}
	
	/**
	 * Sets the game over.
	 *
	 * @param gameOver the new game over
	 */
	public void setGameOver(boolean gameOver){
		
		messagePanel.setVisible(gameOver);
		
	}
	
	/**
	 * Gets the spots.
	 *
	 * @return the spots
	 */
	public ArrayList<Spot> getSpots() {
		return spots;
	}



	/**
	 * Sets the spots.
	 *
	 * @param spots the spots to set
	 */
	public void setSpots(ArrayList<Spot> spots) {
		this.spots = spots;
		this.repaint();
	}



	/**
	 * Sets the snake color.
	 *
	 * @param snakeColor the snakeColor to set
	 */
	public void setSnakeColor(Color snakeColor) {
		this.snakeColor = snakeColor;
	}



	/**
	 * Sets the food color.
	 *
	 * @param foodColor the foodColor to set
	 */
	public void setFoodColor(Color foodColor) {
		this.foodColor = foodColor;
	}



	/**
	 * Sets the wall color.
	 *
	 * @param wallColor the wallColor to set
	 */
	public void setWallColor(Color wallColor) {
		this.wallColor = wallColor;
	}



	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g){
		
		super.paint(g);
		drawGrid(g);
		
		drawFood(g);
		
		for (int i = 0 ; i < spots.size(); i ++)
			drawChunk(g,spots.get(i).x,spots.get(i).y, snakeColor);
		
		drawWall(g);
		
		messagePanel.repaint();

	}
	
	/**
	 * Draw chunk.
	 *
	 * @param g the g
	 * @param x the x
	 * @param y the y
	 * @param c the c
	 */
	private void drawChunk(Graphics g, int x, int y, Color c){
		g.setColor(c);
		g.fillRect(1 + (x*gridSize), 1 + (y*gridSize), gridSize, gridSize);
		g.setColor(Color.black);
		g.drawRect(1 + (x*gridSize), 1 + (y*gridSize), gridSize, gridSize);
	}
	
	/**
	 * Draw food.
	 *
	 * @param g the g
	 */
	public void drawFood(Graphics g){
		for (int i = 0 ; i < Food.all.size(); i ++){
			g.setColor(Food.all.get(i).getColor());
			drawChunk(g, Food.all.get(i).spot.x, Food.all.get(i).spot.y, Food.all.get(i).getColor());
		}
	}
	
	/**
	 * Draw wall.
	 *
	 * @param g the g
	 */
	public void drawWall(Graphics g){
		for (int i = 0 ; i < Wall.all.size(); i ++)
			drawChunk(g, Wall.all.get(i).spot.x, Wall.all.get(i).spot.y, wallColor);

	}
	
	/**
	 * Draw grid.
	 *
	 * @param g the g
	 */
	private void drawGrid(Graphics g){
		Color lightGray = new Color(200,200,200);
		g.setColor(lightGray);
		for (int x = 1; x < this.getWidth(); x += gridSize){
			g.drawLine(x, 0, x, this.getHeight());
		}
		
		for (int y = 1 ; y < this.getHeight(); y += gridSize){
			g.drawLine(0,y,this.getWidth(),y);
		}
		
	}
	

}
