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
 *  Lauren Kahn
**************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class RequirementEventTest {

	Gson gson;
	RequirementEvent changeset;
	RequirementEvent noteEvent;
	GsonBuilder builder;
	
	@Before
	public void setUp() {
		gson = new Gson();
		changeset = new RequirementChangeset();
		noteEvent = new NoteEvent();
		builder = new GsonBuilder();
	}
	
	@Test
	public void testDeseprialization() {
		Date date = new Date(1000);
		changeset.setDate(date);
		noteEvent.setDate(date);
		
		String changesetJson = gson.toJson(changeset);
		String commentJson = gson.toJson(noteEvent);
		
		gson = builder.create();
		
		RequirementEvent newEvent = gson.fromJson(changesetJson, RequirementEvent.class);
		assertTrue(newEvent instanceof RequirementChangeset);
		assertEquals(date, newEvent.getDate());
		
		newEvent = gson.fromJson(commentJson, RequirementEvent.class);
		assertTrue(newEvent instanceof NoteEvent);
		assertEquals(date, newEvent.getDate());
	}
	
	@Test(expected=JsonParseException.class)
	public void testNoType() {
		changeset.type = null; // null fields don't show up in json at all
		String json = gson.toJson(changeset);
		gson = builder.create();
		changeset = gson.fromJson(json, RequirementEvent.class);
	}
	
	@Test(expected=JsonParseException.class)
	public void testBadType() {
		JsonElement element = gson.toJsonTree(changeset);
		element.getAsJsonObject().addProperty("type", "what");
		String json = gson.toJson(element);
		gson = builder.create();
		changeset = gson.fromJson(json, RequirementEvent.class);
	}
	
	@Test(expected=JsonParseException.class)
	public void testNullType() {
		gson = (new GsonBuilder()).serializeNulls().create();
		changeset.type = null;
		String json = gson.toJson(changeset, RequirementEvent.class);
		gson = builder.create();
		changeset = gson.fromJson(json, RequirementEvent.class);
	}

}
