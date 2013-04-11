package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarChartView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.observers.UserObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller.PermissionSaveMode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.UpdatePermissionObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class UserController {
	BarChartView view;
	public UserController(BarChartView view){
		this.view = view;
	}

	public void retrieve(){

		Request request = Network.getInstance().makeRequest("core/user", HttpMethod.GET);
		request.addObserver(new UserObserver(view));
		request.send();
	}

}
