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

import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;



/**
 * The Class SnakeScoreController.
 */
public class SnakeScoreController {

	Controller controller;
	
	/**
	 * Instantiates a new snake score controller.
	 *
	 * @param controller the controller
	 */
	public SnakeScoreController(Controller controller){
		this.controller = controller;
	}
	
	/**
	 * Gets the high score.
	 *
	 * @param model the model
	 */
	public void getHighScore(SnakeModel model){
		Request request;
		
		request = Network.getInstance().makeRequest("requirementsmanager/snake", HttpMethod.PUT);
		String JsonRequest = model.toJSON();
		request.setBody(JsonRequest);
		
		
		request.addObserver(new SnakeScoreObserver(this));
		request.send();
	}
	
	/**
	 * Got high score.
	 *
	 * @param models the models
	 */
	public void gotHighScore(SnakeModel models) {
		
		if (models!=null){
			controller.game.highScoreBody.setText(models.getName() + ": " +models.getScore());
		}

		
	}
	
}
