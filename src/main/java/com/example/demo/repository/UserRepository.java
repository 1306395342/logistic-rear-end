package com.example.demo.repository;

import com.example.demo.bean.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface UserRepository extends JpaRepository<Users,Integer>, JpaSpecificationExecutor<Users> {
    @Query("select s from Users s where s.username=:username and s.upassword=:upassword ")
    Users findByUserNameandPassword(@Param("username") String username, @Param("upassword") String upassword);

    @Query("select s from Users s where s.username=:username ")
    Users findByUserName(@Param("username") String username);

    @Query(nativeQuery = true,value= "select * from user")
    List<Map<String,Object>> findAllUser();
}
