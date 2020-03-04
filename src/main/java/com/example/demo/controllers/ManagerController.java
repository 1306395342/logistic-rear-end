package com.example.demo.controllers;


import com.example.demo.bean.*;
import com.example.demo.services.ManagerService;
import com.example.demo.services.SenderService;
import com.example.demo.services.UserService;
import com.example.demo.util.Md5Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/manager")
public class ManagerController {
    public static final String SECRET="lyysecret";
    public static final String APPID="version1";

    @Resource
    private UserService userService;
    @Resource
    private SenderService senderService;
    @Resource
    private ManagerService managerService;
//管理员登陆页面
    @RequestMapping("/login")
    public String login(Integer error, ModelMap map){
        if(error==null){
            return "login";
        }
        if(error==1){
            map.addAttribute("error", "请先登录");
        }
        return "login";
    }


    @RequestMapping("/index")
    public String userindex(){
        return "managerindex";
    }

    @RequestMapping("/addsender")
    public String addSender(){
        return "addsender";
    }

    @RequestMapping("/showsenderperformance")
    public String ShowsenderPerformance(){
        return "performance";
    }


    @RequestMapping("/showuserinfo")
    public String ShowUserInfo(){
        return "userinfo";
    }

    @RequestMapping("/showusercomplaint")
    public String showUserComplaint(){
        return "usercomplaint";
    }

    @RequestMapping("/showorderandsearch")
    public String showOrderAndSearch(){
        return "showorders";
    }


    //客户投诉处理
    @RequestMapping("/ordersandsearch")
    @ResponseBody
    public  Map<String,Object>  ordersAndSearch(int oid,int code){
        Map<String,Object> responses = new HashMap<>();
        if(code==0||oid==-1){
            List<Map<String,Object>> data = managerService.getAllOrder();
            responses.put("data",data);
            responses.put("count",10);
            responses.put("code",0);
            return responses;
        }
        if(code==1){
                List<Map<String,Object>> data = managerService.searchOrderById(oid);
                responses.put("data",data);
                responses.put("count",10);
                responses.put("code",0);
                return responses;
        }

        return responses;
    }

    //客户投诉处理
    @RequestMapping("/dealcomplaint")
    @ResponseBody
    public  Map<String,Object>  dealecomplaint(int rid,int oid){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        RateComplaint rateComplaint =managerService.getThisComplaintById(rid);
        rateComplaint.setRstatus(1);
        userService.save(rateComplaint);
        Order order = userService.findByOrderId(oid);
        order.setOstatus(-1);
        order.setRateforsender(1);
        order.setEnd_time(sdf.format(d));
        senderService.save(order);
        Map<String,Object> responses = new HashMap<>();
        responses.put("code",0);
        return responses;
    }

    //客户无理投诉
    @RequestMapping("/unreasonablecomplaint")
    @ResponseBody
    public  Map<String,Object>  unreasonablecomplaint(int rid,int oid){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        RateComplaint rateComplaint =managerService.getThisComplaintById(rid);
        rateComplaint.setRstatus(-1);
        userService.save(rateComplaint);
        Order order = userService.findByOrderId(oid);
        order.setOstatus(2);
        order.setEnd_time(sdf.format(d));
        senderService.save(order);
        Map<String,Object> responses = new HashMap<>();
        responses.put("code",0);
        return responses;
    }

    //客户投诉订单
    @RequestMapping("/complaintorders")
    @ResponseBody
    public  Map<String,Object>  showcomplaintorder(){
        List<Map<String,Object>> data =managerService.getAllComplaint();
        Map<String,Object> responses = new HashMap<>();
        responses.put("data",data);
        responses.put("count",10);
        responses.put("code",0);
        return responses;
    }

    //用户信息
    @RequestMapping("/userinfo")
    @ResponseBody
    public  Map<String,Object>  userInfo(){
        List<Map<String,Object>> data =managerService.findAllUser();
        Map<String,Object> responses = new HashMap<>();
        responses.put("data",data);
        responses.put("count",10);
        responses.put("code",0);
        return responses;
    }

