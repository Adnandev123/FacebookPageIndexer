/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.facebookpageindexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author adnan
 */
public class FacebookPageList {

    public FacebookPageList() {

    }

    Connection.Response response = null;

    public Document getDocument(String url) {
        Document doc = null;
        int counter = 0;

        try {
            url = java.net.URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            //Logger.getLogger(WikipediaURLs.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            do {

                response = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                        .timeout(10000)
                        .execute();

                doc = response.parse();
                //System.out.println(response.statusCode());

                if (counter == 5) {
                    break;
                }
                if (counter >= 1) {
                    System.out.println("Status code : " + response.statusCode());
                    System.out.println("Waiting 30 seconds");
                    TimeUnit.SECONDS.sleep(30);
                }
                counter++;

            } while (response.statusCode() != 200);

        } catch (IOException e) {
            // System.out.println("io - " + e);
        } catch (InterruptedException ex) {
            //Logger.getLogger(WikiKnowledgeGraph.class.getName()).log(Level.SEVERE, null, ex);
        }

        return doc;
    }

    public List getUrlsFromPage() {
        List<String> pageLinks = new ArrayList();

        File parentFolder = new File("/home/adnan/NetBeansProjects/FacebookPageIndexer/data/");

        File[] fList = parentFolder.listFiles();
        Arrays.sort(fList);

        for (File file : fList) { //#all the folders of different classes
            System.out.println("Folder : " + file.getPath());

            if (file.isDirectory()) {

                try {

                    File[] nList = file.listFiles();
                    for (File nfile : nList) { //#all the files of current folder(class)
                        if (nfile.isFile()) {
                            BufferedReader br = new BufferedReader(
                                    new InputStreamReader(
                                            new FileInputStream(nfile), "UTF8"));

                            String line = "";
                            String html = "";
                            while ((line = br.readLine()) != null) {
                                html += line;
                            }

                            Document doc = Jsoup.parse(html);

                            Elements links = doc.select("body > div.page > div.content.content--subpages > div > section > section > div > table > tbody > tr > td.name > div > a");

                            for (Element link : links) {
                                String url = link.attr("href");
                                pageLinks.add(url);
                            }

                        }
                    }
                } catch (Exception e) {

                }
            }
            
          
        }

        System.out.println("total pages : " + pageLinks.size());

        return pageLinks;
    }

    public List<FBPage> createPageObjects(List<String> pageList) {

        List<FBPage> fbPages = new ArrayList();
        FBPage fbpage = new FBPage();

        for (String page : pageList) {
            fbpage = new FBPage();
            try {
                Document doc = getDocument(page);

                int itemsOnAccountList = 0;

                String title = "";
                String url = "";
                String image = "";
                int totalFan = 0;
                List<String> categories = new ArrayList<>();
                List<String> relatedPages = new ArrayList<>();

                itemsOnAccountList = doc.select("body > div.page > div.content.content--subpages > div > section > div.account-detail > ul > li").size();

                try {
                    title = doc.select("body > div.page > div.content.content--subpages > div > section > div.page-head > h1 > div").get(0).text();
                } catch (Exception e) {
                    System.out.println("couldn't parse title");
                }

                try {
                    image = doc.select("body > div.page > div.content.content--subpages > div > section > div.account-detail > div.img > img").get(0).attr("src");
                } catch (Exception e) {
                    System.out.println("couldn't parse image");
                }

                if (itemsOnAccountList == 2) {

                    try {
                        url = doc.select("body > div.page > div.content.content--subpages > div > section > div.account-detail > ul > li:nth-child(2) > a").get(0).attr("href");
                    } catch (Exception e) {
                        System.out.println("couldn't parse url");
                    }

                    try {
                        String fan = doc.select("body > div.page > div.content.content--subpages > div > section > div.account-detail > ul > li.odd > span > strong").get(0).text().replaceAll("\\u00a0", "");
                        System.out.println(fan);
                        totalFan = Integer.parseInt(fan.trim());
                    } catch (Exception e) {
                        System.out.println("couldn't parse fan");
                    }
                }

                if (itemsOnAccountList == 3) {

                    try {
                        url = doc.select("body > div.page > div.content.content--subpages > div > section > div.account-detail > ul > li:nth-child(3) > a").get(0).attr("href");
                    } catch (Exception e) {
                        System.out.println("couldn't parse url");
                    }

                    try {
                        String fan = doc.select("body > div.page > div.content.content--subpages > div > section > div.account-detail > ul > li:nth-child(1) > span > strong").get(0).text().replaceAll("\\u00a0", "");
                        System.out.println(fan);
                        totalFan = Integer.parseInt(fan.trim());
                    } catch (Exception e) {
                        System.out.println("couldn't parse fan");
                    }
                }

                Elements cats = doc.select("body > div.page > div.content.content--subpages > div > section > div.account-detail > div.account-tag-list > ul > li > a > i");
                for (Element cat : cats) {
                    categories.add(cat.text());
                }

                Elements relatedPgs = doc.select("body > div.page > div.content.content--subpages > div > section > ul.pages-list > li > a > h3");
                for (Element reletedPg : relatedPgs) {
                    relatedPages.add(reletedPg.text());
                }

                System.out.println("Title : " + title);
                System.out.println("Url : " + url);
                System.out.println("");

                fbpage.setTitle(title);
                fbpage.setUrl(url);
                fbpage.setImage(image);
                fbpage.setTotalFan(totalFan);
                fbpage.setRelatedPages(relatedPages);
                fbpage.setCategories(categories);
                fbpage.setId(url.replace("https://www.facebook.com/",""));

                fbPages.add(fbpage);

            } catch (Exception e) {
                System.out.println("couldn't parse the page : " + page);
            }

        }

        return fbPages;
    }

    public static void main(String args[]) {

        FacebookPageList fbPageList = new FacebookPageList();
        List pageLinks = fbPageList.getUrlsFromPage();

        List fbpages = fbPageList.createPageObjects(pageLinks);
        System.out.println("Total : " + fbpages.size());

    }

}
