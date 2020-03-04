package com.example.demo.bean;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class Users implements java.io.Serializable {
    private static final long  serialVersionUID =  1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int uid;//user id

    private String username;//user name
    private String upassword;
    private int uidentity;
    private String uphone;
    private String urealname;
    private String access_token;
    private String timestamp;


    public  Users(){super();}
    public Users(String username, String upassword, int uidentity, String uphone, String access_token, String timestamp, String urealname) {
        super();
        this.username = username;
        this.upassword = upassword;
        this.uidentity = uidentity;
        this.uphone = uphone;
        this.access_token = access_token;
        this.timestamp = timestamp;
        this.urealname=urealname;
    }


    public void setUrealname(String urealname) {
        this.urealname = urealname;
    }

    public String getUrealname() {
        return urealname;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUpassword(String upassword) {
        this.upassword = upassword;
    }

    public void setUidentity(int uidentity) {
        this.uidentity = uidentity;
    }

    public void setUphone(String uphone) {
        this.uphone = uphone;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public String getUpassword() {
        return upassword;
    }

    public int getUidentity() {
        return uidentity;
    }

    public String getUphone() {
        return uphone;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getTimestamp() {
        return timestamp;
    }


}
