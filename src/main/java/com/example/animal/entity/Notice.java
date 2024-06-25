package com.example.animal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Date;
import java.util.Objects;

@Entity
public class Notice {
    @Id
    private Long id;
    private String title;
    private String content;
    private Date publishedTime;
    public Notice(){}
    public Notice(String title, String content, Date publishedTime) {
        this.title = title;
        this.content = content;
        this.publishedTime = publishedTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(Date publishedTime) {
        this.publishedTime = publishedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notice notice)) return false;
        return Objects.equals(id, notice.id) && Objects.equals(title, notice.title) && Objects.equals(content, notice.content) && Objects.equals(publishedTime, notice.publishedTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, publishedTime);
    }

    @Override
    public String toString() {
        return "notice{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", publishedTime=" + publishedTime +
                '}';
    }
}
