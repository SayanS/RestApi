

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.response.Response;
import controllers.UserController;
import models.User;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.junit.Before;
import org.junit.Test;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
    public void checkAimChat() {
        Response login_endpoint = (Response) given()
                .parameter("grant_type", "password")
                .parameter("type", "LDAP")
                .parameter("client_id", "aimprosoft_chat")
                .parameter("client_secret", "aimprosoft_chat_secret")
                .parameter("username", "s.garmaev")
                .parameter("password", "Rfhfylfitkm")
                .parameter("Connection", "keep-alive")
                .parameter("Content-Type", "application/x-www-form-urlencoded")
                .parameter("Referer", "https://chat.aimprosoft.com/index.html")
                .parameter("Accept-Encoding", "gzip, deflate, br")
                .post("https://chat.aimprosoft.com/oauth/token")
                .thenReturn().getBody();
        String authToken = login_endpoint.jsonPath().getJsonObject("access_token");

        String myJson = "{\"message\":\"It's working\",\"isSystem\":false,\"systemMessageType\":\"PUBLIC\"}";
        for (int i = 0; i <= 100; i++) {
            myJson.replace("It's working", "It's working via RestAssure: " + i);
            login_endpoint = (Response) given()
                    .header("origin", "https://chat.aimprosoft.com")
                    .header("Accept", "*/*")
                    .header("Content-Type", "application/json")
                    .header("Referer", "https://chat.aimprosoft.com/index.html")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Authorization", "bearer " + authToken)
                    .body(myJson)
                    .when()
                    .post("https://chat.aimprosoft.com/api/rooms/7516/histories");
        }
    }

    @Test
    public void httpClientCase() throws IOException {
        HttpClient client = new DefaultHttpClient();
        ResponseHandler<String> handler = new BasicResponseHandler();


        HttpPost loginRequest = new HttpPost("https://chat.aimprosoft.com/oauth/token?grant_type=password&type=LDAP");
        List<NameValuePair> credentials = new ArrayList<NameValuePair>();
        credentials.add(new BasicNameValuePair("username", "s.garmaev"));
        credentials.add(new BasicNameValuePair("password", "Rfhfylfitkm"));
        credentials.add(new BasicNameValuePair("client_id", "aimprosoft_chat"));
        credentials.add(new BasicNameValuePair("client_secret", "aimprosoft_chat_secret"));
        loginRequest.setEntity(new UrlEncodedFormEntity(credentials, Consts.UTF_8));
        loginRequest.addHeader("Origin","https://chat.aimprosoft.com");
        loginRequest.addHeader("Content-Type","application/x-www-form-urlencoded");
        loginRequest.addHeader("Referer","https://chat.aimprosoft.com/index.html");
        loginRequest.addHeader("Accept-Encoding","gzip, deflate, br");
        loginRequest.addHeader("Accept","*/*");

        HttpResponse response = client.execute(loginRequest);
        String body = handler.handleResponse(response);

 //       JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();
//        jsonObject.get("access_token")

        ObjectMapper mapper = new ObjectMapper();
        JsonNode accessToken = mapper.readTree(body).get("access_token");


        HttpPost loginRequest1 = new HttpPost("https://chat.aimprosoft.com/api/rooms/7516/histories/");
        List<NameValuePair> message = new ArrayList<NameValuePair>();
        message.add(new BasicNameValuePair("message", "My HTTPmessage"));
        message.add(new BasicNameValuePair("isSystem", "false"));
        message.add(new BasicNameValuePair("systemMessageType", "PUBLIC"));
        loginRequest1.setEntity(new UrlEncodedFormEntity(message, Consts.UTF_8));
        loginRequest1.addHeader("Origin", "https://chat.aimprosoft.com");
        loginRequest1.addHeader("Authorization", "bearer "+accessToken.asText());
        loginRequest1.addHeader("Content-Type", "application/json");
        loginRequest1.addHeader("Referer","https://chat.aimprosoft.com/index.html");
        loginRequest1.addHeader("Accept-Encoding","gzip, deflate, br");
        loginRequest1.addHeader("Accept","*/*");


        response=client.execute(loginRequest1);
        loginRequest1.releaseConnection();
        int i=0;

    }

    @Test
    public void postTest() {
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
                .parameter("_3_selected_scope", "product")
                .get("https://staging.ict4apps.aimprosoft.com/search/-/search/")
                .then().extract().response();
        int i = 0;

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
