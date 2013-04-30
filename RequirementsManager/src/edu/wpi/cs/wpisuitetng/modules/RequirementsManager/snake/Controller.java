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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;

/**
 * The Class Controller.
 */
public class Controller {

	private int xMax = 0, yMax = 0;
	
	SnakePanel panel;
	Snake snake;
	GamePanel game;
	int score = 0;
	boolean gameRunning = true;
	protected Timer moveTimer;
	
	/**
	 * Instantiates a new controller.
	 *
	 * @param panel the panel
	 * @param snake the snake
	 * @param game the game
	 */
	public Controller(SnakePanel panel, Snake snake, GamePanel game) {
		this.panel = panel;
		this.snake = snake;
		this.game = game;
		this.panel.addKeyListener(new KeyInput(this.snake));
		panel.setSpots(snake.getSpots());
		
		xMax = 31;
		yMax = 27;
		
		
		for (int x = 0; x <= xMax; x ++){
			new Wall(new Spot(x, 0));
			new Wall(new Spot(x, yMax));
		}
		for (int y = 0 ; y <= yMax; y ++){
			new Wall(new Spot(0, y));
			new Wall(new Spot(xMax, y));
		}
		
		reset();
		
		
		moveTimer = new Timer();
		moveTimer.schedule(new MoveTask(snake, panel), 1000,100);
		
		updateHighScore(0,"Unknown");
		
		
	}

	/**
	 * Update high score.
	 *
	 * @param score the score
	 * @param name the name
	 */
	private void updateHighScore(int score, String name){
		SnakeScoreController sc = new SnakeScoreController(this);
		SnakeModel m = new SnakeModel(score,name);
		sc.getHighScore(m);
	}
	
	/**
	 * Reset.
	 */
	public void reset(){
		lastSave = true;
		
		panel.setGameOver(false);
		score = 0;
		game.scoreBody.setText(""+score);
		gameRunning = true;
		snake.reset(new Spot(5,5));
		panel.setSnakeColor(new Color(100,200,150));
		panel.setFoodColor(new Color(50,255,100));
		panel.setWallColor(new Color(25,25,25));
		Food.all.clear();
		spawnFood(null);
	}
	
	/**
	 * Gets the rand.
	 *
	 * @param min the min
	 * @param max the max
	 * @return the rand
	 */
	public int getRand(int min, int max){
		
		int diff = max-min;
		double d = Math.random()*diff;
		int answer = min + (int)d;
		
		
		return answer;
	}
	
	/**
	 * Eat food.
	 *
	 * @param eat the eat
	 */
	public void eatFood(Food eat){
		score += eat.score;
		game.scoreBody.setText(""+score);
		spawnFood(eat);
	}
	
	/**
	 * Spawn food.
	 *
	 * @param old the old
	 */
	public void spawnFood(Food old){
		if (old!=null){
			Food fresh = new Food(new Spot(getRand(2,xMax-1), getRand(2,yMax-1)));
			
			if (score > 5){
				fresh.score++;
				fresh.setColor(Color.BLUE);
			}
			if (score > 15){
				fresh.score++;
				fresh.setColor(Color.CYAN);
			}
			if (score > 25){
				fresh.score++;
				fresh.setColor(Color.GREEN);
			}
			if (score > 40){
				fresh.score++;
				fresh.setColor(Color.MAGENTA);
			}
			if (score > 55){
				fresh.score++;
				fresh.setColor(Color.ORANGE);
			}
			if (score > 75){
				fresh.score++;
				fresh.setColor(Color.PINK);
			}
			if (score > 100){
				fresh.score++;
				fresh.setColor(Color.RED);
			}
			if (score > 150){
				fresh.score++;
				fresh.setColor(Color.YELLOW);
			}
			if (score > 200){
				fresh.score++;
				fresh.setColor(new Color(152, 60, 255));
			}
			for (int i = 0 ; i < snake.spots.size(); i ++)
				if (snake.spots.get(i).equals(fresh.spot)){
					eatFood(fresh);
					Food.all.remove(fresh);
				}
		} else{
			new Food(new Spot(10,10));
		}
	}
	
	/**
	 * The Class KeyInput.
	 */
	class KeyInput extends KeyAdapter{
		
		Snake snake;
		
		/**
		 * Instantiates a new key input.
		 *
		 * @param snake the snake
		 */
		public KeyInput(Snake snake){
			this.snake = snake;
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyPressed(KeyEvent e){
			//37 = left
			//38 = up
			//39 = right
			//40 = down
			
			if (e.getKeyChar() == ' '){
				if(!gameRunning)
					reset(); 
			} else if (e.getKeyCode() >= Snake.LEFT && e.getKeyCode() <= Snake.DOWN){
				boolean isPlayerDumb = (snake.direction == Snake.LEFT && e.getKeyCode() == Snake.RIGHT)
						|| (snake.direction == Snake.RIGHT && e.getKeyCode() == Snake.LEFT)
						|| (snake.direction == Snake.UP && e.getKeyCode() == Snake.DOWN)
						|| (snake.direction == Snake.DOWN && e.getKeyCode() == Snake.UP);
				if (!isPlayerDumb){
					if(!snake.isHasMoved()){
						if(snake.getNextDirection() == 0)
							snake.setNextDirection(e.getKeyCode());
					}
					else{
						snake.setDirection(e.getKeyCode());
					}
					snake.setHasMoved(false);
				}
			}
		}
	}
	
	/**
	 * The Class NewFoodTask.
	 */
	class NewFoodTask extends TimerTask{

		/* (non-Javadoc)
		 * @see java.util.TimerTask#run()
		 */
		@Override
		public void run() {
		}
		
	}
	
	/**
	 * The Class MoveTask.
	 */
	class MoveTask extends TimerTask{

		Snake snake;
		SnakePanel panel;
		
		/**
		 * Instantiates a new move task.
		 *
		 * @param snake the snake
		 * @param panel the panel
		 */
		public MoveTask(Snake snake, SnakePanel panel){
			this.snake = snake;
			this.panel = panel;
			
		}
		
		/* (non-Javadoc)
		 * @see java.util.TimerTask#run()
		 */
		@Override
		public void run() {
			if (gameRunning){
				snake.move();
				panel.setSpots(snake.getSpots());
			}
		
		}
		
	}

	
	
	/**
	 * Checks if is game running.
	 *
	 * @return the gameRunning
	 */
	public boolean isGameRunning() {
		return gameRunning;
	}

	
	private boolean lastSave = true;
	
	/**
	 * Sets the game running.
	 *
	 * @param gameRunning the gameRunning to set
	 */
	public void setGameRunning(boolean gameRunning) {
		if (!gameRunning && lastSave){
			lastSave = false;
			updateHighScore(score, ConfigManager.getConfig().getUserName());
		}
		this.gameRunning = gameRunning;
		panel.setGameOver(!gameRunning);
	}

	/**
	 * Gets the x max.
	 *
	 * @return the xMax
	 */
	public int getXMax() {
		return xMax;
	}

	/**
	 * Gets the y max.
	 *
	 * @return the yMax
	 */
	public int getYMax() {
		return yMax;
	}

	/**
	 * End.
	 */
	public void end() {
		
		moveTimer.cancel();
	}

	
	
}
