package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.snake;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

public class SnakeModel extends AbstractModel{

	int score, id;
	String name;
	
	public SnakeModel(int score, String name){
		this.score = score;
		this.name = name;
		this.id = 1;
	}
	public SnakeModel(){
		this.score = 0;
		this.name = "unknown";
		this.id = 1;
	}
	
	
	/**
	 * Gets the score
	 * @return the score
	 */
	public int getScore() {
		return score;
	}



	/**
	 * Sets the score
	 * @param score sets the score 
	 */
	public void setScore(int score) {
		this.score = score;
	}



	/**
	 * Gets the name
	 * @return the name
	 */
	public String getName() {
		return name;
	}



	/**
	 * Sets the name
	 * @param name sets the name 
	 */
	public void setName(String name) {
		this.name = name;
	}

	

	@Override
	public void save() {
		
	}

	@Override
	public void delete() {
		
	}

	@Override
	public String toJSON() {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(this, SnakeModel.class);

		return json;
	}

	@Override
	public Boolean identify(Object o) {
		return null;
	}

	public void setId(int i) {
		this.id = i;
	}

	public static SnakeModel fromJSON(String json) {
		GsonBuilder builder = new GsonBuilder();
		addGsonDependencies(builder);
		return builder.create().fromJson(json, SnakeModel.class);
	}
	public static void addGsonDependencies(GsonBuilder builder) {
	}
	
	
	public Object getId() {
		return id;
	}

}
