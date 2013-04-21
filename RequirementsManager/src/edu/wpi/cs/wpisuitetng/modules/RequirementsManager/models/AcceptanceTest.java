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
 * Michael French
**************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * The Class to hold an AcceptanceTest object.
 * 
 * @author Michael French
 * 
 */
public class AcceptanceTest extends AbstractModel{

	private String title;
	private String body;
	private String status;
	
	/**
	 * Instantiates a new acceptance test.
	 *
	 * @param title the title
	 * @param body the body
	 */
	public AcceptanceTest(String title, String body){
		this.title = title;
		this.body = body;
		this.status = "";
	}
	
	/**
	 * Instantiates a new acceptance test.
	 *
	 * @param title the title
	 * @param body the body
	 */
	public AcceptanceTest(String title, String status, String body){
		this.title = title;
		this.body = body;
		this.status = status;
	}
	
	/**
	 * Instantiates a new acceptance test.
	 */
	public AcceptanceTest(){
		this.title = "";
		this.body = "";
		this.status = "";
	}
	
	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle(){
		return this.title;
	}
	
	/**
	 * Sets the title.
	 *
	 * @param t the new title
	 */
	public void setTitle(String t){
		this.title = t;
	}
	
	/**
	 * Gets the body.
	 *
	 * @return the body
	 */
	public String getBody(){
		return  this.body;
	}
	
	/**
	 * Sets the body.
	 *
	 * @param b the new body
	 */
	public void setBody(String b){
		this.body = b;
	}
	
	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus(){
		System.out.println(status);
		if ((this.status.compareTo("") == 0) || (this.status.compareTo("Blank") == 0))
			return "";
		if(this.status.compareTo("Passed") == 0)
			return "Passed";
		if(this.status.compareTo("Failed") == 0)
			return "Failed";
		//otherwise
		this.status = "";
		System.out.println("Status was invalid, Corrected to \"\"" );
		return this.status;
		
	}
	
	/**
	 * get index of status
	 */
	public int getStatusIndex(){
		if (this.status.compareTo("Passed") == 0)
			return 1;
		if (this.status.compareTo("Failed") == 0)
			return 2;
		return 0;
	}
	
	/**
	 * Sets the status.
	 *
	 * @param s the new status
	 */
	public void setStatus(String s){
		if (s.compareTo("") == 0 || s.compareTo("Passed") == 0 || s.compareTo("Failed") == 0){
			this.status = s;
		}else{
			System.out.println("Invalid Status: " + s);
		}
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
	 */
	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	
	//NOTE: toJSON and fromJSON are copy-pasta'd from Requirement
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON()
	 */
	@Override
	public String toJSON() {

		String json;
		Gson gson = new Gson();
		json = gson.toJson(this, AcceptanceTest.class);

		return json;
	}
	
	/**
	 * Convert a json String into an AcceptanceTest.
	 *
	 * @param json the json string
	 * @return the acceptance test
	 */
	public static AcceptanceTest fromJSON(String json) {
		GsonBuilder builder = new GsonBuilder();
		addGsonDependencies(builder);
		return builder.create().fromJson(json, AcceptanceTest.class);
	}
	
	/**
	 * Add dependencies necessary for Gson to interact with this class.
	 *
	 * @param builder Builder to modify
	 */
	public static void addGsonDependencies(GsonBuilder builder) {
		
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(java.lang.Object)
	 */
	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String toString(){
		if (this.status.compareTo("") == 0 || this.status.compareTo("Blank") == 0){
			return " >" + this.getTitle();
		}else{
			return " >" + this.getTitle() + " (" + this.status + ")";
		}
	}

}
