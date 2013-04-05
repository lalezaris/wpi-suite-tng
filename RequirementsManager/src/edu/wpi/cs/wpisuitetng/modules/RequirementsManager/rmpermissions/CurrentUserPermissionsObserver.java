package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions;

import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

public class CurrentUserPermissionsObserver implements RequestObserver{



	
	@Override
	public void responseSuccess(IRequest iReq) {
		
		Request request = (Request) iReq;
		ResponseModel response = request.getResponse();
		
		GsonBuilder builder = new GsonBuilder();
		User[] users = builder.create().fromJson(response.getBody(), User[].class);
		
		//Figure out which coreUser's name matches the known current user's name.
		User user = null;
		for (int i = 0 ; i < users.length ; i ++){
			//System.out.println("Does user: " + users[i].getName() + " = " + ConfigManager.getConfig().getUserName());
			if (ConfigManager.getConfig().getUserName().equals(users[i].getUsername()))
			{
				user = users[i];
				break;
			}
		}
		//System.out.println("User Perm: Got users");
		CurrentUserPermissions.setUsers(user, users);
		
	}

	@Override
	public void responseError(IRequest iReq) {
		System.out.println("Failed to retrieve current user permissions3");
		
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.out.println("Failed to retrieve current user permissions4");
		
	}

}
