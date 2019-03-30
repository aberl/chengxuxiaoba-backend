package com.chengxuxiaoba.video.model.po;

import java.util.Date;

public class AccountMessageRelationShip {
    private Integer id;
    private Integer accountId;
    private Integer messageId;
    private Integer messageCategory;
    private Boolean isRead;
    private Date readDateTime;
    private Date createDateTime;

    private Message message;

    public AccountMessageRelationShip(){}

    public AccountMessageRelationShip(Integer accountId, Integer messageId, Integer messageCategory){
        this.accountId=accountId;
        this.messageId=messageId;
        this.messageCategory=messageCategory;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getMessageCategory() {
        return messageCategory;
    }

    public void setMessageCategory(Integer messageCategory) {
        this.messageCategory = messageCategory;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public Date getReadDateTime() {
        return readDateTime;
    }

    public void setReadDateTime(Date readDateTime) {
        this.readDateTime = readDateTime;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
