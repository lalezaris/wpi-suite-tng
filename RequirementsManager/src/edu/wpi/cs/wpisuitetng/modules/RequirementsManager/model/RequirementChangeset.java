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
 *  Michael Perrone
 *  Lauren Kahn
**************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.model;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;


import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * This is an event to encapsulate a change to a Requirement.
 */
public class RequirementChangeset extends RequirementEvent {

	private Map<String, FieldChange<?>> changes;

	
	/**
	 * Construct a RequirementChangeset with default properties.
	 */
	public RequirementChangeset() {
		type = EventType.CHANGESET;
		changes = new HashMap<String, FieldChange<?>>();
	}
	
	/**
	 * Construct a RequirementChangeset with the given properties.
	 * @param user the User responsible for this change
	 */
	public RequirementChangeset(User user) {
		this();
		this.user = user;
	}

	/**
	 * @return the map of field names to changes (Assignee -> (Bob, Joe))
	 */
	public Map<String, FieldChange<?>> getChanges() {
		return changes;
	}

	/**
	 * @param changes the changes to set
	 */
	public void setChanges(Map<String, FieldChange<?>> changes) {
		this.changes = changes;
	}


	/** toJSON : serializing this Model's contents into a JSON/GSON string
	 * @return	A string containing the serialized JSON representation of this Model.
	 */
	@Override
	public String toJSON() {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(this, RequirementChangeset.class);
		return json;
	}
	
	
	
	
	

}
