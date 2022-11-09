package com.example.study_with_teddy;

public class GModel {

    String id, title, desc, author;
    public GModel(){}

    public GModel(String id, String title, String desc, String author){
        this.id= id;
        this.title= title;
        this.desc= desc;
        this.author= author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }


    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
}
