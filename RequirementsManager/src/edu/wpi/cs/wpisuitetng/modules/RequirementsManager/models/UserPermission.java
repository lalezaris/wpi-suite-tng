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
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RMPermissionsLevel;

/**
 * The model to hold a username and the permission associated with that username.
 *
 * @author Michael Perrone
 *
 * @version Mar 31, 2013
 *
 */
public class UserPermission extends AbstractModel {

	private String username;
	private RMPermissionsLevel permissions;
	private int id;
	private String message;
	
	/**
	 * Constructor to input username and permissions
	 * 
	 * @param userName username to set the permissions to
	 * @param permissions the permissions to set for the username
	 */
	public UserPermission(String username, RMPermissionsLevel permissions) {
		super();
		this.username = username;
		this.permissions = permissions;
		this.id = -1;
	}	
	
	/**
	 * Default Constructor 
	 * 
	 */
	public UserPermission() {
		super();
		this.username = "";
		this.permissions = RMPermissionsLevel.NONE;
		this.id = -1;
	}
	
	/**
	 * Gets the username
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets Permissions
	 * 
	 * @return the permissions
	 */
	public RMPermissionsLevel getPermissions() {
		return permissions;
	}

	/**
	 * Sets permissions
	 * 
	 * @param permissions: the permissions to set
	 */
	public void setPermissions(RMPermissionsLevel permissions) {
		this.permissions = permissions;
	}
	
	
	/**
	 * Sets id
	 * 
	 * @param id id to set
	 */
	public void setId(int id){
		this.id = id;
	}
	/**
	 * Gets id
	 * 
	 * @return id
	 */
	public int getId(){
		return this.id;
	}
	
	/**
	 * Gets message
	 * 
	 * @return the message
	 */
	public String getMessage(){
		return this.message;
	}
	
	/**
	 * Sets message
	 * 
	 * @param message message to set
	 */
	public void setMessage(String message){
		this.message = message;
	}

	/*
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	@Override
	public void save() {
		
	}

	/* 
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
	 */
	@Override
	public void delete() {
		
	}

	
	/**
	 * Converts this User to a JSON string
	 * @return a string in JSON representing this User
	 */
	@Override
	public String toJSON() {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(this, UserPermission.class);

		return json;
	}	
	
	/**
	 * Converts the given list of Users to a JSON string.
	 *
	 * @param ulist a list of Users
	 * @return a string in JSON representing the list of Users
	 */
	public static String toJSON(UserPermission[] ulist) {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(ulist, UserPermission.class);
		return json;
	}
	
	/* 
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(java.lang.Object)
	 */
	@Override
	public Boolean identify(Object o) {
		return null;
	}

	/**
	 * Convert a json string into an UserPermission.
	 * 
	 * @param json Json string to parse containing UserPermission
	 * @return The UserPermission given by json
	 */
	public static UserPermission fromJSON(String json) {
		GsonBuilder builder = new GsonBuilder();
		addGsonDependencies(builder);
		return builder.create().fromJson(json, UserPermission.class);
	}
	
	/**
	 * Convert a json string into an array of UserPermission.
	 * 
	 * @param json Json string to parse containing UserPermission array
	 * @return The UserPermission array given by json
	 */
	public static UserPermission[] fromJSONArray(String json) {
		GsonBuilder builder = new GsonBuilder();
		addGsonDependencies(builder);
		return builder.create().fromJson(json, UserPermission[].class);
	}
	
	
	/**
	 * Add dependencies necessary for Gson to interact with this class.
	 * 
	 * @param builder Builder to modify
	 */
	public static void addGsonDependencies(GsonBuilder builder) {
		
	}
}