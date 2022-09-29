package com.junglog.api.request;

public class PostRequest {

    public String title;
    public String content;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "PostRequest{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
