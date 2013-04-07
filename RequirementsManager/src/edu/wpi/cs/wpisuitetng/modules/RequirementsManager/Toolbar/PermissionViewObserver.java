package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar;

import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.UserPermission;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RMPermissionsLevel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller.SetUpPermissionsPanelController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

public class PermissionViewObserver implements RequestObserver{

	ToolbarPanel TBpanel;
	
	public PermissionViewObserver(ToolbarPanel t){
		this.TBpanel = t;
	}
	
	@Override
	public void responseSuccess(IRequest iReq) {
		//TBpanel.setNameText("User: " + CurrentUserPermissions.getUser().getUsername());
		TBpanel.setPermissionText("Permission Level: " + CurrentUserPermissions.getCurrentUserPermission().toString());
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
