package com.example.myapplication.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TodoItem extends RealmObject {

    public static final String Id = "id";
    public static final String TAG = "tag";
    @PrimaryKey
    private long id;
    private String description;
    private String title;
    private String tag;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
