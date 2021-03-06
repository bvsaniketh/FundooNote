import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.bridgeit.model.Collaborator;
import com.bridgeit.model.Login;
import com.bridgeit.model.Note;
import com.bridgeit.model.Register;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class TestLoginController {

	static Login user1,user2,user3,user4,user5;
	static Register reg1, reg2, reg3, reg4, reg5,reg6;
	static Note note1, note2, note3, note4, note5, note6,note7;
	static Collaborator collab;
	Logger logger = Logger.getLogger(TestRegisterController.class);

	@BeforeClass
	public static void setup() {

		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/FundooNote";

		user1 = new Login();
		user1.setEmail("satya@gmail.com");
		user1.setPassword("satya");

		user2 = new Login();
		user2.setEmail("bmsbharathi@gmail.com");
		user2.setPassword("bms");

		user3 = new Login();
		user3.setEmail("robo@gmail.com");
		user3.setPassword("suarez");

		user4 = new Login();
		user4.setEmail("ft@gmail.com");
		user4.setPassword("ft");

		user5 = new Login();
		user5.setEmail("bvsaniketh95@gmail.com");
		user5.setPassword("bridgeit");
		
		note1 = new Note();
		reg1 = new Register();
		reg1.setUser_id(5);
		note1.setTitle("Football");
		note1.setDescription("A beautiful game in peace");
		note1.setLastaccessdate(new Date());
		note1.setUser(reg1);

		note2 = new Note();
		/*
		 * reg2=new Register(); reg2.setUser_id(4);
		 */
		note2.setTitle("Criket");
		note2.setDescription("A bat and ball game in a ground");
		note2.setNotes_id(26);
		// note2.setUser(reg2);

		note3 = new Note();
		reg3 = new Register();
		reg3.setUser_id(4);
		note3.setNotes_id(26);
		note3.setUser(reg3);

		note4 = new Note();
		note4.setNotes_id(8);

		/*
		 * note5=new Note(); reg4=new Register(); reg4.setUser_id(4);
		 * note5.setUser(reg4);
		 */

		note5 = new Note();
		reg4 = new Register();
		reg4.setUser_id(1);
		note5.setUser(reg4);

		note6 = new Note();
		reg4 = new Register();
		reg4.setUser_id(2);
		note6.setUser(reg4);
		
		
		note7=new Note();
		reg6=new Register();
		reg6.setUser_id(1);
		note7.setNotes_id(1);
		collab =new Collaborator();
		collab.setUser(reg6);
		collab.setNote(note7);
		collab.setCollaboratorEmail("ft@gmail.com");
		
		
		
	}

	@Test
	@Ignore
	public void testLogin() {
		// String jsonString =user1.toJSONString;

		System.out.println("testRegister user exists");
		Response resp = given().contentType("application/json").body(user5).when().post("fundoologin");
		logger.info(resp.asString());
		resp.then().statusCode(200);
	}

	@Test
	@Ignore
	public void testLogin1() {
		// String jsonString =user1.toJSONString;
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjM0NSIsImlhdCI6MTUwODkwNDYxNiwic3ViIjoiSldUIFRva2VuIiwiaXNzIjoiQW5pa2V0aCdzIFRva2VucyIsIk5hbWUiOiJMdWlzIFN1YXJleiIsIk1vYmlsZSI6NDcyMzQyLCJJZCI6NCwiZXhwIjoxNTA4OTA4NjE2fQ.uYFTQcTIWOiTM1pQCX4A_3dAh_ofAQjXc6jHsOgZhsQ";
		System.out.println("testRegister user exists");
		Response resp = given().contentType("application/json").header("token", token).body(user4).when()
				.post("auth/insertNote");
		logger.info(resp.asString());
		resp.then().statusCode(200);
	}

	@Test
	@Ignore
	public void testFilter1() {
		// String jsonString =user1.toJSONString;
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjM0NSIsImlhdCI6MTUwODkwNDYxNiwic3ViIjoiSldUIFRva2VuIiwiaXNzIjoiQW5pa2V0aCdzIFRva2VucyIsIk5hbWUiOiJMdWlzIFN1YXJleiIsIk1vYmlsZSI6NDcyMzQyLCJJZCI6NCwiZXhwIjoxNTA4OTA4NjE2fQ.uYFTQcTIWOiTM1pQCX4A_3dAh_ofAQjXc6jHsOgZhsQ";
		System.out.println("Testing Notes Functionalities");
		/* Response resp = */given().contentType("application/json").header("token", token).body(note3).when()
				.post("auth/insertNote").then().statusCode(200);
		/* logger.info(resp.asString()); */

	}

	@Test
	@Ignore
	public void testFilter2() {
		// String jsonString =user1.toJSONString;
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjM0NSIsImlhdCI6MTUwOTE3MTkzOSwic3ViIjoiSldUIFRva2VuIiwiaXNzIjoiQW5pa2V0aCdzIFRva2VucyIsIk5hbWUiOiJBbmlrZXRoIEJvbmRhZGEiLCJNb2JpbGUiOjEyMzQ1LCJJZCI6MSwiZXhwIjoxNTA5MTc1OTM5fQ.6YnPuGXoJSMde8tA1JwI2dLvjV3uSYRpM7R0e594wig";
		System.out.println("Testing Notes Functionalities");
		/* Response resp = */given().contentType("application/json").header("token", token).body(note2).when()
				.post("auth/updateNote").then().statusCode(200);
		/* logger.info(resp.asString()); */

	}

	@Test
	@Ignore
	public void testFilter3() {
		// String jsonString =user1.toJSONString;
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjM0NSIsInN1YiI6IkpXVCBUb2tlbiIsImlzcyI6IkFuaWtldGgncyBUb2tlbnMiLCJOYW1lIjoiQmhhcmF0aGkiLCJNb2JpbGUiOjczNzMsIklkIjoyfQ.99TE3zxvrU4fistZ5_JWcVZO71dqxPW6v4RgJbAD2q0";
		System.out.println("Testing Notes Functionalities");
		/* Response resp = */given().contentType("application/json").header("token", token).body(note3).when()
				.post("auth/deleteNote").then().statusCode(200);
		/* logger.info(resp.asString()); */

	}

	@Test
	@Ignore
	public void testFilter4() {
		// String jsonString =user1.toJSONString;
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjM0NSIsInN1YiI6IkpXVCBUb2tlbiIsImlzcyI6IkFuaWtldGgncyBUb2tlbnMiLCJOYW1lIjoiQmhhcmF0aGkiLCJNb2JpbGUiOjczNzMsIklkIjoyfQ.99TE3zxvrU4fistZ5_JWcVZO71dqxPW6v4RgJbAD2q0";
		System.out.println("Testing Notes Functionalities");
		/* Response resp = */given().contentType("application/json").header("token", token).body(note4).when()
				.post("auth/getNotebyId").then().statusCode(200);
		/* logger.info(resp.asString()); */

	}

	@Test
	@Ignore
	public void testFilter5() {
		// String jsonString =user1.toJSONString;
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjM0NSIsImlhdCI6MTUwOTE3MTkzOSwic3ViIjoiSldUIFRva2VuIiwiaXNzIjoiQW5pa2V0aCdzIFRva2VucyIsIk5hbWUiOiJBbmlrZXRoIEJvbmRhZGEiLCJNb2JpbGUiOjEyMzQ1LCJJZCI6MSwiZXhwIjoxNTA5MTc1OTM5fQ.6YnPuGXoJSMde8tA1JwI2dLvjV3uSYRpM7R0e594wig";
		System.out.println("Testing Notes Functionalities");
		/* Response resp = */given().contentType("application/json").header("token", token).body(note5).when()
				.post("auth/selectAllNotes").then().statusCode(200);
		/* logger.info(resp.asString()); */

	}

	@Test
	@Ignore
	public void testFilter6() {
		// String jsonString =user1.toJSONString;
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjM0NSIsImlhdCI6MTUwOTUxNTU4Nywic3ViIjoiSldUIFRva2VuIiwiaXNzIjoiQW5pa2V0aCdzIFRva2VucyIsIk5hbWUiOiJBbmlrZXRoIEJvbmRhZGEiLCJNb2JpbGUiOjEyMzQ1LCJJZCI6MSwiZXhwIjoxNTA5NTU1NTg3fQ.Hq4kIBMnyVxKOJ4MIIomd-Mz24Za6bB127KFrvSWU_k";
		System.out.println("Testing Elastic Notes Functionalities");
		/* Response resp = */given().contentType("application/json").header("token", token).body(note6).when()
				.post("auth/selectAllFundooNotes").then().statusCode(200);
		/* logger.info(resp.asString()); */

	}

	@Test
	@Ignore
	public void testFilter7() {

		String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjM0NSIsImlhdCI6MTUwOTM2MDMxNiwic3ViIjoiSldUIFRva2VuIiwiaXNzIjoiQW5pa2V0aCdzIFRva2VucyIsIk5hbWUiOiJGZXJuYW5kbyBUb3JyZXMiLCJNb2JpbGUiOjQ3MjM0MiwiSWQiOjUsImV4cCI6MTUwOTQwMDMxNn0.ZfJOiuK0tUml-RRTtvAwZpqTILzuPU_2c_x9Mo2iHsI";
		String content = "cricket";
		System.out.println("Testing Elastic Notes Search Functionalities");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", 5);
		map.put("searchString", content);

		given().contentType(ContentType.JSON).header("token", token).body(map).when().post("auth/searchAllNotesElastic")
				.then().statusCode(200);

	}

	@Test
    @Ignore
	public void testFilter8() {

		String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjM0NSIsImlhdCI6MTUwOTQzMzU0Miwic3ViIjoiSldUIFRva2VuIiwiaXNzIjoiQW5pa2V0aCdzIFRva2VucyIsIk5hbWUiOiJCaGFyYXRoaSIsIk1vYmlsZSI6NzM3MywiSWQiOjIsImV4cCI6MTUwOTQ3MzU0Mn0.EbwVSKQllhhaxtBEx2CcRLWfVpQpTlc0Dl9zFlcsAW0";  
		System.out.println("Indexing all Notes");

		given().contentType(ContentType.JSON).header("token", token).body(note6).when().post("auth/indexAllNotes")
				.then().statusCode(200);

	}
	
	@Test
	@Ignore
	public void testFilter9() {
		
		note6.setNotes_id(2);
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjM0NSIsImlhdCI6MTUwOTM0NTA2OCwic3ViIjoiSldUIFRva2VuIiwiaXNzIjoiQW5pa2V0aCdzIFRva2VucyIsIk5hbWUiOiJGZXJuYW5kbyBUb3JyZXMiLCJNb2JpbGUiOjQ3MjM0MiwiSWQiOjUsImV4cCI6MTUwOTM4NTA2OH0.N9hobq6EZUY0Vzx6flQr_5RNu6U_zqyIlflcmAIrrPI";
		System.out.println("Deleting Note");

		given().contentType(ContentType.JSON).header("token", token).body(note6).when().post("auth/deleteNote")
				.then().statusCode(200);

	}
	
	
	@Test
	/*@Ignore*/
	public void testFilter10()
	{
		
		String token="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjM0NSIsImlhdCI6MTUwOTc3MzQ1MSwic3ViIjoiSldUIFRva2VuIiwiaXNzIjoiQW5pa2V0aCdzIFRva2VucyIsIk5hbWUiOiJBbmlrZXRoIEJvbmRhZGEiLCJNb2JpbGUiOjEyMzQ1LCJJZCI6MSwiZXhwIjoxNTA5ODEzNDUxfQ.7dr9uGECLcVAlquFaz-J2Hn3Ia4SFoqLhPVusbFTc_M";
		Map<String,String> collabMap=new HashMap<String,String>();
		collabMap.put("email","bmsbharathi@gmail.com");
		
		System.out.println("Testing Collaboration");
		given().contentType(ContentType.JSON).header("token",token)/*.body(collabMap)*/.body(collab).when().post("auth/collaborate")
				.then().statusCode(200);
	}

}
