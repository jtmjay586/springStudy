package com.example.demo.board.domain;


import java.util.List;

public class BoardDto {
    private int id;
    private String title;
    private List<Reply> reply;


    public int getReplyCount() {
        if(this.reply != null) {
            return reply.size();
        }
        else {
            return 0;
        }
    }

    public List<Reply> getReply() {
        return reply;
    }

    public void setReply(List<Reply> reply) {
        this.reply = reply;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
