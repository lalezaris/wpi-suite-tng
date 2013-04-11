package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarChartView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.observers.IterationObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.observers.UserObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class IterationController {

	BarChartView view;
	public IterationController(BarChartView view){
		this.view = view;
	}
	public void retreive(){
		Request request = Network.getInstance().makeRequest("requirementsmanager/iteration", HttpMethod.GET);
		request.addObserver(new IterationObserver(view));
		request.send();
	}
}
