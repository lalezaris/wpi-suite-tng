package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.snake;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {

	private int xMax = 0, yMax = 0;
	
	SnakePanel panel;
	Snake snake;
	GamePanel game;
	int score = 0;
	boolean gameRunning = true;
	
	
	public Controller(SnakePanel panel, Snake snake, GamePanel game) {
		this.panel = panel;
		this.snake = snake;
		this.game = game;
		System.out.println("outout");
		this.panel.addKeyListener(new KeyInput(this.snake));
		panel.setSpots(snake.getSpots());
		
		this.xMax = 31;//panel.getWidth() / panel.gridSize;
		this.yMax = 27;//panel.getHeight() / panel.gridSize;
		
		
		for (int x = 0; x <= xMax; x ++){
			new Wall(new Spot(x, 0));
			new Wall(new Spot(x, yMax));
		}
		for (int y = 0 ; y <= yMax; y ++){
			new Wall(new Spot(0, y));
			new Wall(new Spot(xMax, y));
		}
		
		reset();
		
		
		Timer moveTimer = new Timer();
		moveTimer.schedule(new MoveTask(snake, panel), 1000,100);
		
		spawnFood(null);
		
		
	}

	public void reset(){
		panel.setGameOver(false);
		score = 0;
		game.score.setText("SCORE:" + score);
		gameRunning = true;
		snake.reset(new Spot(5,5));
		panel.setSnakeColor(new Color(100,200,150));
		panel.setFoodColor(new Color(50,255,100));
		panel.setWallColor(new Color(25,25,25));
	}
	
	public int getRand(int min, int max){
		
		int diff = max-min;
		double d = Math.random()*diff;
		int answer = min + (int)d;
		
		
		return answer;
	}
	
	public void eatFood(Food eat){
		score += eat.score;
		game.score.setText("SCORE:" + score);
		spawnFood(eat);
	}
	
	public void spawnFood(Food old){
		if (old!=null){
			Food fresh = new Food(new Spot(getRand(2,xMax-1), getRand(2,yMax-1)));
			
			if (score > 5)
				fresh.score++;
			if (score > 15)
				fresh.score++;
			if (score > 25)
				fresh.score++;
			if (score > 40)
				fresh.score++;
			if (score > 55)
				fresh.score++;
			if (score > 75)
				fresh.score++;
			if (score > 100)
				fresh.score++;
			if (score > 150)
				fresh.score++;
			if (score > 200)
				fresh.score++;
			for (int i = 0 ; i < snake.spots.size(); i ++)
				if (snake.spots.get(i).equals(fresh.spot)){
					eatFood(fresh);
					Food.all.remove(fresh);
				}
		} else{
			new Food(new Spot(10,10));
		}
	}
	
	class KeyInput extends KeyAdapter{
		
		Snake snake;
		
		public KeyInput(Snake snake){
			this.snake = snake;
		}
		@Override
		public void keyPressed(KeyEvent e){
			//37 = left
			//38 = up
			//39 = right
			//40 = down
			//System.out.println(e.getKeyChar());
			if (e.getKeyChar() == ' '){
				reset(); 
			} else if (e.getKeyCode() >= Snake.LEFT && e.getKeyCode() <= Snake.DOWN)
				snake.setDirection(e.getKeyCode());
		}
	}
	
	class NewFoodTask extends TimerTask{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class MoveTask extends TimerTask{

		Snake snake;
		SnakePanel panel;
		public MoveTask(Snake snake, SnakePanel panel){
			this.snake = snake;
			this.panel = panel;
			
		}
		
		@Override
		public void run() {
			if (gameRunning){
				snake.move();
				panel.setSpots(snake.getSpots());
			}
		
		}
		
	}

	
	
	/**
	 * @return the gameRunning
	 */
	public boolean isGameRunning() {
		return gameRunning;
	}

	/**
	 * @param gameRunning the gameRunning to set
	 */
	public void setGameRunning(boolean gameRunning) {
		this.gameRunning = gameRunning;
		this.panel.setGameOver(!gameRunning);
	}

	/**
	 * @return the xMax
	 */
	public int getXMax() {
		return xMax;
	}

	/**
	 * @return the yMax
	 */
	public int getYMax() {
		return yMax;
	}
	
}
