package FacebookSync;

import com.restfb.*;
import com.restfb.types.User;
import com.restfb.types.FriendList;
import com.restfb.batch.BatchRequest;
import com.restfb.batch.BatchResponse;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.restfb.batch.BatchRequest.BatchRequestBuilder;
import facebook4j.internal.org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.HttpMethod;
import java.io.IOException;

import com.restfb.types.Post;
import com.restfb.scope.ScopeBuilder;
import com.restfb.scope.UserDataPermissions;


@RestController
public class FacebookConnection {

    private static String appId = "403024159903643";
    private static String appSecretId = "15b0bf950c65802d807eb71ac932820a";
    private static String redirectUrl   = "http://localhost:8080/welcome.jsp";
    private FacebookClient.AccessToken token;

    @RequestMapping(value = "welcome.jsp", method = RequestMethod.GET)
    public void  facebookInit(@RequestParam(value="code") String code) throws IOException{
        token = getFacebookUserToken(code, redirectUrl);


        //return rPosts;
        //System.out.println(fList);
        //return arr;
        //return "Ruchi";
    }

    private FacebookClient.AccessToken getFacebookUserToken(String code, String redirectUrl) throws IOException {
        WebRequestor wr = new DefaultWebRequestor();
        WebRequestor.Response accessTokenResponse = wr.executeGet("https://graph.facebook.com/oauth/access_token?client_id=" + appId + "&redirect_uri=" + redirectUrl + "&client_secret=" + appSecretId + "&code=" + code);
        return DefaultFacebookClient.AccessToken.fromQueryString(accessTokenResponse.getBody());
    }

    public HashMap <String,ArrayList<String>> hello()
    {
        ArrayList<String> arr = new ArrayList<String>();
        HashMap <String,ArrayList<String>> hash  = new HashMap <String,ArrayList<String>>();
        //String accessToken = token.getAccessToken();
        //Date expires = token.getExpires();
        //FacebookClient fbClient = new DefaultFacebookClient(accessToken,Version.VERSION_2_2);
        //User me = fbClient.fetchObject("me",com.restfb.types.User.class);

        //arr.add(accessToken);
        /*Date oneYearAgo =
                new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L * 800L);
        Connection<Post> rPosts = fbClient.fetchConnection("me/posts", Post.class, Parameter.with("until", "yesterday"), Parameter.with(
                "since", oneYearAgo));
        for (Post p: rPosts.getData()){
            arr.add(p.getMessage());
        }
        while (rPosts.hasNext()) {
            rPosts = fbClient.fetchConnectionPage(rPosts.getNextPageUrl(),Post.class);
            for (Post p: rPosts.getData()){
                arr.add(p.getMessage());
            }
        } */

        //return rPosts;
        //System.out.println(fList);
        arr.add("Ruchi");
        arr.add("Piyush");
        arr.add("Mom");
        hash.put("January 2014", arr);
        arr = new ArrayList<String>();
        arr.add("Jack");
        arr.add("Tim");
        hash.put("February 2014", arr);
        return hash;
        //return "Ruchi";
    }

   /* public static void main(String[] args) {
        //String appId = "403024159903643";
        //String appSecret = "15b0bf950c65802d807eb71ac932820a";

        FacebookClient fbClient = new DefaultFacebookClient("CAACEdEose0cBAIckK9r7355XDPgyetocsqKZCkyQURHMj8g9ZACtBpkPsLqoAt2VIc7V5S6jROqjhfsRl3yoDH9naXfiZBv4N6TSl8r4DaDQnURoM5gwGDJ10dR1ai30mC6LgUtRZB56gbi8aeO0H6m6BSGcr9ueZBSSi0nNpoXvCSXalMl4iVPMaeQNqZBwxkCKVnxhllvO3vRw6oycZACUeVeIcwwXeUZD");
        User me = fbClient.fetchObject("749936265121703",com.restfb.types.User.class);
        FriendList fList = fbClient.fetchObject("749936265121703",com.restfb.types.FriendList.class);
        System.out.println(me.getEducation());
        System.out.println();
        BatchRequest meRequest = new BatchRequestBuilder("me").build();
        BatchRequest friendsRequest = new BatchRequestBuilder("me/friends").build();
        List<BatchResponse> results = fbClient.executeBatch(meRequest, friendsRequest);
        BatchResponse resultUser = results.get(0);
        BatchResponse resultFriendList = results.get(1);
        JsonMapper jsonMapper = new DefaultJsonMapper();
        User me = jsonMapper.toJavaObject(resultUser.getBody(), User.class);
        FriendList fList =  jsonMapper.toJavaObject(resultFriendList.getBody(), FriendList.class);
        System.out.println(fList);

    }        */
}
