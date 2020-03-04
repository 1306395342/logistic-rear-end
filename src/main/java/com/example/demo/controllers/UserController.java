package com.example.demo.controllers;


import com.example.demo.bean.*;
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
@RequestMapping("/user")
public class UserController {
    public static final String SECRET="lyysecret";
    public static final String APPID="version1";

    @Resource
    private UserService userService;
    @Resource
    private SenderService senderService;


    //signup
    @RequestMapping("/signup")
    @ResponseBody
    public Map<String , Integer> signup(String username,String password,String phone,String realname){
        Map<String , Integer> m2 =new HashMap<>();
        System.out.print("lyyyy"+username+" "+password+" "+" " +phone+" "+realname);
        if(username.equals("")||password.equals("")||phone.equals("")||realname.equals("")){
            m2.put("code",-1);
            return m2;
        }
        Users ujudge=userService.findByUserName(username);
        if(ujudge!=null){
            m2.put("code",-2);
            return m2;
        }
        Users user = new Users();
        user.setUpassword(password);
        user.setUphone(phone);
        user.setUsername(username);
        user.setUrealname(realname);
        Users userssave=userService.save(user);
        m2.put("code",1);
        return m2 ;
    }

    //login
    @RequestMapping("/login")
    @ResponseBody
    public Map<String , Object> judge(String username,String password,String timestamp){
        Users user = userService.findByUserNameandPasswords(username,password);
        Map<String , Object> m2 =new HashMap<>();
        if(user==null){
            m2.put("code",-1);
            return m2;
        }
        String mixstring = username+password+SECRET+APPID;
        String token = Md5Util.getMD5String(mixstring);
        user.setAccess_token(token);
        user.setTimestamp(timestamp);
        userService.save(user);

        m2.put("code",1);
        m2.put("id",user.getUid());
        m2.put("username",user.getUsername());
        m2.put("status",user.getUidentity());
        m2.put("phone",user.getUphone());
        m2.put("token",user.getAccess_token());
        m2.put("realname",user.getUrealname());
        System.out.print(user.getUsername()+" "+password+" ");
        return m2;
    }

    //submit package and  automatically allocate order
    @RequestMapping("/submitpackage")
    @ResponseBody
    public Map<String , Object> submitpackage(String spr,String spdt,String dpr,String dpdt ,int uid,String size) {
        System.out.print(spr+" "+spdt+" "+dpr+" "+dpdt+" "+uid+" "+size);
        Users userlogin = userService.findById(uid);
        Map<String , Object> m2 =new HashMap<>();
        if(spr.equals("")||spdt.equals("")||dpr.equals("")||dpdt.equals("")){
            m2.put("code",-1);
            return m2;
        }

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //在线包裹
        Packages inpack = new Packages();
        inpack.setUid(uid);
        inpack.setCreate_time(sdf.format(d));
        inpack.setFrom_where(spr+spdt);
        inpack.setTo_where(dpr+dpdt);
        inpack.setSize(size);
        inpack.setUphone(userlogin.getUphone());
        inpack.setUreal_name(userlogin.getUrealname());
        userService.save(inpack);
        //自动分配单 ->快递员
        Sender senderaccept = senderService.findacceptpacksender();
        Map<String,Object> queue = userService.findMaxqueue(senderaccept.getSid());
        if(queue.get("queue")==null){
            queue.put("queue",0);
        }
        Order order = new Order();
        order.setUpdate_time("-1");
        order.setEnd_time("-1");
        order.setPid(inpack.getPid());
        order.setAccept_time(sdf.format(d));
        order.setSid(senderaccept.getSid());
        order.setRateforsender(0);
        order.setPackage_queue(Integer.parseInt(queue.get("queue").toString())+1);
        Order ordersucc =  senderService.save(order);
        if(ordersucc==null){
            m2.put("code",-2);
            return m2;
        }
        //该快递员订单量+1
        senderaccept.setSstatus(senderaccept.getSstatus()+1);
        //若该快递员无接单 则作为当前正在进行的订单
        if(senderaccept.getCurrentoid()==-1){
            senderaccept.setCurrentoid(ordersucc.getOid());
            ordersucc.setOstatus(1);
            ordersucc.setUpdate_time(sdf.format(d));
            senderService.save(order);
        }
        Sender sender = senderService.save(senderaccept);
        m2.put("code",1);
        m2.put("sendername",senderaccept.getSrealname());
        m2.put("senderphone",senderaccept.getSphone());
        System.out.println("当前时间："+ senderaccept.getSrealname()+senderaccept.getSphone());
        return m2;
    }

    //user show order
    @RequestMapping("/showuserorder")
    @ResponseBody
    public List<Map<String,Object>> showUserOrder(int orderstatus,int uid)
    {
        System.out.print("orderstatus: "+orderstatus);
        List<Map<String,Object>> list = userService.showorder(orderstatus,uid);
        return list;
    }

    @RequestMapping("/getTheOrderInformation")
    @ResponseBody
    public Map<String,Object>getTheOrderInformation(int oid)
    {
         Map<String,Object> list = userService.getTheOrderInfo(oid);
        return list;
    }


    @RequestMapping("/complaintorder")
    @ResponseBody
    public Map<String,Object>applycomplaint(int oid,String description)
    {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String , Object> m2 =new HashMap<>();
        if(description.equals("")){
            m2.put("code","-1");
            return m2;
        }
        RateComplaint rateComplaint=new RateComplaint();
        rateComplaint.setOid(oid);
        rateComplaint.setRstatus(0);
        rateComplaint.setDescription(description);

        userService.save(rateComplaint);

        Order thisOrder = userService.findByOrderId(oid);
        thisOrder.setOstatus(-2);
        thisOrder.setUpdate_time(sdf.format(d));
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
        m2.put("code","1");

        return m2;
    }

    @RequestMapping("/searchorderbyId")
    @ResponseBody
    public Map<String , Object> searchOrderByOid(int oid){
        Map<String , Object> m2 =new HashMap<>();
       if((oid+"").equals("")){
           m2.put("code",-1);
            return m2;}
       Order findOrder = userService.findByOrderId(oid);
       if(findOrder==null){
           m2.put("code",-2);
           return m2;
       }
       m2.put("ostatus",findOrder.getOstatus());
       m2.put("code",1);
        return m2;
    }

    @RequestMapping("/setrating")
    @ResponseBody
    public Map<String , Object> setRating(int oid,int rateforsender){
        Map<String , Object> m2 =new HashMap<>();
        Order thisOrder = userService.findByOrderId(oid);
        thisOrder.setRateforsender(rateforsender);
        senderService.save(thisOrder);
        m2.put("code",1);
        return m2;
    }

    //get location
    @RequestMapping("/getlocation")
    @ResponseBody
    public Map<String , Object> saveLocation(int oid) {
        Map<String , Object> m2 =new HashMap<>();
        Order order = userService.findByOrderId(oid);
        Sender sender = senderService.findSenderById(order.getSid());
        if(sender.getLocation()==null){
            m2.put("code",-1);return m2;
        }
        else if(sender.getCurrentoid()!=oid){m2.put("code",-2);return m2;}
        m2.put("code",1);
        m2.put("longitude",sender.getLongitude());
        m2.put("latitude",sender.getLatitude());
        m2.put("location",sender.getLocation());
        return m2;
    }

    //test
    @RequestMapping("/hello")
    @ResponseBody
    public Map<String , Object> hello(){
        Map<String , Object> m2 =new HashMap<>();
        m2.put("hello",-2);
        return m2;
    }
}
