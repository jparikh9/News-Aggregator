package com.example.newsaggregator;

public class NewsSource {
private String id;
private String name;
private String category;

public NewsSource(){
this.id = "";
this.name = "";
this.category = "";
}

    public void setCategory(String category) {
        this.category = category;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