    //快递员业绩
    @RequestMapping("/performance")
    @ResponseBody
    public  Map<String,Object>  senderPerformance(){
        List<Map<String,Object>> senders =senderService.showAllSenderInfo();
        List<Map<String,Object>> data =new ArrayList<>();
        int countcomplaining,countcomplained,finish,noDelivery,income;
        int rate1,rate2,rate3,rate4,rate5;
        float average_rating;
        for(int i=0;i<senders.size();i++){
            rate1=rate2=rate3=rate4=rate5=0;
            countcomplaining=0;
            countcomplained=0;
            finish=0;
            average_rating=0;
            noDelivery=0;

            Map<String,Object> sender = senders.get(i);
            Map<String,Object> senderPerformance = new HashMap<>();
            senderPerformance.put("sid",sender.get("sid"));
            senderPerformance.put("srealname",sender.get("srealname"));
            senderPerformance.put("currentoid",sender.get("currentoid"));
            senderPerformance.put("sstatus",sender.get("sstatus"));

            List<Map<String,Object>> thisSenderAllOrder = managerService.getThisSenderAllOrder((int)sender.get("sid"));
            for(int j=0;j<thisSenderAllOrder.size();j++){
                Map<String,Object> order = thisSenderAllOrder.get(j);
                int rate = (int)order.get("rateforsender");
                int ostatus=(int)order.get("ostatus");


                if(ostatus==0){
                        noDelivery++;
                }
                else if(ostatus==2){
                    finish++;
                }
                else if(ostatus==-2){
                    countcomplaining++;
                }
                else if(ostatus==-1){
                    countcomplained++;
                }
                if(rate==0){
                    rate=2;
                    rate2++;
                }
                else if(rate==1){rate1++;}
                else if(rate==2){rate2++;}
                else if(rate==3){rate3++;}
                else if(rate==4){rate4++;}
                else if(rate==5){rate5++;}
                average_rating+=rate;
            }
            average_rating=average_rating/thisSenderAllOrder.size();
            if((average_rating+"").equals("")){
                average_rating=0;
            }

            income = 3420+finish*240-countcomplained*43-countcomplaining*21;
            senderPerformance.put("nodelivery",noDelivery);
            senderPerformance.put("finish",finish);
            senderPerformance.put("countcomplaining",countcomplaining);
            senderPerformance.put("countcomplained",countcomplained);
            senderPerformance.put("average_rating",average_rating);
            senderPerformance.put("income",income);
            senderPerformance.put("rate1",rate1);
            senderPerformance.put("rate2",rate2);
            senderPerformance.put("rate3",rate3);
            senderPerformance.put("rate4",rate4);
            senderPerformance.put("rate5",rate5);
            data.add(senderPerformance);
        }

        Map<String,Object> responses = new HashMap<>();
        responses.put("data",data);
        responses.put("count",10);
        responses.put("code",0);

        return responses;
    }

    //添加快递员信息
    @RequestMapping("/savesender")
    @ResponseBody
    public  Map<String,Object>  saveSender(String username,String password,String phone,String realname){
        System.out.print(username);
        Map<String,Object> responses = new HashMap<>();
        Sender sameSender = senderService.findSendersBySusername(username);
        if(username.equals("")||password.equals("")||phone.equals("")||realname.equals("")){
            responses.put("code",-1);
            return responses;
        }
        if(sameSender!=null){
            responses.put("code",-2);
            return responses;}
        Sender newSender = new Sender();
        newSender.setCurrentoid(-1);
        newSender.setSphone(phone);
        newSender.setSrealname(realname);
        newSender.setSusername(username);
        newSender.setSpassword(password);
        newSender.setSstatus(0);
        senderService.save(newSender);
        responses.put("code",1);
        return responses;
    }


    //解雇快递员
    @RequestMapping("/fireemployee")
    @ResponseBody
    public  Map<String,Object>  fireEmployee(int sid){
        Map<String,Object> responses = new HashMap<>();
        Sender sender = senderService.findSenderById(sid);
        sender.setSstatus(-1);
        senderService.save(sender);
        responses.put("sid",sender.getSid());
        responses.put("code",1);
        return responses;
    }

    //显示快递员信息
    @RequestMapping("/showsenderinfo")
    @ResponseBody
    public  Map<String,Object>  showSenderInfo(){
        List<Map<String,Object>> data =senderService.showAllSenderInfo();
        Map<String,Object> responses = new HashMap<>();
        responses.put("data",data);
        responses.put("count",10);
        responses.put("code",0);
        return responses;
    }
    //登录判定
    @RequestMapping("/judge")
    @ResponseBody
    public Map<String , Object> judge(String musername,String mpassword,String timestamp){
        System.out.print(musername+mpassword+timestamp);
        Map<String , Object> m2 =new HashMap<>();
        Managers managers = managerService.findByManagerNameandPassword(musername,mpassword);
        if(managers==null){
            m2.put("status",-1);
            return m2;
        }
        String mixstring = musername+mpassword+SECRET+APPID;
        String token = Md5Util.getMD5String(mixstring);
        managers.setAccess_token(token);
        managers.setTimestamp(timestamp);
        managerService.save(managers);

        m2.put("status",1);
        m2.put("mid",managers.getMid());
        m2.put("musername",managers.getMusername());
        m2.put("mtoken",managers.getAccess_token());
        return m2;
    }


}
