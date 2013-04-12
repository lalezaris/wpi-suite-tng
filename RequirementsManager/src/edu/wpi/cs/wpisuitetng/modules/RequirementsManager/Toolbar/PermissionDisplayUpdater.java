/**************************************************
 * This file was developed for CS3733: Software Engineering
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html 
 *
 * Contributors:
 *  Tushar Narayan
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.IOnPermissionUpdate;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;

/**
 * Update the user permission information displayed on Toolbar.
 * 
 * @author Tushar Narayan
 *
 */
public class PermissionDisplayUpdater implements IOnPermissionUpdate{

	ToolbarPanel TBpanel;
	
	/**
	 * Construct a PermissionDisplayUpdater.
	 * 
	 * @param t A ToobarPanel
	 */
	public PermissionDisplayUpdater(ToolbarPanel t){
		this.TBpanel = t;
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.IOnPermissionUpdate#onUpdate()
	 */
	@Override
	public void onUpdate() {
		TBpanel.setPermissionText("Permission Level: " + CurrentUserPermissions.getCurrentUserPermission().toString());
	}
}
