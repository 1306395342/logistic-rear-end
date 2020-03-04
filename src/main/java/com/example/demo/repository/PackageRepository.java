package com.example.demo.repository;

import com.example.demo.bean.Packages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

public interface PackageRepository  extends JpaRepository<Packages,Integer>, JpaSpecificationExecutor<Packages> {
    @Query(nativeQuery = true,value= "select MAX(package_queue)queue from orders where sid =:sid ")
    Map<String,Object> findMaxqueue(@Param("sid")Integer sid);
}
