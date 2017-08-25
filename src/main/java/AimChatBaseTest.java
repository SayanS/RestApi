import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AimChatBaseTest {
    private String BASE_URL="https://chat.aimprosoft.com";
    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();
    UriComponentsBuilder builder;
    private HttpEntity<String> requestEntity;
    JSONObject requestBody = new JSONObject();
    private ResponseEntity responseEntity;

    @BeforeClass
    private void setUp() {
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Origin", "https://chat.aimprosoft.com");
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        headers.set("Accept", "*/*");
        headers.set("Referer", "https://chat.aimprosoft.com/index.html");
        headers.set("Accept-Encoding", "gzip, deflate, br");
        headers.set("Accept-Language", "en-US,en;q=0.8");
    }

    @AfterMethod
    private void tearDownMethod(){
        requestBody = null;
        builder=null;
        requestEntity=null;
    }

    @Test
    public void isLoginSuccessfull() {
        builder = UriComponentsBuilder.fromHttpUrl(BASE_URL+"/oauth/token?grant_type=password&type=LDAP")
                .queryParam("client_id", "aimprosoft_chat")
                .queryParam("client_secret", "aimprosoft_chat_secret")
                .queryParam("username", "s.garmaev")
                .queryParam("password", "Rfhfylfitkm");
        requestEntity = new HttpEntity(headers);
        responseEntity = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST,
                requestEntity, Object.class);
        Assert.assertEquals(200, responseEntity.getStatusCode().value());
        headers.add("Authorization",((Map<String,String>)responseEntity.getBody()).get("token_type")+" "+((Map<String,String>)responseEntity.getBody()).get("access_token"));
    }

    @Test
    public void isProfileUpdated(){
        List<String> header=new ArrayList<>();
        String requestJson="{\"lastName\":\"GGGGddddGGGGGGGGGGGG\",\"authorities\":[\"ROLE_USER\"],\"locked\":false,\"gravatarUrl\":null,\"muted\":false,\"notDisturb\":false,\"phone\":\"85558888085\",\"whatIDo\":\"TestHTTP\",\"ldapUser\":true,\"hasAvatar\":true,\"online\":true,\"avatarId\":\"083007da-ed43-4e3c-b361-f8cfb1c5b362\",\"username\":\"s.garmaev\",\"skype\":\"Skype\",\"version\":9,\"timezone\":\"Asia/Irkutsk\",\"id\":64,\"firstName\":\"HttpFirstName\",\"email\":\"s.garmaev@aimprosoft.com\",\"rating\":null}";
        builder=UriComponentsBuilder.fromHttpUrl(BASE_URL+"/api/users/user/update");
        header.add("application/json");
        headers.replace("Content-Type",header);
        requestEntity = new HttpEntity(requestJson, headers);

        responseEntity = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST,
                requestEntity, Object.class);
    }

    @Test
    public void isMessageDeleted(){
        List<String> header=new ArrayList<>();
        String requestJson="{\"messageId\":699246}";
        builder=UriComponentsBuilder.fromHttpUrl(BASE_URL+"/api/rooms/4/histories/delete");
        header.add("application/json");
        headers.replace("Content-Type",header);
        requestEntity = new HttpEntity(requestJson, headers);

        responseEntity = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST,
                requestEntity, Object.class);
    }

    @Test
    public void isNotificationUpdated(){
        List<String> header=new ArrayList<>();
        String requestJson="{\"mobileSettings\":{\"sound\":\"default\",\"activityForNotification\":\"ANY\",\"pushTimingEnabled\":false,\"pushTimingInterval\":60},\"userId\":1135,\"markAsReadCondition\":\"DEFAULT_AUTOSCROLL\",\"notDisturbTo\":34500,\"muteAll\":false,\"roomSpecificSettings\":[{\"roomId\":4,\"muteRoom\":true,\"desktopActivityForNotification\":\"ANY\",\"mobileActivityForNotification\":\"ANY\"}],\"notDisturbEnabled\":true,\"notDisturbFrom\":34200,\"desktopSettings\":{\"activityForNotification\":\"MENTION\",\"sound\":\"message.mp3\"},\"unmuteTime\":null,\"emailSettings\":{\"emailNotificationEnabled\":true,\"emailNotificationIntrval\":\"720\"}}";
        builder=UriComponentsBuilder.fromHttpUrl(BASE_URL+"/api/ldap/getAllStateOfFields");
        header.add("application/json");
        headers.replace("Content-Type",header);
        requestEntity = new HttpEntity(requestJson, headers);
        responseEntity = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,
                requestEntity, Object.class);


        builder=UriComponentsBuilder.fromHttpUrl(BASE_URL+"/api/users/notificationSettings/save");
        requestEntity = new HttpEntity(requestJson, headers);
        responseEntity = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST,
                requestEntity, Object.class);
    }

    @Test
    public void isRoomDeleted(){
        List<String> header=new ArrayList<>();
        header.add("application/json");
        headers.replace("Content-Type",header);
        builder=UriComponentsBuilder.fromHttpUrl(BASE_URL+"/api/rooms/7636");
        requestEntity = new HttpEntity(headers);
        responseEntity = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.DELETE,
                requestEntity, Object.class);
        Assert.assertEquals(200,responseEntity.getStatusCode().value());
    }

    @Test
    public void isMessageEdited(){
        List<String> header=new ArrayList<>();
        String requestJson="{\"message\":\"HTTPmessageEdited\",\"index\":658282}";
        header.add("application/json");
        headers.replace("Content-Type",header);
        builder=UriComponentsBuilder.fromHttpUrl(BASE_URL+"/api/rooms/5870/histories/edit");
        requestEntity = new HttpEntity(requestJson,headers);
        responseEntity = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST,
                requestEntity, Object.class);
        Assert.assertEquals(200,responseEntity.getStatusCode().value());
    }


}
