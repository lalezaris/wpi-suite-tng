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

/**
 * Insert Description Here
 *
 * @author "Michael Perrone"
 *
 * @version Mar 31, 2013
 *
 */
public class ProjectUser extends AbstractModel {

	private String name;
	private String userName;
	private int id;
	private PermissionsLevel permissions;
	
	
	/**
	 * @param name
	 * @param userName
	 * @param id
	 * @param permissions
	 */
	public ProjectUser(String name, String userName, int id, PermissionsLevel permissions) {
		super();
		this.name = name;
		this.userName = userName;
		this.id = id;
		this.permissions = permissions;
	}	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the permissions
	 */
	public PermissionsLevel getPermissions() {
		return permissions;
	}

	/**
	 * @param permissions: the permissions to set
	 */
	public void setPermissions(PermissionsLevel permissions) {
		this.permissions = permissions;
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
		json = gson.toJson(this, ProjectUser.class);

		return json;
	}	
	
	/**
	 * Converts the given list of Users to a JSON string
	 * @param ulist a list of Users
	 * @return a string in JSON representing the list of Users
	 */
	public static String toJSON(ProjectUser[] ulist) {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(ulist, ProjectUser.class);
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
	public static ProjectUser fromJSON(String json) {
		GsonBuilder builder = new GsonBuilder();
		addGsonDependencies(builder);
		return builder.create().fromJson(json, ProjectUser.class);
	}
	
	/**
	 * @param json Json string to parse containing User array
	 * @return The User array given by json
	 */
	public static ProjectUser[] fromJSONArray(String json) {
		GsonBuilder builder = new GsonBuilder();
		addGsonDependencies(builder);
		return builder.create().fromJson(json, ProjectUser[].class);
	}
	
	
	/**
	 * Add dependencies necessary for Gson to interact with this class
	 * @param builder Builder to modify
	 */
	public static void addGsonDependencies(GsonBuilder builder) {
		
	}

}