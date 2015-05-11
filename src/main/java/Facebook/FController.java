package Facebook;

import FacebookUser.UPost;
import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultWebRequestor;
import com.restfb.FacebookClient;
import com.restfb.WebRequestor;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

@RestController
public class FController {
    private static final Logger logger = Logger.getLogger(FController.class);


    /*--------------------------Welcome Page ---------------------------------*/
    @RequestMapping(value = "welcome.jsp/action", method = RequestMethod.GET)
    public ResponseEntity fbConnect(@RequestParam(value = "code") String code) throws IOException {
        String redirectUrl = "http://localhost:8080/welcome.jsp/action";
        FacebookClient.AccessToken token = getFacebookUserToken(code, redirectUrl);
        String accessToken = token.getAccessToken();
        FacebookClient fbClient = new DefaultFacebookClient(accessToken);
        FacebookDesign fb = new FacebookDesign();
        TreeMap<String, ArrayList<UPost>> allPosts = fb.getHighlights(fbClient);
        NavigableMap<String, ArrayList<UPost>> nmap = allPosts.descendingMap();
        if (!nmap.isEmpty())
            return new ResponseEntity<>(nmap, HttpStatus.OK);
        else
            return new ResponseEntity<>("There are no Highlights to display currently", HttpStatus.BAD_REQUEST);
    }


    /*---------------------------------Generate User Token --------------------------------------------------*/
    private FacebookClient.AccessToken getFacebookUserToken(String code, String redirectUrl) throws IOException {
        String appId = "403024159903643";
        String appSecretId = "15b0bf950c65802d807eb71ac932820a";
        WebRequestor wr = new DefaultWebRequestor();
        WebRequestor.Response accessTokenResponse = wr.executeGet("https://graph.facebook.com/oauth/access_token?client_id=" + appId + "&redirect_uri=" + redirectUrl + "&client_secret=" + appSecretId + "&code=" + code);
        return DefaultFacebookClient.AccessToken.fromQueryString(accessTokenResponse.getBody());
    }
}