import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.bridgeit.json.Response;
import com.bridgeit.model.Register;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class TestRegisterController {

	static Register user1;
	static Register user2;
	static Register user3;
	static Register user4;
	static Register user5;
	Logger logger = Logger.getLogger(TestRegisterController.class);

	@BeforeClass
	public static void setup() {
		
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/FundooNote";

		user1 = new Register();
		user1.setName("Aniketh");
		user1.setEmail("bvsaniketh95@gmail.com");
		//user1.setAge(25);
		user1.setMobile(472342);
		//user1.setUsername("subbu");
		user1.setPassword("subbu");

		user2 = new Register();
		user2.setName("Luis Suarez");
		user2.setEmail("robo@gmail.com");
		//user2.setAge(25);
		user2.setMobile(472342);
		//user2.setUsername("luis");
		user2.setPassword("suarez");
		
		user3 = new Register();
		user3.setName("Fernando Torres");
		user3.setEmail("ft@gmail.com");
		//user2.setAge(25);
		user3.setMobile(472342);
		//user2.setUsername("luis");
		user3.setPassword("ft");

		user4 = new Register();
		user4.setName("Sadio Mane");
		user4.setEmail("sm@gmail.com");
		//user2.setAge(25);
		user4.setMobile(12123);
		//user2.setUsername("luis");
		user4.setPassword("sm");
		
		user5 = new Register();
		user5.setName("Anibo1");
		user5.setEmail("bvsaniketh@yahoo.co.in");
		//user1.setAge(25);
		user5.setMobile(472342);
		//user1.setUsername("subbu");
		user5.setPassword("subbu");
	}

	@Test
	@Ignore
	public void testRegister() {
		// String jsonString =user1.toJSONString;
		System.out.println("testRegister user exists");
		Response resp=(Response) given().contentType("application/json").body(user5).when().post("fundooregister").then().statusCode(200);
		System.out.println(resp);
	}
	
	@Test
	@Ignore
	public void insertUserWithoutAnyError() {
		logger.info("insertUserWithoutAnyError()");
		given().contentType("application/json").body(user4).when().post("fundooregister").then().statusCode(200);
		// assertThat(resp.getStatus(),equalTo(1));
	}
	
	@Test
	@Ignore 
	public void testInsertRegister() {
	logger.info("insert user");
	 RestAssured.given().body(user2).contentType("application/json").when().post("fundooregister").then().statusCode(200); }
	 

}
