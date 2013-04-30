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
 * Sam Abradi
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.commons.codec.binary.Base64;




//import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * A class used contain files, along with their names and a reference to the 
 * Requirement that they are attached to. the contents are Base64 encoded.
 * 
 * @author Sam Abradi
 */
public class Attachment extends AbstractModel{
	private String fileContents;
	private String fileName;
	private int id;
	private int ownerId;
	
	/**
	 * Instantiates a new empty attachment.
	 */
	public Attachment(){
		id = -1;
	}
	
	/**
	 * Instantiates a new attachment.
	 *
	 * @param file the file to be attached
	 * @param ownerId the owner id
	 */
	public Attachment(File file,int ownerId){
		this.setFileName(file.getName());
		id = -1;
		this.ownerId = ownerId;
	}
	
	
	/**
	 * loads fileContents from the file
	 *
	 * @param file the file
	 */
	public void loadFile(File file){
		try {
			FileInputStream in = new FileInputStream(file);
			int i=0;
			int buffer;
			byte[] contents = new byte[(int) file.length()];
			
			contents = readFile(file);
			
			in.close();
			this.setFileContents(Base64.encodeBase64String(contents));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Read file.
	 *
	 * @param file the file to read from
	 * @return the byte array of file contents.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public byte[] readFile(File file) throws IOException {
        final InputStream inStream = new FileInputStream(file);
        int offset = 0;
        int numRead = 0;
        int length = (int)file.length();
        byte[] retVal = new byte[length];

        while ((numRead = inStream.read(retVal, offset, length)) > 0) {
                System.out.println("NumRead: " + numRead);
                offset += numRead;
                length -= numRead;
        }
        inStream.close();

        return retVal;
}
	
	/**
	 * Save file.
	 *
	 * @param file the file pointer to save
	 */
	public void saveFile(File file){
				try {
					File OF = file;
					FileOutputStream out = new FileOutputStream(OF);
					byte[] contents = Base64.decodeBase64(this.getFileContents());//new byte[(int) file.length() +1];

					out.write(contents, 0, contents.length);
					out.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	@Override
	public void save() {
		
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
	 */
	@Override
	public void delete() {
		
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
	

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(java.lang.Object)
	 */
	@Override
	public Boolean identify(Object o) {
		return null;
	}

	/**
	 * Sets the id.
	 *
	 * @param i the new id
	 */
	public void setId(int i) {
		id = i;
		
	}
	/**
	 * Gets the contents of this attachment. Encoded into a string using base64.
	 * 
	 * @return the fileContents
	 */
	public String getFileContents() {
		return fileContents;
	}
	
	/**
	 * Sets the contents of this attachment. Encoded into a string using base64.
	 *
	 * @param fileContents the new file contents
	 */
	public void setFileContents(String fileContents) {
		this.fileContents = fileContents;
	}
	/**
	 * Returns the file's name.
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	
	/**
	 * Sets the file's name.
	 *
	 * @param fileName the new file name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * Gets the attachment id.
	 *
	 * @return the attachment's id
	 */
	public int getId(){
		return id;
	}
	/**
	 * Gets the owner's ID
	 * @return the owner's ID
	 */
	public int getOwnerId() {
		return ownerId;
	}
	
	/**
	 * Sets the owner's ID
	 *
	 * @param ownerId the new owner id
	 */
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	
	/**
	 * Takes a ArrayList<Integer> to make sure that there are now adjacent duplicates.
	 * Removes adjacent duplicates if there are any.s
	 *
	 * @param in the array list to check for adjacent duplicates.
	 * @return the modified array list
	 */
	public static ArrayList<Integer> trimList (ArrayList<Integer> in){

		for(int i = 0; i<in.size()-1; i++){
			if(in.get(i) == in.get(i+1)){
				in.remove(i+1);
				i--;
			}
		}
		return in;
	}

}
