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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.model;

import com.google.gson.Gson;
/**
 * Persistent Model that represents a Note on a Defect 
 *
 * @author Tushar Narayan
 *
 * @version Mar 17, 2013
 *
 */
public class NoteEvent extends RequirementEvent{
	private int requirementId;
	private String body;
	
	/**
	 * Create a Note with default properties.
	 */
	public NoteEvent() {
		type = EventType.NOTE;
		requirementId = -1;
		body = "";
	}
	
	/**
	 * Create a Note with the given properties.
	 * Other properties are the same as the default constructor.
	 * 
	 * @param defectId the id of the Requirement the Note is associated with
	 * @param body the message body of the Comment
	 */
	public NoteEvent(int requirementId, String body) {
		this();
		this.requirementId = requirementId;
		this.body = body;
	}
	
	
	/**
	 * @return the requirementId of the Requirement this Note is associated with
	 */
	public int getRequirementId() {
		return requirementId;
	}
	/**
	 * @param requirementId: the requirementId to set
	 */
	public void setRequirementId(int requirementId) {
		this.requirementId = requirementId;
	}
	/**
	 * @return the body of this Note
	 */
	public String getBody() {
		return body;
	}
	/**
	 * @param body: the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}
	
	@Override
	public String toJSON() {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(this, NoteEvent.class);
		return json;
	}

	/**
	 * Converts the given JSON string into a Comment
	 * @param json JSON string containing a serialized Comment
	 * @return a Comment deserialized from the given JSON string
	 */
	public static NoteEvent fromJson(String json) {
		Gson parser = new Gson();
		return parser.fromJson(json, NoteEvent.class);
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}
}
