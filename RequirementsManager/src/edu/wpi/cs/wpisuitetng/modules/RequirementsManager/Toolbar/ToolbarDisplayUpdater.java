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
 * Update the toobar buttons according to the current user permission.
 *
 * @author Tushar Narayan
 *
 * @version Apr 7, 2013
 *
 */
public class ToolbarDisplayUpdater implements IOnPermissionUpdate{

	ToolbarPanel TBpanel;
	
	/**
	 * Construct ToolbarDisplayUpdater.
	 * 
	 * @param t A ToolbarPanel
	 */
	public ToolbarDisplayUpdater(ToolbarPanel t){
		this.TBpanel = t;
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.IOnPermissionUpdate#onUpdate()
	 */
	@Override
	public void onUpdate() {
		TBpanel.setToolbarDisplay(CurrentUserPermissions.getCurrentUserPermission().toString());
	}

}
