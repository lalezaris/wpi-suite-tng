/**************************************************
 * This file was developed for CS3733: Software Engineering The course was taken
 * at Worcester Polytechnic Institute.
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 	Lauren Kahn
 * 					Michael Perrone
 * 					Ned Shelton
 * 					Xia Li
 **************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import java.util.Date;

/**
 * Persistent Model that represents a Comment on a Requirement.
 * 
 * @author Lauren Kahn
 * @author Michael Perrone
 * 
 * @version Mar 20, 2013
 * 
 */
public class Note {
	private String body;
	private String creator;
	private Date creationDate;

	/**
	 * Create a Note with the default values.
	 * 
	 */
	public Note() {
		this("","[USER UNKNOWN]");
	}

	/**
	 * Create a Note with the given properties. Other properties are the same as
	 * the default constructor.
	 * 
	 * @param user
	 *            The User who created the Note
	 * @param body
	 *            The message body of the Note
	 */
	public Note(String body, String creator) {
		this.body = body;
		this.creator = creator;
		this.creationDate = new Date();
	}

	/**
	 * Get the body.
	 * 
	 * @return The body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Set the body.
	 * 
	 * @param The body
	 *
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * Get the creator.
	 * 
	 * @return The creator
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * Get the creationDate.
	 * 
	 * @return The creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Set the creationDate
	 * 
	 * @param creationDate
	 *            : sets the creationDate
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
