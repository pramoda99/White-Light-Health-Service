package com.example.study_with_teddy;

public class GModel {

    String id, title;
    public GModel(){}

    public GModel(String id, String title){
        this.id= id;
        this.title= title;
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

}
