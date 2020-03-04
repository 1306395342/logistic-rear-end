package com.example.demo.repository;

import com.example.demo.bean.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface OrderRepository  extends JpaRepository<Order,Integer>, JpaSpecificationExecutor<Order> {
    @Query(nativeQuery = true,value= "select  o.oid,s.srealname,s.sphone,p.to_where from package as p inner join  orders as  o on p.pid=o.pid inner join sender as s on o.sid=s.sid where o.ostatus=:ostatus and p.uid=:uid ;")
    List<Map<String,Object>> showorder(@Param("ostatus")Integer ostatus,@Param("uid")Integer uid);

    @Query(nativeQuery = true,value= "select  o.longitude,o.latitude,p.from_where,p.to_where , o.accept_time , o.update_time,o.end_time, s.sphone , s.srealname,o.rateforsender   from orders  as o inner join sender as s on o.sid=s.sid inner join package as p on o.pid=p.pid where o.oid =:oid ;")
    Map<String,Object> getTheOrderInfo(@Param("oid")Integer oid);

    @Query("select s from Order s where s.oid=:oid ")
    Order findByOrderId(@Param("oid") int oid);

    @Query(nativeQuery = true,value="select oid from orders where ostatus=:ostatus and sid=:sid order by package_queue asc limit 1  ")
    int findCurrentOrder(@Param("ostatus") int ostatus,@Param("sid") int sid);

    @Query(nativeQuery = true,value= "select o.oid,u.urealname,u.uphone,p.to_where from package as p inner join orders as o on p.pid=o.pid inner join user as u on p.uid=u.uid where o.ostatus=:ostatus and o.sid = :sid")
    List<Map<String,Object>> showorderforsender(@Param("ostatus")Integer ostatus,@Param("sid")Integer sid);

    @Query(nativeQuery = true,value= "select  p.from_where,p.to_where , o.accept_time , o.update_time,o.end_time, u.uphone , u.urealname from orders  as o inner join package as p on p.pid=o.pid inner join user as u on u.uid=p.uid where o.oid =:oid ;")
    Map<String,Object> getTheOrderInfoforSender(@Param("oid")Integer oid);

    @Query(nativeQuery = true,value= "select oid,rateforsender,ostatus from orders where sid =:sid ;")
    List<Map<String,Object>> getThisSenderAllOrder(@Param("sid")Integer sid);

    @Query(nativeQuery = true,value= "\n" +
            "select o.oid ,p.pid,u.uid,s.sid,o.accept_time,o.update_time,o.end_time,(case when o.ostatus=0 then '已接受，未配送' when o.ostatus=1 then '已接受，正在配送' when o.ostatus=2 then '配送完成' when o.ostatus=-1 then  '投诉已解决'  when o.ostatus = -2 then '投诉申请中'  end)status,o.rateforsender,u.urealname,u.uphone,s.srealname,s.sphone from orders as o inner join sender as s on o.sid=s.sid inner join package as p on p.pid = o.pid inner join user as u on u.uid=p.uid  ;")
    List<Map<String,Object>> getAllOrder();

    @Query(nativeQuery = true,value= "\n" +
            "select o.oid ,p.pid,u.uid,s.sid,o.accept_time,o.update_time,o.end_time,(case when o.ostatus=0 then '已接受，未配送' when o.ostatus=1 then '已接受，正在配送' when o.ostatus=2 then '配送完成' when o.ostatus=-1 then  '投诉已解决'  when o.ostatus = -2 then '投诉申请中'  end)status,o.rateforsender,u.urealname,u.uphone,s.srealname,s.sphone from orders as o inner join sender as s on o.sid=s.sid inner join package as p on p.pid = o.pid inner join user as u on u.uid=p.uid where o.oid = :oid  ;")
    List<Map<String,Object>> searchOrderById(@Param("oid")Integer oid );
}
