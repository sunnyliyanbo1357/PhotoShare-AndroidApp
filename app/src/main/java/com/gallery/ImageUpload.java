package com.gallery;

/**
 * Created by yanboli on 5/9/17.
 */

public class ImageUpload {

    public String name;
    public String url;

    public String getName(){
        return name;
    }

    public String getUrl() {
        return url;
    }

    public ImageUpload(String name, String url){
        this.name = name;
        this.url = url;
    }
}
