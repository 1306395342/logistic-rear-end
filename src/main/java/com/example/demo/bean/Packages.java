package com.example.demo.bean;

import javax.persistence.*;

@Entity
@Table(name = "package")
public class Packages implements java.io.Serializable{
    private static final long  serialVersionUID =  1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private  int pid;

    private  int uid,pstatus;
    String create_time,from_where,to_where,size,uphone,ureal_name;

    public Packages(){super();}

    public Packages(int uid, int pstatus, String create_time, String from_where, String to_where, String size, String uphone, String ureal_name) {
        super();
        this.uid = uid;
        this.pstatus = pstatus;
        this.create_time = create_time;
        this.from_where = from_where;
        this.to_where = to_where;
        this.size = size;
        this.uphone=uphone;
        this.ureal_name=ureal_name;
    }

    public String getUphone() {
        return uphone;
    }

    public String getUreal_name() {
        return ureal_name;
    }

    public int getPid() {
        return pid;
    }

    public int getUid() {
        return uid;
    }

    public int getPstatus() {
        return pstatus;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String getFrom_where() {
        return from_where;
    }

    public String getTo_where() {
        return to_where;
    }

    public String getSize() {
        return size;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setPstatus(int pstatus) {
        this.pstatus = pstatus;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setFrom_where(String from_where) {
        this.from_where = from_where;
    }

    public void setTo_where(String to_where) {
        this.to_where = to_where;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setUphone(String uphone) {
        this.uphone = uphone;
    }

    public void setUreal_name(String ureal_name) {
        this.ureal_name = ureal_name;
    }
}
