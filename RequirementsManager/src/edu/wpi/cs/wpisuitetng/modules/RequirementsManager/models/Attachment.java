package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;




//import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

public class Attachment extends AbstractModel{
	private String fileContents;
	private String fileName;
	private int id;
	private int ownerId;
	
	public Attachment(){
		id = -1;
	}
	public Attachment(File file,int ownerId){
		this.setFileName(file.getName());
		id = -1;
		this.ownerId = ownerId;
	}
	
	//loads fileContents from the file
	public void loadFile(File file){
		//TODO actually load the contents into fileContents when this is called
		try {
			FileInputStream in = new FileInputStream(file);
			int i=0;
			byte[] contents = new byte[(int) file.length() +1];
			
			while((contents[i++] = (byte) in.read())!=-1);
			in.close();//new String(Base64.encodeBase64
			this.setFileContents(Base64.encodeBase64String(contents));
			//this.fileContents = new String(Base64.encodeBase64(contents));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	/**
	 * Enter description here.
	 * Make sure the method's name starts with get (delete this statement)
	 * @return the fileContents
	 */
	public String getFileContents() {
		return fileContents;
	}
	/**
	 * Enter description here.
	 * Make sure the method's name starts with get (delete this statement)
	 * @param fileContents: the fileContents to set
	 */
	public void setFileContents(String fileContents) {
		this.fileContents = fileContents;
	}
	/**
	 * Enter description here.
	 * Make sure the method's name starts with get (delete this statement)
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * Enter description here.
	 * Make sure the method's name starts with get (delete this statement)
	 * @param fileName: the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public int getId(){
		return this.id;
	}
	/**
	 * Enter description here.
	 * Make sure the method's name starts with get (delete this statement)
	 * @return the ownerId
	 */
	public int getOwnerId() {
		return ownerId;
	}
	/**
	 * Enter description here.
	 * Make sure the method's name starts with get (delete this statement)
	 * @param ownerId: the ownerId to set
	 */
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

}