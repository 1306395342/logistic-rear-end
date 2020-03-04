package com.example.demo.repository;

import com.example.demo.bean.Managers;
import com.example.demo.bean.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ManagerRepository extends JpaRepository<Managers,Integer>, JpaSpecificationExecutor<Managers> {

    @Query("select s from Managers s where s.musername=:musername and s.mpassword=:mpassword")
    Managers findByManagerNameandPassword(@Param("musername") String musername, @Param("mpassword") String mpassword);



}
