Facebook Page Indexer
=========================

This program crawls/gets posts from a list of popular facebook pages and creates a collection of objects that can be later used to index it in search engines. Written in JAVA. A goood example of object oriented programming in action.



### How to use

To get posts from any facebook page, use the following class. This class contains all the request handleing to facebook API

        FacebookPosts.java
        ====================

        FacebookPosts cp = new FacebookPosts();
        cp.getPosts("airtelbuzz");


The following class reads the list of page from a already downloaded html in resource folder.
        
        Main.java
        ======================
        
       //get page title, url, number of fan, category
        List<FBPage> fbpages = fbPageList.createPageObjects(pageLinks);
        System.out.println("Total : " + fbpages.size());
        
        //get latest posts of the page
        List<FbPost> fbPosts = new ArrayList<>();
        FacebookPosts cp = new FacebookPosts();


This class reads the list of page from a already downloaded html in resource folder.

### workflow 

        read the pagelist >> get posts from facebook >> create FBPage.java objects to make ready for index
