package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.snake;

import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;



public class SnakeScoreController {

	Controller controller;
	public SnakeScoreController(Controller controller){
		this.controller = controller;
	}
	
	public void getHighScore(SnakeModel model){
		Request request;
		
		request = Network.getInstance().makeRequest("requirementsmanager/snake", HttpMethod.PUT);
		String JsonRequest = model.toJSON();
		request.setBody(JsonRequest);
		
		
		request.addObserver(new SnakeScoreObserver(this));
		request.send();
	}
	
	public void gotHighScore(SnakeModel models) {
		
		if (models!=null){
			controller.game.highScoreBody.setText(models.getName() + ": " +models.getScore());
		}

		
	}
	
}
