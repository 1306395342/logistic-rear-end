package com.example.demo.controllers;


import com.example.demo.bean.Order;
import com.example.demo.bean.RateComplaint;
import com.example.demo.bean.Sender;
import com.example.demo.bean.Users;
import com.example.demo.services.SenderService;
import com.example.demo.services.UserService;
import com.example.demo.util.Md5Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sender")
public class SenderController {
    public static final String SECRET="lyysecret";
    public static final String APPID="version1";

    @Resource
    private UserService userService;
    @Resource
    private SenderService senderService;

    //login
    @RequestMapping("/login")
    @ResponseBody
    public Map<String , Object> judge(String username, String password, String timestamp){
        Map<String , Object> m2 =new HashMap<>();
        Sender sender =senderService.findByUserNameandPassword(username,password);
        if(sender==null){
            m2.put("code",-1);
            return m2;
        }
        String mixstring = username+password+SECRET+APPID;
        String token = Md5Util.getMD5String(mixstring);
        sender.setAccess_token(token);
        sender.setTimestamp(timestamp);
        senderService.save(sender);

        m2.put("code",1);
        m2.put("sid",sender.getSid());
        m2.put("susername",sender.getSusername());
        m2.put("sphone",sender.getSphone());
        m2.put("stoken",sender.getAccess_token());
        m2.put("srealname",sender.getSrealname());
        return m2;
    }

    //save location
    @RequestMapping("/savesenderlocation")
    @ResponseBody
    public Map<String , Object> saveLocation(String longitude, String latitude, String location,int sid) {
        Map<String , Object> m2 =new HashMap<>();
        Sender sender = senderService.findSenderById(sid);
        sender.setLongitude(longitude);
        sender.setLatitude(latitude);
        sender.setLocation(location);
        senderService.save(sender);
        m2.put("code",1);
        return m2;
    }
    //user show order
    @RequestMapping("/showsenderorder")
    @ResponseBody
    public List<Map<String,Object>> showSenderOrder(int orderstatus, int sid)
    {
        List<Map<String,Object>> list = senderService.showorderforsender(orderstatus,sid);
        return list;
    }

    @RequestMapping("/getTheOrderInformation")
    @ResponseBody
    public Map<String,Object>getTheOrderInformation(int oid)
    {
        Map<String,Object> list = senderService.getTheOrderInformation(oid);
        return list;
    }

    @RequestMapping("/finishorder")
    @ResponseBody
    public Map<String,Object>finishOrder(int oid)
    {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       Map<String , Object> m2 =new HashMap<>();

        Order thisOrder = userService.findByOrderId(oid);
        thisOrder.setOstatus(2);
        thisOrder.setEnd_time(sdf.format(d));
        thisOrder=senderService.save(thisOrder);
        Sender sender = senderService.findSenderById(thisOrder.getSid());
        int currentoid = sender.getCurrentoid();
        if(currentoid==thisOrder.getOid()){
            int currentoidnew=senderService.findCurrentOrder(0,sender.getSid());
            String currentoidnews=currentoidnew+"";
            //如果存在，选一个新的未配送订单开始配送，不存在设为-1当前再无未配送订单
            if(currentoidnews.equals("")){
                sender.setCurrentoid(-1);
            }
            else{
                sender.setCurrentoid(currentoidnew);
                Order currentneworder=userService.findByOrderId(currentoidnew);
                currentneworder.setOstatus(1);
                senderService.save(currentneworder);
                currentneworder.setUpdate_time(sdf.format(d));
                senderService.save(currentneworder);
            }
            senderService.save(sender);

        }
      m2.put("code",1);

       return m2;
    }



}
