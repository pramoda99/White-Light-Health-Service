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

    
    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
