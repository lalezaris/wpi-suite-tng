package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

public class AcceptanceTest extends AbstractModel{

	private String title;
	private String body;
	private String status;
	
	public AcceptanceTest(String title, String body){
		this.title = title;
		this.body = body;
		this.status = "Blank";
	}
	
	public AcceptanceTest(){
		this.title = "";
		this.body = "";
		this.status = "Blank";
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public void setTitle(String t){
		this.title = t;
	}
	
	public String getBody(){
		return  this.body;
	}
	
	public void setBody(String b){
		this.body = b;
	}
	
	public String getStatus(){
		if (this.status == "Blank" || this.status == "Failed" || this.status == "Passed"){
			return this.status;
		}else{
			this.status = "Blank";
			System.out.println("Status was invalid, Corrected to \"Blank\"" );
			return this.status;
		}
	}
	
	public void setStatus(String s){
		if (s.compareTo("Blank") == 0 || s.compareTo("Passed") == 0 || s.compareTo("Failed") == 0){
			this.status = s;
		}else{
			System.out.println("Invalid Status: " + s);
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

	
	//NOTE: toJSON and fromJSON are copy-pasta'd from Requirement
	@Override
	public String toJSON() {

		String json;
		Gson gson = new Gson();
		json = gson.toJson(this, AcceptanceTest.class);

		return json;
	}
	
	public static AcceptanceTest fromJSON(String json) {
		GsonBuilder builder = new GsonBuilder();
		addGsonDependencies(builder);
		return builder.create().fromJson(json, AcceptanceTest.class);
	}
	
	/**
	 * Add dependencies necessary for Gson to interact with this class
	 * @param builder Builder to modify
	 */
	public static void addGsonDependencies(GsonBuilder builder) {
		
	}

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String toString(){
		if (this.status.compareTo("Blank") == 0){
			return " >" + this.getTitle();
		}else{
			return " >" + this.getTitle() + " (" + this.status + ")";
		}
	}

}
