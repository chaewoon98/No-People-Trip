package com.test.mosun.home;

public class areaItem {
    private int id;
    private String img_url;
    private String name;
    private String description;

    public areaItem(int id, String img_url, String name,String description)
    {
        this.id = id;
        this.img_url = img_url;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}
