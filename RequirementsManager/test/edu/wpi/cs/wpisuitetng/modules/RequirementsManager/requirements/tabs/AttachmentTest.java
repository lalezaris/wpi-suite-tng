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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.junit.*;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Attachment;

/**
 * The Class AttachmentTest tests attachements.
 * 
 * @author Sam Abradi
 */
public class AttachmentTest {
	Attachment a = null;
	Attachment b = null;
	byte[] barray = new byte[4096];
	FileOutputStream out = null;
	
	/**
	 * Setup the tests.
	 */
	@Before
	public void setup(){
		for(int i = 0; i<4096; i++){
			barray[i]=(byte) i;
		}	
		a=new Attachment();
		b=new Attachment(new File(""),3);
		try {
			out = new FileOutputStream("testFile");
			//now to fill the file
			out.write(barray);
		} catch (FileNotFoundException e) {
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}

	}


	/**
	 * Test encoding.
	 */
	@Test
	public void testEncoding() {
		FileInputStream in = null;
		try {
			in = new FileInputStream("testFile");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		byte[] fileRead = new byte[(int) barray.length];
		try {
			in.read(fileRead, 0, fileRead.length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Attachment testAttachment = new Attachment();
		testAttachment.loadFile(new File("testFile"));
		assertEquals("0123456789qwertyuiopasdfghjklzxcvbnm",Base64.encodeBase64String(Base64.decodeBase64("0123456789qwertyuiopasdfghjklzxcvbnm")));
		assertEquals(testAttachment.getFileContents(),Base64.encodeBase64String(barray));
		System.out.println(Base64.encodeBase64String(barray));
		//assertEquals(Base64.decodeBase64("Pj4+Pj4+Pj4+Pj4+"),barray);
		assertArrayEquals(Base64.decodeBase64(Base64.encodeBase64String(barray)), barray);
		assertArrayEquals(Base64.decodeBase64(testAttachment.getFileContents()), fileRead);
	}
	
	/**
	 * Test loading and saving a file.
	 */
	@Test
	public void testLoadSaveFile() {
		a.loadFile(new File("testFile"));
		a.saveFile(new File("testOutFile"));
		try {
			FileInputStream a = new FileInputStream(new File("testFile"));
			FileInputStream b = new FileInputStream(new File("testFile"));
			int bbuf, abuf;
			while((abuf=a.read())!=-1&&(bbuf=b.read())!=-1) assertEquals(abuf,bbuf);
		} catch (FileNotFoundException e) {} catch (IOException e) {}
		a.save();
		a.delete();

	}
	
	/**
	 * Test json functionality.
	 */
	@Test
	public void testJSON(){
		a.setId(5);
		a.setOwnerId(898897);
		assertNull(a.identify(null));
		String aJSON = a.toJSON();
		String bJSON = b.toJSON();
		assertTrue(!a.equals(b));
		
		System.out.println(aJSON);
		
		assertEquals(a.getFileContents(),a.fromJSON(aJSON).getFileContents());
		assertEquals(a.getFileName(),a.fromJSON(aJSON).getFileName());
		assertEquals(a.getId(),a.fromJSON(aJSON).getId());
		assertEquals(a.getOwnerId(),a.fromJSON(aJSON).getOwnerId());
		
		Attachment[] array = Attachment.fromJSONArray("["+a.toJSON()+"]");
		assertEquals(a.getFileContents(),array[0].getFileContents());
		assertEquals(a.getFileName(),array[0].getFileName());
		assertEquals(a.getId(),array[0].getId());
		assertEquals(a.getOwnerId(),array[0].getOwnerId());
	}
	

}
