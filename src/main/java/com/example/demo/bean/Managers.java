package com.example.demo.bean;


import javax.persistence.*;

@Entity
@Table(name = "managers")
public class Managers implements java.io.Serializable {
    private static final long  serialVersionUID =  1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int mid;

    private String musername,mpassword,access_token,timestamp;


    public Managers(){super();}

    public Managers(String musername, String mpassword, String access_token, String timestamp) {
        this.musername = musername;
        this.mpassword = mpassword;
        this.access_token = access_token;
        this.timestamp = timestamp;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getMusername() {
        return musername;
    }

    public void setMusername(String musername) {
        this.musername = musername;
    }

    public String getMpassword() {
        return mpassword;
    }

    public void setMpassword(String mpassword) {
        this.mpassword = mpassword;
    }
}
