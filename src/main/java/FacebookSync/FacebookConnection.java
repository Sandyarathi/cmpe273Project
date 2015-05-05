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
        arr.add("Ruchi");
        arr.add("Piyush");
        arr.add("Mom");
        hash.put("January 2014", arr);
        arr = new ArrayList<String>();
        arr.add("Jack");
        arr.add("Tim");
        hash.put("February 2014", arr);
        return hash;
    }

}
