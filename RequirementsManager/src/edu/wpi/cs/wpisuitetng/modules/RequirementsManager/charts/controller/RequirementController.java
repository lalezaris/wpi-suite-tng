package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarChartView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.observers.RequirementObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.observers.UserObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class RequirementController {

	BarChartView view;
	public RequirementController(BarChartView view){
		this.view = view;
		
	}
	
	public void retrieve(){
		Request request = Network.getInstance().makeRequest("requirementsmanager/requirement", HttpMethod.GET);
		request.addObserver(new RequirementObserver(view));
		request.send();
	}
	
}
