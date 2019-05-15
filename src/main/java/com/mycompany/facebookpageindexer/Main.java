/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.facebookpageindexer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adnan
 */
public class Main {
    
    public static void main(String args[]){
        
        //get page list
        FacebookPageList fbPageList = new FacebookPageList();
        List<String> pageLinks = fbPageList.getUrlsFromPage();
        
        //get page title, url, number of fan, category
        List<FBPage> fbpages = fbPageList.createPageObjects(pageLinks);
        System.out.println("Total : " + fbpages.size());
        
        //get latest posts of the page
        List<FbPost> fbPosts = new ArrayList<>();
        FacebookPosts cp = new FacebookPosts();
        
        for(FBPage fbPage : fbpages){
            
            fbPosts = new ArrayList<>();
            fbPosts = cp.getPosts(fbPage.getId());
            System.out.println("number of post : " + fbPosts.size());
            
            //600 call per 600 sec
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}
