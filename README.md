# FacebookPosts.java

        FacebookPosts cp = new FacebookPosts();
        cp.getPosts("airtelbuzz");

This class contains all the request handleing to facebook API

# Main.java

       //get page title, url, number of fan, category
        List<FBPage> fbpages = fbPageList.createPageObjects(pageLinks);
        System.out.println("Total : " + fbpages.size());
        
        //get latest posts of the page
        List<FbPost> fbPosts = new ArrayList<>();
        FacebookPosts cp = new FacebookPosts();


This class reads the list of page from a already downloaded html in resource folder.
workflow : read the pagelist >> get posts from facebook >> create FBPage.java objects to make ready for index