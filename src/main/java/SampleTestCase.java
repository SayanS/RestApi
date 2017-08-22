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
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

public class SampleTestCase {

    private UserController userController;

    @Before
    public void setUp() {
        userController = new UserController();
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
    public void postTest() {
        Response login_endpoint = given()
                .parameter("_58_login", "test@liferay.com")
                .parameter("_58_password", "test-ict4apps!")
                .get("https://staging.ict4apps.aimprosoft.com/welcome?p_p_id=58&p_p_lifecycle=1&p_p_state=maximized&p_p_mode=view&_58_struts_action=%2Flogin%2Flogin")
                .then().extract().response();
        //String authToken = login_endpoint.getCookie("JSESSIONID");
        Map<String, String> authToken = login_endpoint.getCookies();
        login_endpoint = given()
                .parameter("Cookie", authToken)
                .parameter("_3_keywords", "pear")
                .parameter("_3_selected_scope", "product")
                .get("https://staging.ict4apps.aimprosoft.com/search/-/search/")
                .then().extract().response();
        int i = 0;

    }


    @Test
    public void checkCaseRestAssured() {
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
        loginRequest.addHeader("Origin", "https://chat.aimprosoft.com");
        loginRequest.addHeader("Content-Type", "application/x-www-form-urlencoded");
        loginRequest.addHeader("Referer", "https://chat.aimprosoft.com/index.html");
        loginRequest.addHeader("Accept-Encoding", "gzip, deflate, br");
        loginRequest.addHeader("Accept", "*/*");

        HttpResponse response = client.execute(loginRequest);
        String body = handler.handleResponse(response);
//      JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();
//      jsonObject.get("access_token")

        ObjectMapper mapper = new ObjectMapper();
        JsonNode accessToken = mapper.readTree(body).get("access_token");

        for (int i = 1; i <= 100; i++) {
            HttpPost request = new HttpPost("https://chat.aimprosoft.com/api/rooms/7636/histories");
            StringEntity params = new StringEntity("{\"message\":\"Test HTTP request!!! \",\"isSystem\":false,\"systemMessageType\":\"PUBLIC\"} ");
            request.setEntity(params);
            request.addHeader("Origin", "https://chat.aimprosoft.com");
            request.addHeader("Authorization", "bearer " + accessToken.asText());
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Referer", "https://chat.aimprosoft.com/index.html");
            request.addHeader("Accept-Encoding", "gzip, deflate, br");
            request.addHeader("Accept", "*/*");
            request.addHeader("Accept-Language", "en-US,en;q=0.8");
            response = client.execute(request);
            request.releaseConnection();
        }

        String s="{\"name\":\"HTTProom2\",\"channelType\":\"public\",\"description\":\"qqqqqqqqqqqqqqqqqqqqqqqqqqqqq\",\"members\":[membs]}";
        String m="746,955,923,899,907,915,931,939,947,963,971,979,987,995,1003,1011,1019,1027,1043,1035,1051,1059,1067,1075,1083,1091,1099,1107,1115,1123,1131,1139,1147,1155,1163,1171,1179,1187,3,395,867,875,883,891,156,949,933,901,229,909,917,925,941,957,965,973,981,989,997,1005,1013,1021,1029,1037,1045,1053,1061,1069,1077,1093,1085,1101,1109,1117,1125,1133,1141,1149,1157,1165,1173,1181,1189,5,37,221,261,869,877,885,893,54,86,959,927,879,903,911,919,935,951,943,967,975,983,991,999,1007,1015,1023,1031,1039,1047,1055,1063,1071,1079,1087,1095,1103,1111,1119,1127,1135,1143,1151,1159,1167,1175,1183,23,863,871,887,895,64,945,881,865,905,913,921,929,937,953,961,969,977,985,993,1001,1009,1017,1025,1033,1041,1049,1057,1065,1073,1089,1097,1105,1113,1121,1129,1137,1145,1153,1161,1169,1177,1185,873,889,897";
        for (int i = 3; i <= 20; i++) {
            s=s.replace("HTTProom"+(i-1),"HTTProom"+i);
            s=s.replace("membs", m);
            HttpPost request = new HttpPost("https://chat.aimprosoft.com/api/rooms");
            StringEntity params = new StringEntity(s);
            request.setEntity(params);
            request.addHeader("Origin", "https://chat.aimprosoft.com");
            request.addHeader("Authorization", "bearer " + accessToken.asText());
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Referer", "https://chat.aimprosoft.com/index.html");
            request.addHeader("Accept-Encoding", "gzip, deflate, br");
            request.addHeader("Accept", "*/*");
            request.addHeader("Accept-Language", "en-US,en;q=0.8");
            response = client.execute(request);
            request.releaseConnection();
        }



        loginRequest.releaseConnection();
    }


}
