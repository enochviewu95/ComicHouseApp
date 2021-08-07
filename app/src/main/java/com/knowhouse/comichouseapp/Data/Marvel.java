package com.knowhouse.comichouseapp.Data;

public class Marvel {

    private String id;
    private String imageUrl;
    private String resourceUrl;
    private String name;

    public Marvel(String id, String imageUrl, String name,String resourceUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.resourceUrl = resourceUrl;
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

    public String getResourceUrl() {
        return resourceUrl;
    }
}
