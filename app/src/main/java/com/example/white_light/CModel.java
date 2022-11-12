package com.example.white_light;

public class CModel {

    String id, age, height, weight;
    public CModel(){}

    public CModel(String id, String age, String height, String weight){
        this.id= id;
        this.age= age;
        this.height= height;
        this.weight= weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
