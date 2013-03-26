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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * Insert Description Here
 *
 * @author Chris Dunkers
 *
 * @version Mar 26, 2013
 *
 */
public class HistoryLog {

	private List<Log> history;
	
	HistoryLog(){
		this.history = new ArrayList<Log>();
	}
	
	/**
	 * Adds a Log to the history  
	 * 
	 * @param date the date the log was creator
	 * @param editor the user who made the log
	 * @param message the message to be displayed 
	 */
	public void addLog(Date date, User editor, String message){
		Log entry = new Log(date, editor, message);
		this.history.add(entry);
	}
	
	public List<Log> getHistory(){
		return this.history;
	}
	
	public void setHistory(List<Log> hist){
		this.history = hist;
	}
	
}
