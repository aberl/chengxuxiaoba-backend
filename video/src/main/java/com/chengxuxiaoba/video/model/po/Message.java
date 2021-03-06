package com.chengxuxiaoba.video.model.po;

import java.util.Date;

public class Message {
    private Integer id;
    private String name;
    private String content;
    private Integer status;
    private Integer category;
    private Date createDateTime;

    public static Message build(Integer category, String name, String content) {
        Message message = new Message();
        message.setCategory(category);
        message.setName(name);
        message.setContent(content);

        return message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }
}
