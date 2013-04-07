package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar;


import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.IOnPermissionUpdate;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;


public class PermissionDisplayUpdater implements IOnPermissionUpdate{

	ToolbarPanel TBpanel;
	
	public PermissionDisplayUpdater(ToolbarPanel t){
		this.TBpanel = t;
	}
	
	@Override
	public void onUpdate() {
		TBpanel.setPermissionText("Permission Level: " + CurrentUserPermissions.getCurrentUserPermission().toString());
	}

	
}
