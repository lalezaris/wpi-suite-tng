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
 *  "Michael Perrone"
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * The model to hold a username and the permission associated with that username
 *
 * @author "Michael Perrone"
 *
 * @version Mar 31, 2013
 *
 */
public class UserPermission extends AbstractModel {

	private String username;
	private RMPermissionsLevel permissions;
	private int id;
	
	/**
	 * @param userName
	 * @param permissions
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
	 * @param username
	 * @param permissions
	 */
	public UserPermission() {
		super();
		this.username = "";
		this.permissions = RMPermissionsLevel.NONE;
		this.id = -1;
	}
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the permissions
	 */
	public RMPermissionsLevel getPermissions() {
		return permissions;
	}

	/**
	 * @param permissions: the permissions to set
	 */
	public void setPermissions(RMPermissionsLevel permissions) {
		this.permissions = permissions;
	}
	
	
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return this.id;
	}
	
	
	

	/*
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	/* 
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
	 */
	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
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
	 * Converts the given list of Users to a JSON string
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
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param json Json string to parse containing User
	 * @return The User given by json
	 */
	public static UserPermission fromJSON(String json) {
		GsonBuilder builder = new GsonBuilder();
		addGsonDependencies(builder);
		return builder.create().fromJson(json, UserPermission.class);
	}
	
	/**
	 * @param json Json string to parse containing User array
	 * @return The User array given by json
	 */
	public static UserPermission[] fromJSONArray(String json) {
		GsonBuilder builder = new GsonBuilder();
		addGsonDependencies(builder);
		return builder.create().fromJson(json, UserPermission[].class);
	}
	
	
	/**
	 * Add dependencies necessary for Gson to interact with this class
	 * @param builder Builder to modify
	 */
	public static void addGsonDependencies(GsonBuilder builder) {
		
	}

}