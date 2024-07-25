package com.example.entity;

public class Emoji {
    private String emoDesc;

    private String emoji;

    public String getEmoDesc() {
        return emoDesc;
    }

    public void setEmoDesc(String emoDesc) {
        this.emoDesc = emoDesc == null ? null : emoDesc.trim();
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji == null ? null : emoji.trim();
    }
}