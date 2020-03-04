package com.example.demo.repository;


import com.example.demo.bean.RateComplaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface RateComplaintRepository   extends JpaRepository<RateComplaint,Integer>, JpaSpecificationExecutor<RateComplaint> {
    @Query(nativeQuery = true,value= "select r.rid,o.oid,s.sid,u.urealname,s.srealname,u.uphone,s.sphone,r.description from rate_complaint_request as r inner join orders as o on o.oid=r.oid inner join package as p on o.pid=p.pid inner join \n" +
            "user as u on u.uid=p.uid inner join sender as s on s.sid=o.sid where rstatus !=1 and rstatus !=-1 ;")
    List<Map<String,Object>> getAllComplaint();

    @Query(nativeQuery = true,value= "select * from  rate_complaint_request as r where r.rid=:rid  ;")
    RateComplaint getThisComplaintById(@Param("rid")Integer rid);
}
