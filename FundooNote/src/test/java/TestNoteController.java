import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.bridgeit.Model.Note;
import com.bridgeit.Model.Register;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TestNoteController {

	static Note note1,note2,note3,note4,note5,note6,noteunarchive,note7,note8,note9;
	static Register user1,user2,user3,user4,user5,user6,userunarchive,user7,user8,user9;
	private static final Logger logger=Logger.getLogger(TestNoteController.class);
	
	
	
	@BeforeClass
	public static void setup()
	{
		RestAssured.baseURI="http://localhost";
		RestAssured.port=8080;
		RestAssured.basePath="/FundooNote"; 
		
		note1=new Note();
		user1=new Register();
		user1.setUser_id(8);
		note1.setTitle("Soccer");
		note1.setDescription("A beautiful game in peace");
		note1.setLastaccessdate(new Date());
		note1.setUser(user1);
		
		note2=new Note();
		user2=new Register();
		/*user2.setUser_id(4);*/
		note2.setTitle("Criket");
		note2.setDescription("A bat and ball game in a ground");
		note2.setNotes_id(7);
		note2.setUser(user2);
		
		note3=new Note();
		user3=new Register();
		user3.setUser_id(1);
		note3.setNotes_id(17);
		note3.setUser(user3);
		
		note4=new Note();
		note4.setNotes_id(8);
		
		note5=new Note();
		user5=new Register();
		user5.setUser_id(4);
		note5.setUser(user5);
		
		note6=new Note();
		user6=new Register();
		user6.setUser_id(5);
		note6.setNotes_id(36);
		note6.setArchive(true);
		note6.setUser(user6);
		
		noteunarchive=new Note();
		userunarchive=new Register();
		userunarchive.setUser_id(5);
		noteunarchive.setNotes_id(36);
		noteunarchive.setArchive(false);
		noteunarchive.setUser(userunarchive);
		
		/*Sending note to trash*/
		note7=new Note();
		user7=new Register();
		user7.setUser_id(4);
		note7.setNotes_id(48);
		note7.setTrash(true);
		note7.setRemainder(new Date());
		note7.setDeletefromtrash(false);
		note7.setUser(user7);
		
		/*Permanent deletion of the note*/
		note8=new Note();
		user8=new Register();
		user8.setUser_id(5);
		note8.setNotes_id(34);
		note8.setTrash(true);
		note8.setDeletefromtrash(true);
		note8.setUser(user7);
		
		/*Restoring from trash
		note8=new Note();
		user8=new Register();
		user8.setUser_id(5);
		note8.setNotes_id(34);
		note8.setTrash(false);
		note8.setDeletefromtrash(false);
		note8.setUser(user7);
		*/
		
		note9=new Note();
		user9=new Register();
		user9.setUser_id(9);
		note9.setNotes_id(40);
		note9.setRemainder(new Date());
		note9.setUser(user9);
		
		
		
		
		
	}
	
	@Test
	@Ignore
	public void testInsertNote()
	{
		given().contentType(ContentType.JSON).body(note3).when().post("insertNote").then().statusCode(200);
	}
	
	@Test
	@Ignore
	public void testUpdateNote()
	{
		given().contentType(ContentType.JSON).body(note2).when().post("updateNote").then().statusCode(200);
	}
	@Test
	@Ignore
	public void testDeleteNote()
	{
		given().contentType(ContentType.JSON).body(note3).when().post("deleteNote").then().statusCode(200);
	}
	@Test
	@Ignore
	public void testgetNotebyId()
	{
		given().contentType(ContentType.JSON).body(note4).when().post("getNotebyId").then().statusCode(200);
	}
	@Test
	@Ignore
	public void testgetallNotes()
	{
		given().contentType(ContentType.JSON).body(note5).when().post("selectAllNotes").then().statusCode(200);
	}
	@Test
	@Ignore
	public void archiveNote()
	{
		given().contentType(ContentType.JSON).body(note6).when().post("archiveNote").then().statusCode(200);
	}
	
	@Test
	@Ignore
	public void unarchiveNote()
	{
		given().contentType(ContentType.JSON).body(noteunarchive).when().post("archiveNote").then().statusCode(200);
	}
	@Test
	@Ignore
	public void trashNote()
	{
		given().contentType(ContentType.JSON).body(note7).when().post("trashNote").then().statusCode(200);
	}
	
	@Test
	@Ignore
	public void deletefromtrash()
	{
		given().contentType(ContentType.JSON).body(note7).when().post("deleteFromTrash").then().statusCode(200); 
	}
	
	@Test
	@Ignore
	public void setRemainder()
	{	logger.info("Setting Remainder");
		Response resp=given().contentType(ContentType.JSON).body(note7).when().post("setRemainder");
		logger.info(resp.asString());
		logger.info(resp.then().statusCode(200));
	}


}
