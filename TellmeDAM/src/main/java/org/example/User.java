package org.example;

public class User {
    private String email;
    private int id;
    private String password;
    private String photourl;
    private String username;

    public User() {
    }

    public User(String email, int id, String password, String photoUrl, String username) {
        this.email = email;
        this.id = id;
        this.password = password;
        this.photourl = photoUrl;
        this.username = username;
    }

    // Getters y setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
