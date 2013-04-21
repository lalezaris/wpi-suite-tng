package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.snake;

import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

public class SnakeScoreObserver implements RequestObserver {

	SnakeScoreController controller;
	public SnakeScoreObserver(SnakeScoreController controller){
		this.controller = controller;
	}
	
	@Override
	public void responseSuccess(IRequest iReq) {
		Request request = (Request) iReq;
		ResponseModel response = request.getResponse();
		
		GsonBuilder builder = new GsonBuilder();
		SnakeModel model = builder.create().fromJson(response.getBody(), SnakeModel.class);
		//this.panel.setAllusers(users);
		this.controller.gotHighScore(model);

	}

	@Override
	public void responseError(IRequest iReq) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		// TODO Auto-generated method stub

	}

}
