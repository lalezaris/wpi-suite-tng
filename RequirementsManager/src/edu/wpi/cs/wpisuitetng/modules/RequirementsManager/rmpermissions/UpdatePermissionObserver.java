package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.google.gson.GsonBuilder;


import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.UserPermission;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

public class UpdatePermissionObserver implements RequestObserver{

	UserPermissionPanel panel;
	
	public UpdatePermissionObserver(UserPermissionPanel panel){
		this.panel = panel;
	}
	
	
	@Override
	public void responseSuccess(IRequest iReq) {
		
		Request request = (Request) iReq;

		// get the response from the request
		ResponseModel response = request.getResponse();

		// print the body
		System.out.println("Received response: " + response.getBody()); //TODO change this to logger
		//if (response.getStatusCode() == 200) {
			// parse the Requirement from the body
			final UserPermission per = UserPermission.fromJSON(response.getBody());

			System.out.println("Got Per: " + per.getUsername() + " has " + per.getPermissions());
		
			panel.addPermission(per);
		//}
			CurrentUserPermissions.updateCurrentUserPermissions();

	}

	@Override
	public void responseError(IRequest iReq) {
		System.out.println("Per response error");
		System.out.println(
				"Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage()); 
				
		
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.out.println("Per response fail");
		
	}

}
