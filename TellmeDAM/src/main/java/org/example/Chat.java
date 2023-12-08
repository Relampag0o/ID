package org.example;

public class Chat {
    //private Date createdat;
    private int id;
    //private Date updatedat;
    private int user1_id;

    
    private String user1_username;
    private int user2_id;
    private String user2_username;

    public Chat() {
    }


    public Chat(int id, int user1Id, String user1Username, int user2Id, String user2Username) {
        this.id = id;
        this.user1_id = user1Id;
        this.user1_username = user1Username;
        this.user2_id = user2Id;
        this.user2_username = user2Username;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getUser1_id() {
        return user1_id;
    }

    public void setUser1_id(int user1_id) {
        this.user1_id = user1_id;
    }

    public String getUser1_username() {
        return user1_username;
    }

    public void setUser1_username(String user1_username) {
        this.user1_username = user1_username;
    }

    public int getUser2_id() {
        return user2_id;
    }

    public void setUser2_id(int user2_id) {
        this.user2_id = user2_id;
    }

    public String getUser2_username() {
        return user2_username;
    }

    public void setUser2_username(String user2_username) {
        this.user2_username = user2_username;
    }
}
