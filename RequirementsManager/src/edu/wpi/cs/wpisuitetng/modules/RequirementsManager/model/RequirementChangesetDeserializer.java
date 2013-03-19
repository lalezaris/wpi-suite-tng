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
 *  Tushar Narayan
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.model;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * Custom JSON deserializer for the RequirementChangeset class
 *
 * @author Tushar Narayan
 *
 * @version Mar 18, 2013
 *
 */
// TODO: May not work properly and/or be incomplete as far as functionality goes
public class RequirementChangesetDeserializer implements JsonDeserializer<RequirementChangeset> {

	@Override
	public RequirementChangeset deserialize(JsonElement json, Type type,
			JsonDeserializationContext context) throws JsonParseException {
		
		// hash map to hold the deserialized FieldChange objects
		HashMap<String, FieldChange<?>> changesMap = new HashMap<String, FieldChange<?>>();
		
		JsonObject changeSet = json.getAsJsonObject();
		if (changeSet.has("changes")) {
			JsonObject changes = changeSet.get("changes").getAsJsonObject();
			if (changes.has("title")) {
				JsonObject titleObj = changes.get("title").getAsJsonObject();
				String oldTitle = context.deserialize(titleObj.get("oldValue"), String.class);
				String newTitle = context.deserialize(titleObj.get("newValue"), String.class);
				changesMap.put("title", new FieldChange<String>(oldTitle, newTitle));
			}
			if (changes.has("description")) {
				JsonObject descriptionObj = changes.get("description").getAsJsonObject();
				String oldDesc = context.deserialize(descriptionObj.get("oldValue"), String.class);
				String newDesc = context.deserialize(descriptionObj.get("newValue"), String.class);
				changesMap.put("description", new FieldChange<String>(oldDesc, newDesc));
			}
			if (changes.has("assignee")) {
				JsonObject assigneeObj = changes.get("assignee").getAsJsonObject();
				User oldUser = context.deserialize(assigneeObj.get("oldValue"), User.class);
				User newUser = context.deserialize(assigneeObj.get("newValue"), User.class);
				changesMap.put("assignee", new FieldChange<User>(oldUser, newUser));
			}
			/*Requirements don't have tags assigned to them
			 * if (changes.has("tags")) {
				JsonObject tagsObj = changes.get("tags").getAsJsonObject();
				Tag[] oldTags = context.deserialize(tagsObj.get("oldValue"), Tag[].class);
				Tag[] newTags = context.deserialize(tagsObj.get("newValue"), Tag[].class);
				changesMap.put("tags", new FieldChange<Set<Tag>>(new HashSet<Tag>(new ArrayList<Tag>(Arrays.asList(oldTags))), new HashSet<Tag>(new ArrayList<Tag>(Arrays.asList(newTags)))));
			}
			*/
			if (changes.has("status")) {
				JsonObject statusObj = changes.get("status").getAsJsonObject();
				RequirementStatus oldStatus = context.deserialize(statusObj.get("oldValue"), RequirementStatus.class);
				RequirementStatus newStatus = context.deserialize(statusObj.get("newValue"), RequirementStatus.class);
				changesMap.put("status", new FieldChange<RequirementStatus>(oldStatus, newStatus));
			}
			
			// reconstruct the RequirementChangeset
			RequirementChangeset retVal = new RequirementChangeset();
			retVal.setChanges(changesMap);
			retVal.setDate((Date)(context.deserialize(changeSet.get("date"), Date.class)));
			retVal.setUser((User)(context.deserialize(changeSet.get("user"), User.class)));
			
			// return the RequirementChangeset
			return retVal;
		}
		else {
			throw new JsonParseException("RequirementChangeset type is unrecognized");
		}
	}
}
