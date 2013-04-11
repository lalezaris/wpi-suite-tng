package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.observers;

import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarChartView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

public class IterationObserver implements RequestObserver{

	BarChartView view;
	public IterationObserver(BarChartView view){
		this.view = view;
	}
	
	@Override
	public void responseSuccess(IRequest iReq) {
		Request request = (Request) iReq;
		ResponseModel response = request.getResponse();
		
		GsonBuilder builder = new GsonBuilder();
		Iteration[] iterations = builder.create().fromJson(response.getBody(), Iteration[].class);
		//this.panel.setAllusers(users);
		this.view.recieveServerIterations(iterations);
		
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
