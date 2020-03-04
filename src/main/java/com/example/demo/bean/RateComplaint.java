package com.example.demo.bean;

import javax.persistence.*;

@Entity
@Table(name = "rate_complaint_request")
public class RateComplaint implements java.io.Serializable{

    private static final long  serialVersionUID =  1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int rid;
    int oid;
    String description;
    int rstatus;

    public RateComplaint(){super();}

    public RateComplaint(int rid,int oid, String description, int rstatus) {
        super();
        this.rid=rid;
        this.oid = oid;
        this.description = description;
        this.rstatus = rstatus;
    }

    public int getRstatus() {
        return rstatus;
    }

    public void setRstatus(int rstatus) {
        this.rstatus = rstatus;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
