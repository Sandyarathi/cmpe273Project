package Facebook;

import FacebookUser.UPost;
import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.exception.FacebookGraphException;
import com.restfb.types.Photo;
import com.restfb.types.Post;
import com.restfb.types.User;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;
import com.restfb.types.User;
import facebookFriendPhotos.FacebookPhotoFinder;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class calls functions to fetch User's FacebookDesign Highlights
 * Created by Nakul Sharma on 20-04-2015.
 */
public class FacebookDesign {

    /*  private FacebookClient fbClient;
      FacebookDesign(FacebookClient fbClient){
          this.fbClient=fbClient;
      }
  */
    @Autowired
    PostRepository repo;
    protected TreeMap<String, ArrayList<UPost>> getAllPost(FacebookClient fbClient) {
        TreeMap<String, ArrayList<UPost>> posts = new TreeMap<String, ArrayList<UPost>>();
        ArrayList<UPost> monthPost = new ArrayList<UPost> ();
        Date oneYearAgo = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L * 365L);
        String userId, postMonth = "WrongMonth", postYear;
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] alphabeticMonth = dfs.getMonths();
        Date date = new Date();
        String message;
        int flag = 0;
        try {
            User me = fbClient.fetchObject("me", com.restfb.types.User.class, Parameter.with("fields", "id"));
            userId = me.getId();
            String profilePicture = "https://graph.facebook.com/" + userId + "/picture?width=130&height=130";
            Date currentDate = dateFormat.parse(dateFormat.format(date));
            Connection<Post> userPost = fbClient.fetchConnection("me/posts", Post.class, Parameter.with("fields", "id,message,description,status_type,type, story, created_time, picture"), Parameter.with("until", "yesterday"), Parameter.with("since", oneYearAgo));
            do {
                for (Post p : userPost.getData()) {
                    int numericMonth = Integer.parseInt(month.format(p.getCreatedTime()));
                    if (numericMonth >= 1 && numericMonth <= 12) {
                        postMonth = alphabeticMonth[numericMonth - 1];
                    }
                    postYear = year.format(p.getCreatedTime());
                    // System.out.println("Current Month: " + postMonth + " & Year: " + postYear + " & Flag: " + flag);
                    if (!currentDate.equals(dateFormat.parse(postYear + "-" + numericMonth))) {
                        Collections.sort(monthPost);
                        posts.put(dateFormat.format(currentDate), monthPost);
                        currentDate = dateFormat.parse(postYear + "-" + numericMonth);
                        flag = 1;
                        // System.out.println("Date: " + dateFormat.format(currentDate) + " Flag: " + flag);
                    } else {
                        flag = 0;
                    }
                    // System.out.println("Current Month: " + postMonth + " & Year: " + postYear + " & Flag: " + flag);
                    String postImageURL = null;
                    switch (flag) {
                        case 0:
                            Post count = fbClient.fetchObject(p.getId(), Post.class, Parameter.with("fields", "likes.summary(true),comments.summary(true)"));
                            UPost post = new UPost(userId, p.getId(), p.getMessage(), postMonth, p.getStatusType(), count.getLikesCount(), count.getCommentsCount());
                            if (p.getDescription() != null)
                                message = p.getDescription();
                            else if (p.getMessage() != null)
                                message = p.getMessage();
                            else if (p.getStory() !=null)
                                message=p.getStory();
                            else if (p.getStatusType() != null)
                                message = p.getStatusType();
                            else if (p.getType() != null)
                                message = p.getType();
                            else
                                message = "Default Message: User did not mention any message";
                            postImageURL = p.getPicture();
                            if(postImageURL == null)
                            {
                                postImageURL = profilePicture;
                            }
                            post.setPostImage(postImageURL);
                            post.setStory(p.getStory());
                            post.setType(p.getType());
                            post.setDescription(p.getDescription());
                            post.setPostMessage(message);
                            post.setPostYear(postYear);
                            monthPost.add(post);
                            break;
                        case 1:
                            monthPost = new ArrayList<UPost> ();
                            Post count1 = fbClient.fetchObject(p.getId(), Post.class, Parameter.with("fields", "likes.summary(true),comments.summary(true)"));
                            UPost post1 = new UPost(userId, p.getId(), p.getMessage(), postMonth, p.getStatusType(), count1.getLikesCount(), count1.getCommentsCount());
                            if (p.getDescription() != null)
                                message = p.getDescription();
                            else if (p.getMessage() != null)
                                message = p.getMessage();
                            else if (p.getStory() !=null)
                                message=p.getStory();
                            else if (p.getStatusType() != null)
                                message = p.getStatusType();
                            else if (p.getType() != null)
                                message = p.getType();
                            else
                                message = "Default Message: User did not mention any message";
                            postImageURL = p.getPicture();
                            if(postImageURL == null)
                            {
                                postImageURL = profilePicture;
                            }
                            post1.setPostImage(postImageURL);
                            post1.setStory(p.getStory());
                            post1.setType(p.getType());
                            post1.setDescription(p.getDescription());
                            post1.setPostMessage(message);
                            post1.setPostYear(postYear);
                            monthPost.add(post1);
                            break;
                    }
                }
                userPost = fbClient.fetchConnectionPage(userPost.getNextPageUrl(), Post.class);
            } while (userPost.hasNext());

        } catch (FacebookGraphException e) {
            System.out.println("Error: " + e.getErrorCode() + "\nError Message: " + e.getErrorMessage());
            System.out.println("Error Type: " + e.getErrorType() + "\nHttps Status Code" + e.getHttpStatusCode());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return posts;
    }

    public JSONObject getHighlights(FacebookClient fbClient) {
        TreeMap<String, ArrayList<UPost>> highlights = new TreeMap<String, ArrayList<UPost>>();
        JSONObject picObj = new JSONObject();
        JSONArray friends = new JSONArray();
        for (Map.Entry<String, ArrayList<UPost>> entry : getAllPost(fbClient).entrySet()) {
            String key = entry.getKey();
            ArrayList<UPost> value = entry.getValue();
            ArrayList<UPost> topPost = new ArrayList<UPost>();
            Iterator it = value.iterator();
            int flag = 0, count = 0;
            while (flag == 0) {
                if (it.hasNext()) {
                    if (count < 5) {
                        topPost.add((UPost) it.next());
                        count++;
                        flag = 0;
                    } else {
                        flag = 1;
                        count = 1;
                    }
                } else
                    flag = 1;
            }
            highlights.put(key, topPost);
            picObj = new JSONObject();
            DateFormatSymbols dfs = new DateFormatSymbols();
            String[] alphabeticMonth = dfs.getMonths();
            String str[] = entry.getKey().split("-");
            int numericMonth = Integer.parseInt(str[1]);
            if (numericMonth >= 1 && numericMonth <= 12) {
                str[1]= alphabeticMonth[numericMonth - 1];
            }
            picObj.put("Month", str[1] + " " + str[0]);
            picObj.put("Post", topPost);
            friends.add(picObj);
            //repo.save(topPost);
        }
        ArrayList<String> pics = getPhotoMoments(highlights,fbClient);
        picObj = new JSONObject();
        picObj.put("Posts", friends);
        picObj.put("Pics", pics);
        return picObj;
    }

    public User  getAbout(FacebookClient fbClient){
        User me = fbClient.fetchObject("me", com.restfb.types.User.class);
        return me;
    }

    public JSONArray getFriends(FacebookClient fbClient){
        JSONObject picObj = new JSONObject();
        JSONArray friends = new JSONArray();
        Connection<User> myFriends = fbClient.fetchConnection("me/taggable_friends", User.class, Parameter.with("fields", "id, name,picture"));
        int i =0;
        do{
            for (User u: myFriends.getData())
            {
                picObj = new JSONObject();
                picObj.put("name",u.getName().split(" ")[0]);
                picObj.put("link", u.getPicture().getUrl());
                friends.add(picObj);
                i++;
            }

            myFriends = fbClient.fetchConnectionPage(myFriends.getNextPageUrl(), User.class);
        } while (myFriends.hasNext() && i <50);
        return friends;
    }

    public List<UPost> getTopPosts(TreeMap<String, ArrayList<UPost>> allPosts,
                                   FacebookClient fbClient) {
        List<UPost> topPosts = new ArrayList<UPost>();
        Set<String> keySet = allPosts.keySet();

        for (String key : keySet) {
            List<UPost> topPostsOfMonth = allPosts.get(key);
            if (topPostsOfMonth.size() > 0)
                topPosts.add(topPostsOfMonth.get(0));

        }

        return topPosts;

    }

    public ArrayList<String> getPhotoMoments(TreeMap<String, ArrayList<UPost>> allPosts, FacebookClient fbClient) {
        FacebookPhotoFinder facebookPhotoFinder = new FacebookPhotoFinder();
        if (!allPosts.isEmpty()) {
            List<UPost> topPosts = getTopPosts(allPosts, fbClient);
            List<Photo> photoMoments = facebookPhotoFinder.findPhotoMoments(topPosts, fbClient);
            ArrayList<String> pics = new ArrayList<String>();
            for (Photo photo : photoMoments) {
                pics.add(photo.getPicture());
                //System.out.println(photo.getPicture());
            }
            return pics;

        }
        return new ArrayList<String>();
    }

}