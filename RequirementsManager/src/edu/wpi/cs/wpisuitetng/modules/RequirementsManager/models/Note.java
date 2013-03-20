/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Andrew Hurle
 *    Chris Casola
 *    Mike Della Donna
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;
import java.util.Date;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * Persistent Model that represents a Comment on a Requirement
 */
public class Note {
	private String title;
	private String body;
	private User creator, lastModifiedUser;
	private Date creationDate, lastModifiedDate;
	
	public Note(){
		this.title = "";
		this.body = "";
		this.creator = new User("","","",-1);
		this.lastModifiedUser = creator;
		this.creationDate = new Date();
		this.lastModifiedDate = new Date();
	}
	/**
	 * Create a Note with the given properties.
	 * Other properties are the same as the default constructor.
	 * 
	 * @param user the User who created the Note
	 * @param body the message body of the Note
	 */
	public Note(String title, String body, User creator) {
		this();
		this.title = title;
		this.body = body;
		this.creator = creator;
	}

	
	/**
	 * Gets the title
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}


	/**
	 * Sets the title
	 * @param title: sets the title 
	 */
	public void setTitle(String title) {
		this.title = title;
	}


	/**
	 * Gets the body
	 * @return the body
	 */
	public String getBody() {
		return body;
	}


	/**
	 * Sets the body
	 * @param body: sets the body 
	 */
	public void setBody(String body) {
		this.body = body;
	}


	/**
	 * Gets the creator
	 * @return the creator
	 */
	public User getCreator() {
		return creator;
	}


	/**
	 * Gets the lastModifiedUser
	 * @return the lastModifiedUser
	 */
	public User getLastModifiedUser() {
		return lastModifiedUser;
	}


	/**
	 * Sets the lastModifiedUser
	 * @param lastModifiedUser: sets the lastModifiedUser 
	 */
	public void setLastModifiedUser(User lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}


	/**
	 * Gets the creationDate
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}


	/**
	 * Sets the creationDate
	 * @param creationDate: sets the creationDate 
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


	/**
	 * Gets the lastModifiedDate
	 * @return the lastModifiedDate
	 */
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}


	/**
	 * Sets the lastModifiedDate
	 * @param lastModifiedDate: sets the lastModifiedDate 
	 */
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
}
