import com.jayway.restassured.response.Response;
import controllers.UserController;
import models.User;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class SampleTestCase {

    private UserController userController;

    @Before
    public void setUp() {
        userController = new UserController();
    }

    @Test
    public void login() throws IOException {

        HttpClient client = new DefaultHttpClient();

        HttpPost loginRequest = new HttpPost("https://staging.ict4apps.aimprosoft.com/welcome/-/login/login?saveLastPath=false");
        List<NameValuePair> credentials = new ArrayList<NameValuePair>();
        credentials.add(new BasicNameValuePair("_58_login", " test@liferay.com"));
        credentials.add(new BasicNameValuePair("_58_password", "test-ict4apps!"));

        loginRequest.setEntity(new UrlEncodedFormEntity(credentials, Consts.UTF_8));
        HttpResponse response = client.execute(loginRequest);
        System.out.println(response.getStatusLine().getStatusCode());
    }

    @Test
    public void checkAimChat(){
        Response login_endpoint = given()
                .parameter("grant_type","password")
                .parameter("type","LDAP")
                .parameter("client_id","aimprosoft_chat")
                .parameter("client_secret","aimprosoft_chat_secret")
                .parameter("username","s.garmaev")
                .parameter("password","Rfhfylfitkm")
                .parameter("Connection","keep-alive")
                .post("https://chat.aimprosoft.com/oauth/token")
                .then().extract().response();
        String authToken = login_endpoint.jsonPath().getJsonObject("access_token");

        login_endpoint = given()
                .parameter("message","99999999999999999988888888888899999999")
                .parameter("Host","chat.aimprosoft.com")
                .parameter("Accept","*/*")
                .parameter("isSystem","false")
                .parameter("systemMessageType","PUBLIC")
                .parameter("Content-Type","application/json")
                .parameter("Accept-Encoding","gzip, deflate, br")
                .parameter("Authorization", "bearer "+authToken)
                .parameter("Connection","keep-alive")
                .post("https://chat.aimprosoft.com/api/rooms/5870/histories")
                .then().extract().response();


        int i=0;
    }

    @Test
    public void postTest(){
        Response login_endpoint = given()
                .parameter("_58_login", "test@liferay.com")
                .parameter("_58_password", "test-ict4apps!")
                .get("https://staging.ict4apps.aimprosoft.com/welcome?p_p_id=58&p_p_lifecycle=1&p_p_state=maximized&p_p_mode=view&_58_struts_action=%2Flogin%2Flogin")
                .then().extract().response();
        //String authToken = login_endpoint.getCookie("JSESSIONID");
        Map<String, String> authToken = login_endpoint.getCookies();

                login_endpoint = given()
                .parameter("Coockie", authToken)
                .parameter("_3_keywords", "pear")
                .parameter("_3_selected_scope","product")
                .get("https://staging.ict4apps.aimprosoft.com/search/-/search/")
                .then().extract().response();
        int i=0;

    }

    @Test
    public void testUserInfoResponse() {
        User remoteUser = userController.getUserByUsername("maxkolotilkin");

        assertTrue(remoteUser.getHtml_url().contains("https://github.com/maxkolotilkin"));
    }

    @Test
    public void testFollowers() {
        User[] followers = userController.getFollowersByUser("Mak0");
        assertTrue(followers.length == 0);
    }


}
