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
 *  Tushar
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.model;

import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * Implementations of this interface represent some kind of event in a requirement.
 * For example, the addition of a note or the modification of fields.
 *
 * @author Tushar Narayan
 *
 * @version Mar 17, 2013
 *
 */
public abstract class RequirementEvent extends AbstractModel{

	public enum EventType {
		NOTE,
		CHANGESET
	};
	
	/**
	 * The type of event this is.  Subclasses must specify this in order to be deserialized properly.
	 */
	protected EventType type;
	
}
