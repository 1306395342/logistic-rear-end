package com.example.demo.repository;

import com.example.demo.bean.Sender;
import com.example.demo.bean.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface SenderRepository extends JpaRepository<Sender,Integer>, JpaSpecificationExecutor<Sender> {
    @Query("select s from Sender s where s.susername=:susername and s.spassword=:spassword")
    Sender findByUserNameandPassword(@Param("susername") String susername, @Param("spassword") String spassword);

    @Query(nativeQuery = true,value= "select  * from  sender   order by sstatus asc limit 1 ")
    Sender findacceptpacksender();
    @Query(nativeQuery = true,value= "select  * from  sender   where sid=:sid ")
    Sender findSendersById(@Param("sid") int sid);

    @Query(nativeQuery = true,value= "select  * from  sender   where susername=:susername ")
    Sender findSendersBySusername(@Param("susername") String susername);

    @Query(nativeQuery = true,value= "select  * from  sender  where sstatus != -1 ")
    List<Map<String,Object>> showAllSenderInfo();


}
