package com.knowhouse.comichouseapp.Data;

public class Marvel {

    private String id;
    private String imageUrl;
    private String name;

    public Marvel(String id, String imageUrl, String name) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

}
