package com.example.nihue.huetpokedex;

public class ListItem {

    private String head;
    private String description;
    private String imageUrl;
    private String height;
    private String weight;
    private String type;


    public ListItem(String head, String description, String imageUrl, String height, String weight, String type) {
        this.head = head;
        this.description = description;
        this.imageUrl =imageUrl;
        this.height = height;
        this.weight = weight;
        this.type = type;

    }

    public String getHead() {
        return head;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getType() {
        return type;
    }
}

