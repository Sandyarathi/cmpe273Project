package Facebook;

import FacebookUser.UPost;
import com.mongodb.*;
import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.exception.FacebookGraphException;
import com.restfb.types.Post;
import com.restfb.types.User;

import java.net.UnknownHostException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

    
    /*public String[] postClassAttributes = {"userId","postId","postMessage","postMonth","postYear","statusType","story","type","description"
    ,"likesCount","commentCount","rating","postImageURL"};*/
    static String strFirstName;

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
        int flag = 0;
        try {
            User me = fbClient.fetchObject("me", com.restfb.types.User.class);
            userId = me.getId();
            strFirstName = me.getFirstName();
            System.out.println(strFirstName);
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
                            postImageURL = p.getPicture();
                            if(postImageURL == null)
                            {
                                postImageURL = profilePicture;
                            }
                            post.setPostImage(postImageURL);
                            post.setStory(p.getStory());
                            post.setType(p.getType());
                            post.setDescription(p.getDescription());
                            post.setPostYear(postYear);
                            monthPost.add(post);
                            break;
                        case 1:
                            monthPost = new ArrayList<UPost> ();
                            Post count1 = fbClient.fetchObject(p.getId(), Post.class, Parameter.with("fields", "likes.summary(true),comments.summary(true)"));
                            UPost post1 = new UPost(userId, p.getId(), p.getMessage(), postMonth, p.getStatusType(), count1.getLikesCount(), count1.getCommentsCount());
                            postImageURL = p.getPicture();
                            if(postImageURL == null)
                            {
                                postImageURL = profilePicture;
                            }
                            post1.setPostImage(postImageURL);
                            post1.setStory(p.getStory());
                            post1.setType(p.getType());
                            post1.setDescription(p.getDescription());
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

    public TreeMap<String, ArrayList<UPost>> getHighlights(FacebookClient fbClient) {
        TreeMap<String, ArrayList<UPost>> highlights = new TreeMap<String, ArrayList<UPost>>();
        for (Map.Entry<String, ArrayList<UPost>> entry : getAllPost(fbClient).entrySet()) {
            String key = entry.getKey();
            ArrayList<UPost> value = entry.getValue();
            ArrayList<UPost> topPost = new ArrayList<UPost>();
            Iterator it = value.iterator();
            int flag = 0, count = 0;
            while (flag == 0) {
                if (it.hasNext()) {
                    if (count < 5) {
                        UPost currentPost = (UPost) it.next();
                        topPost.add(currentPost);
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
            storeInDatabase(topPost);
       }
        return highlights;
    }
    public void storeInDatabase(ArrayList<UPost> topPost)
    {
        String textUri = "mongodb://cmpe273:cmpe273@ds031651.mongolab.com:31651/facebook_moments";
        MongoClientURI uri = new MongoClientURI(textUri);
        try {
            MongoClient client = new MongoClient(uri);
            DB db = client.getDB("facebook_moments");
            System.out.println("FirstNAME"+ strFirstName);
            if(db.collectionExists(strFirstName))
            {
                DBCollection storeUpdate = db.getCollection(strFirstName);
                for(UPost newPost:topPost)
                {
                    BasicDBObject oldPostData = new BasicDBObject("UserID",newPost.getUserId());
                    BasicDBObject newPostData = new BasicDBObject()
                            .append("UserID",newPost.getUserId())
                            .append("PostID",newPost.getPostId())
                            .append("PostMessage",newPost.getPostMessage())
                            .append("PostMonth",newPost.getPostMonth())
                            .append("PostYear",newPost.getPostYear())
                            .append("statusType",newPost.getStatusType())
                            .append("story",newPost.getStory())
                            .append("type",newPost.getType())
                            .append("description",newPost.getDescription())
                            .append("likescount",newPost.getLikesCount())
                            .append("rating",newPost.getRating())
                            .append("postImageURL",newPost.getPostImage());
                    DBObject update = new BasicDBObject("$set", newPostData);
                    storeUpdate.updateMulti(oldPostData, update);
                }
            }
            else {

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
        }
        catch(UnknownHostException e)
        {
            System.out.println("Could not connected");
        }
    }
    public User getAbout(FacebookClient fbClient){
        User me = fbClient.fetchObject("me", com.restfb.types.User.class);
        String strFirstName = me.getFirstName();
        Facebook.User user1 = new Facebook.User();
        user1.setStrName(strFirstName);
        //repo.save(user1);
        System.out.println("FirstName:" + strFirstName);
        String textUri = "mongodb://cmpe273:cmpe273@ds031651.mongolab.com:31651/facebook_moments";
        MongoClientURI uri = new MongoClientURI(textUri);
       /* try {
            MongoClient client = new MongoClient(uri);
            DB db = client.getDB("facebook_moments");
            DBCollection store = db.getCollection(strFirstName);
            if (db.collectionExists(strFirstName)) {

                //BasicDBObject newUser = new BasicDBObject();
                //newUser.replace("Username",newUser.get("Username"),"Jihirsha");
                //store.save(newUser);
                DBObject query = new BasicDBObject("Username", user1.getStrName());
                DBObject put = new BasicDBObject().append("Username", "Jihirsha");
                DBObject update = new BasicDBObject("$set", put);
                store.update(query, update);
            }
            else
             {
                BasicDBObject postUser = new BasicDBObject();
                postUser.append("Username", strFirstName);
                store.insert(postUser);

            }
        }
        catch(UnknownHostException e)
        {
            System.out.println("Could not connected");
        }
        //if(repo == null)
        {
            //System.out.println("Null REPO");
        }
        //System.out.println(repo.toString());
        //repo.save(strFirstName);*/
        return me;
    }

    public HashMap <String,String> getFriends(FacebookClient fbClient){
        HashMap <String,String> data = new HashMap <String,String>();
        Connection<User> myFriends = fbClient.fetchConnection("me/taggable_friends", User.class, Parameter.with("fields", "id, name,picture"));
        do{
            for (User u: myFriends.getData())
            {
                data.put(u.getName().split(" ")[0],u.getPicture().getUrl());
            }
            myFriends = fbClient.fetchConnectionPage(myFriends.getNextPageUrl(), User.class);
        } while (myFriends.hasNext() && data.size() <50);
        return data;
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

}