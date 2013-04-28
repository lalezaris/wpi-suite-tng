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

public class AttachmentTest {
	Attachment a = null;
	Attachment b = null;
	byte[] barray = {62,62,62,62,62,62,62,62,62,62,62,62};//{126,52,89,0,111,122,126,52,89,0,111,122,126,52,89,0,111,122,126,52,89,0,111,122,126,52,89,0,111,122,126,52,89,0,111,122};
	FileOutputStream out = null;
	@Before
	public void setup(){
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
		//assertEquals(Base64.decodeBase64(Base64.encodeBase64String(barray)), barray);
		//assertEquals(Base64.decodeBase64(testAttachment.getFileContents()), fileRead);
	}
	
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
