package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions;

import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.UserPermission;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

public class PermissionsObserver implements RequestObserver{

	private UserPermissionPanel panel;
	public PermissionsObserver(UserPermissionPanel panel){
		this.panel = panel;
	}
	
	@Override
	public void responseSuccess(IRequest iReq) {
		Request request = (Request) iReq;
		ResponseModel response = request.getResponse();
		
		GsonBuilder builder = new GsonBuilder();
		UserPermission[] all = builder.create().fromJson(response.getBody(), UserPermission[].class);
		this.panel.setAllPermissions(all);
		
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
