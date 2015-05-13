package Facebook;

        import FacebookUser.UPost;
        import com.restfb.DefaultFacebookClient;
        import com.restfb.DefaultWebRequestor;
        import com.restfb.FacebookClient;
        import com.restfb.WebRequestor;
        import com.restfb.json.JsonArray;
        import org.apache.log4j.Logger;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;
        import org.springframework.web.bind.annotation.RequestParam;
        import org.springframework.web.bind.annotation.RestController;

        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.TreeMap;
        import com.restfb.types.User;
        import com.restfb.types.FacebookType;
        import com.restfb.types.Photo;
        import com.restfb.types.User.Picture;

        import facebookFriendPhotos.FacebookPhotoFinder;
        import facebookFriendProfiles.FriendProfiles;
        import facebookPostStory.PostStory;
        import  java.util.Map;
        import org.json.simple.JSONObject;
        import org.json.simple.JSONArray;
import java.util.List;
@RestController
public class FController {
    private static final Logger logger = Logger.getLogger(FController.class);
    public static String redirectUrl   = "http://localhost:8080/welcome.jsp";
    public static FacebookClient fbClient;
    public static FacebookClient.AccessToken token;
    FacebookPhotoFinder facebookPhotoFinder = new FacebookPhotoFinder();

    PostStory postStory = new PostStory();

    FriendProfiles friends = new FriendProfiles();

    FacebookDesign fb = new FacebookDesign();
    Mail mail = new Mail();

    /*--------------------------Welcome Page ---------------------------------*/
    /*@RequestMapping(value = "/action", method = RequestMethod.GET)
    public ResponseEntity fbConnect(@RequestParam(value = "code") String code) throws IOException {
        String redirectUrl = "http://localhost:8080/welcome.jsp/action";
        FacebookClient.AccessToken token = getFacebookUserToken(code, redirectUrl);
        String accessToken = token.getAccessToken();
        FacebookClient fbClient = new DefaultFacebookClient(accessToken);
        FacebookDesign fb = new FacebookDesign();
        TreeMap<String, ArrayList<UPost>> allPosts = fb.getHighlights(fbClient);
        //TreeMap<String, ArrayList<UPost>> allPosts = fb.getAllPost(fbClient);
        if (!allPosts.isEmpty())
            return new ResponseEntity(allPosts, HttpStatus.OK);
        else
            return new ResponseEntity("There are no Highlights to display currently", HttpStatus.BAD_REQUEST);
    }*/


    @RequestMapping(value = "/highlights", method = RequestMethod.GET)
    public JSONObject getResult() {
        FacebookDesign fb = new FacebookDesign();
        return fb.getHighlights(fbClient);
    }

    @RequestMapping(value = "/userName", method = RequestMethod.GET)
    public String getMyData() {
        FacebookDesign fb = new FacebookDesign();
        User me = fb.getAbout(fbClient);
        String name =   me.getFirstName() + " " +   me.getLastName();
        return name;
    }

    @RequestMapping(value = "/profilePic", method = RequestMethod.GET)
    public String getProfilePic() {
        FacebookDesign fb = new FacebookDesign();
        User me = fb.getAbout(fbClient);
        String profilePicture = "https://graph.facebook.com/" + me.getId() + "/picture?type=large";
        return profilePicture;
    }

    public User getInfo() {
        FacebookDesign fb = new FacebookDesign();
        return fb.getAbout(fbClient);
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public JSONObject getInformation() {
        FacebookDesign fb = new FacebookDesign();
        User me = fb.getAbout(fbClient);
        JSONObject userObj = new JSONObject();
        userObj.put("BirthDate", me.getBirthday());
        userObj.put("Hometown", me.getHometown().getName());
        userObj.put("Gender", me.getGender());
        userObj.put("Interested", me.getInterestedIn().get(0));
        userObj.put("Relationship", me.getRelationshipStatus());
        return userObj;
    }

    @RequestMapping(value = "/friendPics", method = RequestMethod.GET)
    public JSONArray getFriends() {
        FacebookDesign fb = new FacebookDesign();
        return fb.getFriends(fbClient);
    }

    public void publishStory(String story, String emailAddress) {

        // to post a story to logged in users wall
        //FacebookType publishMessageResponse = postStory.PostOnWall(fbClient, story);
        //mail.sendEmail(emailAddress,story);
        //return publishMessageResponse;

    }
    /*public List<Picture> getProfilePhotos(FacebookClient fbClient) {
        // to get profile photos of friends of logged in user
        List<Picture> friendProfilePhotos = friends.getProfilePhotos(fbClient);

        logger.info(String.format("Found %s profiles",
                friendProfilePhotos.size()));
        for (Picture profilePicture : friendProfilePhotos) {
            System.out.println(profilePicture.getUrl());
        }
        return friendProfilePhotos;
    }*/


    /*---------------------------------Generate User Token --------------------------------------------------*/
    public FacebookClient.AccessToken getFacebookUserToken(String code, String redirectUrl) throws IOException {
        String appId = "403024159903643";
        String appSecretId = "15b0bf950c65802d807eb71ac932820a";
        WebRequestor wr = new DefaultWebRequestor();
        WebRequestor.Response accessTokenResponse = wr.executeGet("https://graph.facebook.com/oauth/access_token?client_id=" + appId + "&redirect_uri=" + redirectUrl + "&client_secret=" + appSecretId + "&code=" + code);
        return DefaultFacebookClient.AccessToken.fromQueryString(accessTokenResponse.getBody());
    }
}