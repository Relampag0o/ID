package org.example;

public class Message {
    private String content;
    private Integer id;
    private Integer idsender;

    public Message() {

    }

    public Message(String content, Integer id, Integer idSender) {
        this.content = content;
        this.id = id;
        this.idsender = idSender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdsender() {
        return idsender;
    }

    public void setIdsender(Integer idsender) {
        this.idsender = idsender;
    }
}
