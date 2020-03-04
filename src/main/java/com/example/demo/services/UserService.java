package com.example.demo.services;

import com.example.demo.bean.Order;
import com.example.demo.bean.Packages;
import com.example.demo.bean.RateComplaint;
import com.example.demo.bean.Users;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PackageRepository;
import com.example.demo.repository.RateComplaintRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private PackageRepository packageRepository;
    @Resource
    private OrderRepository orderRepository;
    @Resource
    private RateComplaintRepository rateComplaintRepository;

    //User sign up
    @Transactional
    public Users save(Users user){
        return userRepository.save(user);
    }
    //submit packages
    @Transactional
        public Packages save(Packages pack){return packageRepository.save(pack);}
      //submit complaint
      @Transactional
      public RateComplaint save(RateComplaint rateComplaint){return rateComplaintRepository.save(rateComplaint);}
    //find repetitive username
    public Users findByUserName(String username){
        return userRepository.findByUserName(username);
    }
    //find Order By Id
    public Order findByOrderId(int oid){return orderRepository.findByOrderId(oid);}

    //login judge
    public Users findByUserNameandPasswords(String username,String password) {
        return userRepository.findByUserNameandPassword(username,password);
    }
    //submitpackage

    //find user by id
    public Users findById(int id){
        return userRepository.findById(id).get();
    }
    //set package queue
    public   Map<String,Object> findMaxqueue(int sid){return packageRepository.findMaxqueue(sid);}
    //show userorder
    public List<Map<String,Object>> showorder(Integer ostatus, Integer uid){return orderRepository.showorder(ostatus,uid);};
    //get the  order  info
    public Map<String,Object> getTheOrderInfo(@Param("oid")Integer oid){return orderRepository.getTheOrderInfo(oid);}

}
