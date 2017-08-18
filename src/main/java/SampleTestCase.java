import com.jayway.restassured.response.Response;
import controllers.UserController;
import models.User;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
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
    //_3_formDate	"1503048151679"
    //_3_cur	"1"
    //_3_format	""
    //_3_keywords	"apple"
    //_3_selected_scope	"product"
    //_3_documentsSearchContainerPrimaryKeys	"PRODUCT_PORTLET_ECOMMERCE_ENTITY_id_327,PRODUCT_PORTLET_ECOMMERCE_ENTITY_id_331,PRODUCT_PORTLET_ECOMMERCE_ENTITY_id_325"

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
