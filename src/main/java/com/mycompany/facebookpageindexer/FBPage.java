/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.facebookpageindexer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adnan
 */
public class FBPage {

    private String title = "";
    private String id = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    private String url = "";
    private String image = "";
    private int totalFan = 0;
    private List<String> categories = new ArrayList<>();
    private List<String> RelatedPages = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getTotalFan() {
        return totalFan;
    }

    public void setTotalFan(int totalFan) {
        this.totalFan = totalFan;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getRelatedPages() {
        return RelatedPages;
    }

    public void setRelatedPages(List<String> RelatedPages) {
        this.RelatedPages = RelatedPages;
    }

}
