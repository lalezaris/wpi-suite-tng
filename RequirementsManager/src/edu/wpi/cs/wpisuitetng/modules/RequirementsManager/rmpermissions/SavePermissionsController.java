package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.UserPermission;

import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;

import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class SavePermissionsController {
	/** The view object containing the request fields */

	private UserPermissionPanel panel;
	
	/**
	 * Construct a new handler for the given view
	 * @param view the view containing the request fields
	 */
	public SavePermissionsController(UserPermissionPanel panel) {
		//this.view = view;
		this.panel = panel;
	}


	/**
	 * Call this to save a Permission
	 * @param permission the permission to save to the database
	 * @param mode chose to update, or make a new one
	 */
	public void save(UserPermission permission, PermissionSaveMode mode) {
		//final RequirementPanel panel = (RequirementPanel) view.getRequirementPanel();
		//final RequestObserver requestObserver = (permission.getEditMode() == Mode.CREATE) ? new CreateRequirementRequestObserver(view) : new UpdateRequirementRequestObserver(view);
		Request request;
		
		if (mode == PermissionSaveMode.NEW)
			request = Network.getInstance().makeRequest("requirementsmanager/permissions", HttpMethod.PUT);
		else request = Network.getInstance().makeRequest("requirementsmanager/permissions", HttpMethod.POST);
			String JsonRequest = permission.toJSON();
			request.setBody(JsonRequest);
			System.out.println("Sending PERM to server:" +JsonRequest );
			request.addObserver(new UpdatePermissionObserver(panel));
			request.send();
			//close tab
		
	} 
}
