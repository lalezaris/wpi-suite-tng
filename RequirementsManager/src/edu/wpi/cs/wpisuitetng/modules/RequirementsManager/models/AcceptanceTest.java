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
 *  Chris Dunkers
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.AcceptanceTestsView.AcceptanceStatus;
import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.AcceptanceTestsView.AcceptanceStatus.*;

/**
 * Insert Description Here
 *
 * @author Chris Dunkers
 *
 * @version Mar 26, 2013
 *
 */
public class AcceptanceTest {

	private static int nextTest = 1;
	protected int id;
	protected String description;
	protected AcceptanceStatus status;
	
	public void AcceptanceTest(String description){
		this.id = nextTest++;
		this.description = description;
		this.status = INCOMPLETE;
	}
	
	
}
