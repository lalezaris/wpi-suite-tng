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

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.model.RequirementEvent.EventType;
/**
 * Responsible for deserializing a RequirementEvent
 *
 * @author Tushar Narayan
 *
 * @version Mar 18, 2013
 *
 */
public class RequirementEventDeserializer implements JsonDeserializer<RequirementEvent> {

	@Override
	public RequirementEvent deserialize(JsonElement element, Type type,
			JsonDeserializationContext context) throws JsonParseException {
		// we need to switch on the type field to figure out the concrete class to instantiate
		JsonObject object = element.getAsJsonObject();
		if(!object.has("type")) {
			throw new JsonParseException("RequirementEvent does not have type information");
		}
		EventType eType = context.deserialize(object.get("type"), EventType.class);
		if(eType != null) { // type could be any garbage string, eType null if not in enum
			switch(eType) {
			case CHANGESET:
				return context.deserialize(element, RequirementChangeset.class);
			case NOTE:
				return context.deserialize(element, NoteEvent.class);
			}
		}
		throw new JsonParseException("RequirementEvent type is unrecognized");
	}
}