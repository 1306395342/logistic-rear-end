package com.example.demo.services;


import com.example.demo.bean.Managers;
import com.example.demo.bean.RateComplaint;
import com.example.demo.bean.Users;
import com.example.demo.repository.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
public class ManagerService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private PackageRepository packageRepository;
    @Resource
    private OrderRepository orderRepository;
    @Resource
    private RateComplaintRepository rateComplaintRepository;
    @Resource
    private ManagerRepository managerRepository;


   public Managers findByManagerNameandPassword(String musername,  String mpassword){return  managerRepository.findByManagerNameandPassword(musername,mpassword);}

   @Transactional
    public Managers save(Managers manager){
        return managerRepository.save(manager);
    }

   public List<Map<String,Object>> getThisSenderAllOrder(Integer sid){return orderRepository.getThisSenderAllOrder(sid);}

   public  List<Map<String,Object>> findAllUser(){return userRepository.findAllUser();}

   public    List<Map<String,Object>> getAllComplaint(){return rateComplaintRepository.getAllComplaint();}

   public  RateComplaint  getThisComplaintById(Integer rid){return rateComplaintRepository.getThisComplaintById(rid);}

   public List<Map<String,Object>> getAllOrder() {return orderRepository.getAllOrder();}

    public List<Map<String,Object>> searchOrderById(int oid) {return orderRepository.searchOrderById(oid);}
}
