package Facebook;

import FacebookUser.UPost;
import com.mongodb.*;
import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.exception.FacebookGraphException;
import com.restfb.types.Photo;
import com.restfb.types.Post;
import com.restfb.types.User;
import facebookFriendPhotos.FacebookPhotoFinder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.UnknownHostException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class calls functions to fetch User's FacebookDesign Highlights
 * Created by Nakul Sharma on 20-04-2015.
 * Updated by Nakul Sharma on 13-05-2015.
 */
public class FacebookDesign {

    static String strFirstName;

    protected TreeMap<String, ArrayList<UPost>> getAllPost(FacebookClient fbClient) {
        TreeMap<String, ArrayList<UPost>> posts = new TreeMap<String, ArrayList<UPost>>();
        ArrayList<UPost> monthPost = new ArrayList<UPost>();
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
            User me = fbClient.fetchObject("me", com.restfb.types.User.class);
            userId = me.getId();
            strFirstName = me.getFirstName();
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
                    if (!currentDate.equals(dateFormat.parse(postYear + "-" + numericMonth))) {
                        Collections.sort(monthPost);
                        if (!monthPost.isEmpty())
                            posts.put(dateFormat.format(currentDate), monthPost);
                        currentDate = dateFormat.parse(postYear + "-" + numericMonth);
                        flag = 1;
                    } else {
                        flag = 0;
                    }
                    String postImageURL = null;
                    switch (flag) {
                        case 0:
                            Post count = fbClient.fetchObject(p.getId(), Post.class, Parameter.with("fields", "likes.summary(true),comments.summary(true)"));
                            UPost post = new UPost(userId, p.getId(), p.getMessage(), postMonth, p.getStatusType(), count.getLikesCount(), count.getCommentsCount());
                            if (p.getDescription() != null)
                                message = p.getDescription();
                            else if (p.getMessage() != null)
                                message = p.getMessage();
                            else if (p.getStory() != null)
                                message = p.getStory();
                            else if (p.getStatusType() != null)
                                message = p.getStatusType();
                            else if (p.getType() != null)
                                message = p.getType();
                            else
                                message = "Default Message: User did not mention any message";
                            postImageURL = p.getPicture();
                            if (postImageURL == null) {
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
                            monthPost = new ArrayList<UPost>();
                            Post count1 = fbClient.fetchObject(p.getId(), Post.class, Parameter.with("fields", "likes.summary(true),comments.summary(true)"));
                            UPost post1 = new UPost(userId, p.getId(), p.getMessage(), postMonth, p.getStatusType(), count1.getLikesCount(), count1.getCommentsCount());
                            if (p.getDescription() != null)
                                message = p.getDescription();
                            else if (p.getMessage() != null)
                                message = p.getMessage();
                            else if (p.getStory() != null)
                                message = p.getStory();
                            else if (p.getStatusType() != null)
                                message = p.getStatusType();
                            else if (p.getType() != null)
                                message = p.getType();
                            else
                                message = "Default Message: User did not mention any message";
                            postImageURL = p.getPicture();
                            if (postImageURL == null) {
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
        NavigableMap<String, ArrayList<UPost>> sortedHighlights;
        JSONObject picObj = new JSONObject();
        JSONArray friends = new JSONArray();
        ArrayList<UPost> topPosts = new ArrayList<UPost>();
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
            topPosts.addAll(topPost);
            //repo.save(topPost);
        }
        storeInDatabase(topPosts);
        sortedHighlights = highlights.descendingMap();
        for (Map.Entry entry : sortedHighlights.entrySet()) {
            picObj = new JSONObject();
            DateFormatSymbols dfs = new DateFormatSymbols();
            String[] alphabeticMonth = dfs.getMonths();
            String str[] = entry.getKey().toString().split("-");
            int numericMonth = Integer.parseInt(str[1]);
            if (numericMonth >= 1 && numericMonth <= 12) {
                str[1] = alphabeticMonth[numericMonth - 1];
            }
            picObj.put("Month", str[1] + " " + str[0]);
            picObj.put("Post", entry.getValue());
            friends.add(picObj);
        }
        ArrayList<String> pics = getPhotoMoments(highlights, fbClient);
        picObj = new JSONObject();
        picObj.put("Posts", friends);
        picObj.put("Pics", pics);
        return picObj;
    }

    public void storeInDatabase(ArrayList<UPost> topPost) {
        String textUri = "mongodb://cmpe273:cmpe273@ds031651.mongolab.com:31651/facebook_moments";
        MongoClientURI uri = new MongoClientURI(textUri);
        try {
            MongoClient client = new MongoClient(uri);
            DB db = client.getDB("facebook_moments");
            if (db.collectionExists(strFirstName)) {
                DBCollection storeUpdate = db.getCollection(strFirstName);
                for (UPost newPost : topPost) {
                    BasicDBObject oldPostData = new BasicDBObject("UserID", newPost.getUserId());
                    BasicDBObject newPostData = new BasicDBObject()
                            .append("UserID", newPost.getUserId())
                            .append("PostID", newPost.getPostId())
                            .append("PostMessage", newPost.getPostMessage())
                            .append("PostMonth", newPost.getPostMonth())
                            .append("PostYear", newPost.getPostYear())
                            .append("statusType", newPost.getStatusType())
                            .append("story", newPost.getStory())
                            .append("type", newPost.getType())
                            .append("description", newPost.getDescription())
                            .append("likescount", newPost.getLikesCount())
                            .append("rating", newPost.getRating())
                            .append("postImageURL", newPost.getPostImage());
                    DBObject update = new BasicDBObject("$set", newPostData);
                    storeUpdate.updateMulti(oldPostData, update);
                }
            } else {

                DBCollection store = db.getCollection(strFirstName);
                // System.out.println(topPost.size());
                for (UPost postData : topPost) {
                    BasicDBObject post = new BasicDBObject();
                    post.append("UserID", postData.getUserId());
                    post.append("PostID", postData.getPostId());
                    post.append("PostMessage", postData.getPostMessage());
                    post.append("postMonth", postData.getPostMonth());
                    post.append("postYear", postData.getPostYear());
                    post.append("statusType", postData.getStatusType());
                    post.append("story", postData.getStory());
                    post.append("type", postData.getType());
                    post.append("description", postData.getDescription());
                    post.append("likesCount", postData.getLikesCount());
                    post.append("rating", postData.getRating());
                    post.append("postImageURL", postData.getPostImage());
                    store.insert(post);
                }
            }
        } catch (UnknownHostException e) {
            System.out.println("Could not connected");
        }
    }


    public User getAbout(FacebookClient fbClient) {
        User me = fbClient.fetchObject("me", com.restfb.types.User.class);
        return me;
    }

    public JSONArray getFriends(FacebookClient fbClient) {
        JSONObject picObj = new JSONObject();
        JSONArray friends = new JSONArray();
        Connection<User> myFriends = fbClient.fetchConnection("me/taggable_friends", User.class, Parameter.with("fields", "id, name,picture"));
        int i = 0;
        do {
            for (User u : myFriends.getData()) {
                picObj = new JSONObject();
                picObj.put("name", u.getName().split(" ")[0]);
                picObj.put("link", u.getPicture().getUrl());
                friends.add(picObj);
                i++;
            }

            myFriends = fbClient.fetchConnectionPage(myFriends.getNextPageUrl(), User.class);
        } while (myFriends.hasNext() && i < 50);
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
            }
            return pics;

        }
        return new ArrayList<String>();
    }

}