package com.example.demo.services;

import com.example.demo.bean.Order;
import com.example.demo.bean.Sender;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.SenderRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
public class SenderService {
    @Resource
    private SenderRepository senderRepository;
    @Resource
    private OrderRepository orderRepository;

    @Transactional
    public Order save(Order order){
        return orderRepository.save(order);
    }
    @Transactional
    public Sender save(Sender sender){
        return senderRepository.save(sender);
    }

    public Sender findSenderById(int sid){return  senderRepository.findSendersById(sid);}

     //allocate order
     public Sender findacceptpacksender(){
         return  senderRepository.findacceptpacksender();
     }

     //findCurrentOrder
    public  int findCurrentOrder(int ostatus,  int sid){return orderRepository.findCurrentOrder(ostatus,sid);}
    //login
    public Sender findByUserNameandPassword( String susername,  String spassword){return  senderRepository.findByUserNameandPassword(susername,spassword);}
    //show sender order
    public List<Map<String,Object>> showorderforsender(Integer ostatus, Integer sid){return orderRepository.showorderforsender(ostatus,sid);}
    //show the order for sender
    public Map<String,Object>getTheOrderInformation(int oid){return orderRepository.getTheOrderInfoforSender(oid);}

    //show all sender info
    public List<Map<String,Object>> showAllSenderInfo(){return senderRepository.showAllSenderInfo();}

    //find sender by username
    public Sender findSendersBySusername(String susername){return senderRepository.findSendersBySusername(susername);}
}
