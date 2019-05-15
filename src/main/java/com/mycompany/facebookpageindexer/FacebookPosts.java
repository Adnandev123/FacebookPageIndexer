/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.facebookpageindexer;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.Post;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adnan
 */
public class FacebookPosts {

    public FacebookPosts() {
    }

    FacebookClient facebookClient = new DefaultFacebookClient(MY_ACCESS_TOKEN, Version.VERSION_2_5);

    public static final String MY_ACCESS_TOKEN = "@@@@";
    public static final String MY_APP_ID = "@@@@";
    public static final String MY_APP_SECRET = "@@@@";
    
    

    public List<FbPost> getPosts(String pageId) {
        long unixTime = 0;
        
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            Date todayDate = dateFormat.parse(dateFormat.format(date));
            unixTime = (long) todayDate.getTime()/1000;
             System.out.println("Time : " + unixTime);
            } catch (ParseException ex) {
           // Logger.getLogger(CollectPosts.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
           
            
            Connection<Post> pages = facebookClient.fetchConnection(pageId.trim() + "posts", Post.class, Parameter.with("limit", 50), Parameter.with("since", "1514764800"), Parameter.with("until", unixTime));
            List<FbPost> fbPosts = new ArrayList<>();
            FbPost fbPost = new FbPost();
            
            for (List<Post> posts : pages) {
                
                for (Post post : posts) {
                    try {
                        fbPost.setText(post.getMessage());
                        fbPost.setTime(post.getCreatedTime());
                        fbPost.setUrl("www.facebook.com/" + post.getId().replaceAll(".*_", ""));
                        
                        System.out.println(post.getCreatedTime());
                        
                        fbPosts.add(fbPost);
                        
                    } catch (Exception e) {
                        
                    }
                }
            }
            
         
        
        
           return fbPosts;
    }

    public static void main(String args[]) {
        FacebookPosts cp = new FacebookPosts();
        cp.getPosts("airtelbuzz");

    }

}
