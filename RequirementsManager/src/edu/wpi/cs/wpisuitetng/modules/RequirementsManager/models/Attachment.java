package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import java.io.File;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

public class Attachment extends AbstractModel{
	String fileContents;
	String fileName;
	int id;
	
	public Attachment(File file){
		this.fileName = file.getName();
		id = -1;
	}
	
	//loads fileContents from the file
	public void loadFile(File file){
		//TODO actually load the contents into fileContents when this is called
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Converts this Attachment to a JSON string.
	 *
	 * @return a string in JSON representing this attachment
	 */
	@Override
	public String toJSON() {
		
		String json;
		Gson gson = new Gson();
		json = gson.toJson(this, Attachment.class);

		return json;
	}	

	/**
	 * Convert a json string into a Attachment.
	 *
	 * @param json Json string to parse containing Attachment
	 * @return The Attachment given by json
	 */
	public static Attachment fromJSON(String json) {
		GsonBuilder builder = new GsonBuilder();
		addGsonDependencies(builder);
		return builder.create().fromJson(json, Attachment.class);
	}
	
	/**
	 * Convert a json string to an array of Attachment.
	 *
	 * @param json Json string to parse containing Attachment array
	 * @return The Attachment array given by json
	 */
	public static Attachment[] fromJSONArray(String json) {
		GsonBuilder builder = new GsonBuilder();
		addGsonDependencies(builder);
		return builder.create().fromJson(json, Attachment[].class);
	}
	
	
	/**
	 * Add dependencies necessary for Gson to interact with this class.
	 *
	 * @param builder Builder to modify
	 */
	public static void addGsonDependencies(GsonBuilder builder) {
		
	}
	

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setId(int i) {
		this.id = i;
		
	}

}
