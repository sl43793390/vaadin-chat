package com.example.entity;

import java.util.Date;

public class UserMessage {
    private String userId;

    private String receiveUserId;

    private Long messageId;

    private String message;

    private String imgUrl;

    private String fileUrl;

    private String fileName;

    private String emoji;

    private Date dtSend;

    private String flagDelete;
    public UserMessage() {
		super();
		this.dtSend = new Date();
	}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(String receiveUserId) {
        this.receiveUserId = receiveUserId == null ? null : receiveUserId.trim();
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl == null ? null : fileUrl.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji == null ? null : emoji.trim();
    }

    public Date getDtSend() {
        return dtSend;
    }

    public void setDtSend(Date dtSend) {
        this.dtSend = dtSend;
    }

    public String getFlagDelete() {
        return flagDelete;
    }

    public void setFlagDelete(String flagDelete) {
        this.flagDelete = flagDelete == null ? null : flagDelete.trim();
    }
}