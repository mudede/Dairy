package com.example.dairy.entity;


import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "DiaryEntity")
public class DiaryEntity {
    @Column(name = "id",isId = true)
    private int id;
    @Column(name = "date")
    private String date;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "weather")
    private String weather;

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
