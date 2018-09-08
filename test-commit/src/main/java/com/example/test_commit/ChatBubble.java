package com.example.test_commit;

/**
 * Created by ThanhHang on 10/15/2017.
 */

public class ChatBubble {

    private String content;
    private int myMessage;

    public ChatBubble(String content, int myMessage) {
        this.content = content;
        this.myMessage = myMessage;
    }

    public String getContent() {
        return content;
    }

    public int myMessage() {
        return myMessage;
    }
}