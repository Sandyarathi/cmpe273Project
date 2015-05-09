package Facebook;

import FacebookUser.UPost;
import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.exception.FacebookGraphException;
import com.restfb.types.Post;
import com.restfb.types.User;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

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
    protected TreeMap<String, ArrayList<UPost>> getAllPost(FacebookClient fbClient) {
        TreeMap<String, ArrayList<UPost>> posts = new TreeMap<>();

        return posts;
    }

    public TreeMap<String, ArrayList<UPost>> getHighlights(FacebookClient fbClient) {
        TreeMap<String, ArrayList<UPost>> highlights = new TreeMap<>();
        for (Map.Entry<String, ArrayList<UPost>> entry : getAllPost(fbClient).entrySet()) {
            String key = entry.getKey();
            ArrayList<UPost> value = entry.getValue();
            ArrayList<UPost> topPost = new ArrayList<>();
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
        }
        return highlights;
    }


}

