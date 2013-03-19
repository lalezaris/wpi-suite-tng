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
 *  Lauren Kahn
 *  Mike Perrone
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.model;

import java.util.Date;

import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

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
	
	protected Date date = new Date();
	protected User user = new User("", "", "", -1);
	
	/**
	 * The type of event this is.  Subclasses must specify this in order to be deserialized properly.
	 */
	protected EventType type;
	
	/**
	 * @return The Date when this event happened
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * @param date The Date of the Event to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * @return The User responsible for this event
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * @param user The User responsible for the event to set
	 */
	public void setUser(User user) {
		this.user = user;
	}	
	
	/**
	 * Given a builder, add anything to it that's necessary for Gson to interact with this class.
	 * 
	 * @param builder The builder to modify
	 */
	public static void addGsonDependencies(GsonBuilder builder) {
		builder.registerTypeAdapter(RequirementEvent.class, new RequirementEventDeserializer());
		builder.registerTypeAdapter(RequirementChangeset.class, new RequirementChangesetDeserializer());
	}
	

	
	//The following methods are not actually being used for our purposes.
	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}
	
	// this model will only be created server side and then retrieved as part of a Defect in the future
	// so I'm not sure if this is necessary
	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

}
