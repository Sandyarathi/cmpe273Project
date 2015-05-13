import com.restfb.*;
import com.restfb.types.User;
import com.restfb.types.FriendList;
import com.restfb.batch.BatchRequest;
import com.restfb.batch.BatchResponse;
import java.util.List;
import com.restfb.batch.BatchRequest.BatchRequestBuilder;




public class FacebookConnection {
    public static void main(String[] args) {
        //String appId = "403024159903643";
        //String appSecret = "15b0bf950c65802d807eb71ac932820a";

        FacebookClient fbClient = new DefaultFacebookClient("CAACEdEose0cBALjDR7lZCqxMeElV1pMHUQZCwP8tZA9B2j7us1ZBi2Q7ZB8xETMLmZCu3BuFhRTeqhnSyRfwdc7ShM13oi1RZCuZArWUg4BiTB861Wp1RwCZBjnTZAt4kGzA8zt426nmoRif4FgqFgiD6aH760iKZCjI9aO3470pvj1mdYWlJknjVxgZCow7JHCzKC8hZBVkUKkFtgXbIwDQL6OX0");
        User me = fbClient.fetchObject("749936265121703",com.restfb.types.User.class);
        FriendList fList = fbClient.fetchObject("749936265121703",com.restfb.types.FriendList.class);
        System.out.println(me.getEducation());
        System.out.println();
        /*BatchRequest meRequest = new BatchRequestBuilder("me").build();
        BatchRequest friendsRequest = new BatchRequestBuilder("me/friends").build();
        List<BatchResponse> results = fbClient.executeBatch(meRequest, friendsRequest);
        BatchResponse resultUser = results.get(0);
        BatchResponse resultFriendList = results.get(1);
        JsonMapper jsonMapper = new DefaultJsonMapper();
        User me = jsonMapper.toJavaObject(resultUser.getBody(), User.class);
        FriendList fList =  jsonMapper.toJavaObject(resultFriendList.getBody(), FriendList.class);
        System.out.println(fList);*/

    }
}
