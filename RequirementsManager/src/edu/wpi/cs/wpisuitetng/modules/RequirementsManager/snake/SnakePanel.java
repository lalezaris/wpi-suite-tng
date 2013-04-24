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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SnakePanel extends JPanel{

	int gridSize = 15;
	ArrayList<Spot> spots;
	
	Color snakeColor, foodColor, wallColor;
	
	JPanel messagePanel;
	JLabel gameOver, resetText, helpText;
	
	public SnakePanel() {
		spots = new ArrayList<Spot>();
		this.setFocusable(true);
		this.setLayout( new GridBagLayout());
		//this.setLayout(new BorderLayout());
		
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
	
	public void setGameOver(boolean gameOver){
		
		this.messagePanel.setVisible(gameOver);
		
	}
	
	/**
	 * @return the spots
	 */
	public ArrayList<Spot> getSpots() {
		return spots;
	}



	/**
	 * @param spots the spots to set
	 */
	public void setSpots(ArrayList<Spot> spots) {
		this.spots = spots;
		this.repaint();
	}



	/**
	 * @param snakeColor the snakeColor to set
	 */
	public void setSnakeColor(Color snakeColor) {
		this.snakeColor = snakeColor;
	}



	/**
	 * @param foodColor the foodColor to set
	 */
	public void setFoodColor(Color foodColor) {
		this.foodColor = foodColor;
	}



	/**
	 * @param wallColor the wallColor to set
	 */
	public void setWallColor(Color wallColor) {
		this.wallColor = wallColor;
	}



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
	
	private void drawChunk(Graphics g, int x, int y, Color c){
		g.setColor(c);
		g.fillRect(1 + (x*gridSize), 1 + (y*gridSize), gridSize, gridSize);
		g.setColor(Color.black);
		g.drawRect(1 + (x*gridSize), 1 + (y*gridSize), gridSize, gridSize);
	}
	
	public void drawFood(Graphics g){
		g.setColor(foodColor);
		for (int i = 0 ; i < Food.all.size(); i ++)
			drawChunk(g, Food.all.get(i).spot.x, Food.all.get(i).spot.y, foodColor);
			//g.fillRect(1 + (Food.all.get(i).spot.x*gridSize), 1 + (Food.all.get(i).spot.y*gridSize), gridSize, gridSize);
	}
	
	public void drawWall(Graphics g){
		for (int i = 0 ; i < Wall.all.size(); i ++)
			drawChunk(g, Wall.all.get(i).spot.x, Wall.all.get(i).spot.y, wallColor);

	}
	
	private void drawGrid(Graphics g){
		Color lightGray = new Color(200,200,200);
		g.setColor(lightGray);
		for (int x = 1; x < this.getWidth(); x += this.gridSize){
			g.drawLine(x, 0, x, this.getHeight());
		}
		
		for (int y = 1 ; y < this.getHeight(); y += this.gridSize){
			g.drawLine(0,y,this.getWidth(),y);
		}
		
	}
	

}
