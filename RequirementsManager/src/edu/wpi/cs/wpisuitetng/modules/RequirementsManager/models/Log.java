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

import java.util.Date;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * Insert Description Here
 *
 * @author Chris Dunkers
 *
 * @version Mar 26, 2013
 *
 */
public class Log {
	
	private String message;
	private User editor;
	private Date date;
	private int logNumber;
	private static int nextLogNumber = 1;
	
	/**
	 * Constructor. the date the log was made, the user who made it and the message to be displayed
	 * 
	 * @param date the date the log was made
	 * @param editor the user who made the changes
	 * @param message the message to be displayed
	 */
	public Log(Date date, User editor, String message){
		this.date = date;
		this.editor = editor;
		this.message = message;
		this.logNumber = nextLogNumber++;		
	}
	
	/**
	 * Resets the log number
	 * 
	 */
	static void resetLogNumber(){
		nextLogNumber = 1;
	}
	
	/**
	 * Gets the log number 
	 * 
	 * @return the log number
	 */
	public int getLogNumber(){
		return this.logNumber;
	}
	
	/**
	 * gets the message to be displayed 
	 * 
	 * @return the message displayed
	 */
	public String getMessage(){
		return this.message;
	}
	
	/**
	 * sets the message to be displayed
	 * 
	 * @param message the message wanted displayed
	 */
	public void setMessage(String message){
		this.message = message;
	}
	
	/**
	 * Gets the user who made the edits
	 * 
	 * @return the user who made the edits
	 */
	public User getEditor(){
		return this.editor;
	}
	
	/**
	 * Sets the user who made the edits
	 * 
	 * @param editor the user who made the edits
	 */
	public void setMessage(User editor){
		this.editor = editor;
	}
	
	/**
	 * The date the changes were made
	 * 
	 * @return the date changes were made
	 */
	public Date getDate(){
		return this.date;
	}
	
	/**
	 * sets the date for when changes were made
	 * 
	 * @param date the date changes were made
	 */
	public void setMessage(Date date){
		this.date = date;
	}

}
