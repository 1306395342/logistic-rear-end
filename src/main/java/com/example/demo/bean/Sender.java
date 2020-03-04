package com.example.demo.bean;


import javax.persistence.*;

@Entity
@Table(name = "sender")
public class Sender  implements  java.io.Serializable{
    private static final long  serialVersionUID =  1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int sid;

    private String susername,spassword,sphone,srealname,access_token,timestamp,longitude,latitude ,location;
    private  int sstatus,currentoid;
    public Sender (){super();}

    public Sender(String susername, String spassword, String sphone, String srealname, String access_token, String timestamp, String longitude, String latitude, String location, int sstatus, int currentoid) {
        super();
        this.susername = susername;
        this.spassword = spassword;
        this.sphone = sphone;
        this.srealname = srealname;
        this.access_token = access_token;
        this.timestamp = timestamp;
        this.longitude = longitude;
        this.latitude = latitude;
        this.location = location;
        this.sstatus = sstatus;
        this.currentoid = currentoid;
    }

    public int getSid() {
        return sid;
    }

    public String getSusername() {
        return susername;
    }

    public String getSpassword() {
        return spassword;
    }

    public String getSphone() {
        return sphone;
    }

    public String getSrealname() {
        return srealname;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getSstatus() {
        return sstatus;
    }

    public int getCurrentoid() {
        return currentoid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public void setSusername(String susername) {
        this.susername = susername;
    }

    public void setSpassword(String spassword) {
        this.spassword = spassword;
    }

    public void setSphone(String sphone) {
        this.sphone = sphone;
    }

    public void setSrealname(String srealname) {
        this.srealname = srealname;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setSstatus(int sstatus) {
        this.sstatus = sstatus;
    }

    public void setCurrentoid(int currentoid) {
        this.currentoid = currentoid;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
