package com.example.demo.bean;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order implements java.io.Serializable {
    private static final long  serialVersionUID =  1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private  int oid;

    private int pid,sid,ostatus,package_queue,rateforsender;
    private  String accept_time,update_time,end_time,description,location,longitude,latitude;

    public Order(){super();}
    public Order(int pid, int sid, int ostatus, String accept_time, String update_time, String end_time, String description, String location, int rateforsender,int package_queue
    ,String  longitude,String latitude) {
        super();
        this.pid = pid;
        this.sid = sid;
        this.ostatus = ostatus;
        this.accept_time = accept_time;
        this.update_time = update_time;
        this.end_time = end_time;
        this.description = description;
        this.location = location;
        this.rateforsender = rateforsender;
        this.package_queue = package_queue;
        this.longitude=longitude;
        this.latitude=latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public int getOid() {
        return oid;
    }

    public int getPid() {
        return pid;
    }

    public int getSid() {
        return sid;
    }

    public int getOstatus() {
        return ostatus;
    }

    public String getAccept_time() {
        return accept_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public int getRateforsender() {
        return rateforsender;
    }

    public int getPackage_queue() {
        return package_queue;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public void setOstatus(int ostatus) {
        this.ostatus = ostatus;
    }

    public void setAccept_time(String accept_time) {
        this.accept_time = accept_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setRateforsender(int rateforsender) {
        this.rateforsender = rateforsender;
    }

    public void setPackage_queue(int package_queue) {
        this.package_queue = package_queue;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
