package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions;

import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

public class UsersObserver implements RequestObserver{

	UserPermissionPanel panel;
	
	public UsersObserver(UserPermissionPanel panel){
		this.panel = panel;
	}
	
	
	@Override
	public void responseSuccess(IRequest iReq) {
		Request request = (Request) iReq;
		ResponseModel response = request.getResponse();
		
		GsonBuilder builder = new GsonBuilder();
		User[] users = builder.create().fromJson(response.getBody(), User[].class);
		this.panel.setAllusers(users);
		
		CurrentUserPermissions.updateCurrentUserPermissions();
		
//		System.out.println("FOUND THESE USERS...");
//		for (int i = 0 ; i < this.allUsers.length ; i ++)
//			System.out.println("USER:" + this.allUsers[i].getName());
		
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
