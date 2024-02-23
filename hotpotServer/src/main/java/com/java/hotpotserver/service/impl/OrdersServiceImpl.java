package com.java.hotpotserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.hotpotserver.config.WebSocketServer;
import com.java.hotpotserver.entity.Customer;
import com.java.hotpotserver.entity.Items;
import com.java.hotpotserver.entity.Orders;
import com.java.hotpotserver.mapper.CustomerMapper;
import com.java.hotpotserver.mapper.ItemsMapper;
import com.java.hotpotserver.mapper.OrdersMapper;
import com.java.hotpotserver.service.*;
import com.java.hotpotserver.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService { //订单接口
    @Autowired
    OrdersMapper ordersMapper;
    @Autowired
    ItemsMapper itemsMapper;
    @Autowired
    ITableService iTableService;
    @Autowired
    IEmployeeService iEmployeeService;

    @Autowired
    IItemsService iItemsService;

    @Autowired
    IFinishedorderService iFinishedorderService;

    @Autowired
    CustomerMapper customerMapper;


    @Override
    public int orderReady(int orderid) { //订单制作好
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("orderid",orderid).eq("conditions",1);
        Orders order=ordersMapper.selectOne(queryWrapper);
        if(order==null) {
            return 404; //获取失败
        }
        else
        {
            order.setConditions(2);
            ordersMapper.updateById(order);
            return 200; //获取成功
        }
    }
    @Override
    public List<CurrentorderList> currentOrderList(String phonenumber) { //顾客的当前订单

        QueryWrapper<Orders> orderQueryWrapper=new QueryWrapper<>();
        orderQueryWrapper.eq("phonenumber",phonenumber).ne("conditions",4);
        List<Orders> currentOrders=ordersMapper.selectList(orderQueryWrapper);
        List currentOrderLists=new ArrayList<CurrentorderList>();

        for(Orders myorder: currentOrders)
        {
            int foodnumber=0;
            CurrentorderList c1=new CurrentorderList();
            BeanUtils.copyProperties(myorder, c1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = myorder.getTime().format(formatter);
            c1.setTime(formattedDateTime);
            QueryWrapper<Items> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("orderid",myorder.getOrderid());
            List<Items> items=itemsMapper.selectList(queryWrapper3);
            for(Items i:items) {
                foodnumber+=i.getQuantity();
                c1.getItems().add(new CustomerItem(i.getFoodname(), i.getQuantity(),i.getPrice(),i.getImg().replaceAll("[\r\n]", "")));
            }
            c1.setFoodnumber(foodnumber);
            currentOrderLists.add(c1);
        }
        return currentOrderLists;
    }

    @Override
    public int orderPaid(int tableid) {//根据桌号，支付订单
        QueryWrapper<Orders> orderQueryWrapper=new QueryWrapper<>();
        orderQueryWrapper.eq("tableid",tableid).ne("conditions",4);
        List<Orders> currentOrders=ordersMapper.selectList(orderQueryWrapper);
        int sum=0;
        for(Orders myorder: currentOrders)
        {
            Orders o=new Orders();
            o.setPaid(1);
            o.setOrderid(myorder.getOrderid());
            ordersMapper.updateById(o);
            sum+= myorder.getMoney();
        }
        if(currentOrders!=null) {
            QueryWrapper<Customer> customerQueryWrapper = new QueryWrapper<>();
            customerQueryWrapper.eq("phonenumber", currentOrders.get(0).getPhonenumber());
            Customer c = customerMapper.selectOne(customerQueryWrapper);
            if (c != null) {
                if (sum > 100) {
                    if (c.getRanks() < 5) {
                        int temp = c.getRanks();
                        temp++;
                        c.setRanks(temp);
                        customerMapper.updateById(c);
                    }
                } else if (sum > 50) {
                    if (c.getRanks() < 5) {
                        boolean a = iFinishedorderService.findAnother(LocalDate.now(), currentOrders.get(0).getPhonenumber());
                        if (a == true) {
                            int temp = c.getRanks();
                            temp++;
                            c.setRanks(temp);
                            customerMapper.updateById(c);
                        }
                    }
                }
            }
        }
        if(currentOrders==null)
            return 404; //获取失败
        else
            return 200; //获取成功
    }
    @Override
    public IPage<Orders> allPagedCurrentOrderList(int pagenumber, int pagesize) { //服务员（分页）
        Page<Orders> page = new Page<Orders>(pagenumber, pagesize);
        QueryWrapper<Orders> ordersQueryWrapper=new QueryWrapper<>();
        ordersQueryWrapper.ne("conditions",4);
        IPage<Orders> pageResult =ordersMapper.selectPage(page,ordersQueryWrapper);
        List<Orders> currentorderList=pageResult.getRecords();
        List OrderList=new ArrayList<CurrentorderList>();
        for(Orders order: currentorderList)
        {
           CurrentorderList c1=new CurrentorderList();
           BeanUtils.copyProperties(order, c1);
            QueryWrapper<Items> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("orderid",order.getOrderid());
            List<Items> items=itemsMapper.selectList(queryWrapper3);
            for(Items i:items) {
                c1.getItems().add(new CustomerItem(i.getFoodname(), i.getQuantity(),i.getPrice(),i.getImg(). replaceAll("[\r\n]", "")));
            }
            OrderList.add(c1);
        }
        pageResult.setRecords(OrderList);
        return pageResult;
    }

    @Override
    public  List<CurrentorderList> allCurrentOrderList() { //服务员（不分页）
        QueryWrapper<Orders> ordersQueryWrapper=new QueryWrapper<>();
        ordersQueryWrapper.ne("conditions",4);
        List<Orders> orders=ordersMapper.selectList(ordersQueryWrapper);
        if(orders==null)
            return null;
            else
        {
            List<CurrentorderList> list=new ArrayList<>();
            for(Orders order: orders) {
                CurrentorderList c1 = new CurrentorderList();
                BeanUtils.copyProperties(order, c1);
                QueryWrapper<Items> queryWrapper3 = new QueryWrapper<>();
                queryWrapper3.eq("orderid", order.getOrderid());
                List<Items> items = itemsMapper.selectList(queryWrapper3);
                for (Items i : items) {
                    c1.getItems().add(new CustomerItem(i.getFoodname(), i.getQuantity(), i.getPrice(), i.getImg().replaceAll("[\r\n]", "")));
                }
                list.add(c1);
            }
            return list;
        }
    }

    @Override
    public int changeOrderCondition(int orderid, int condition) { //改变订单状态
       if(condition==3)
       {
          QueryWrapper<Orders> queryWrapper=new QueryWrapper<>();
          queryWrapper.eq("orderid",orderid);
          Orders order=ordersMapper.selectOne(queryWrapper);
          if(order==null)
              return 404;//获取失败
          Orders newOrder=new Orders();
          newOrder.setOrderid(order.getOrderid());
          newOrder.setConditions(3);
          ordersMapper.updateById(newOrder);
       }
       if(condition==4)
       {
           QueryWrapper<Orders> queryWrapper=new QueryWrapper<>();
           queryWrapper.eq("orderid",orderid);
           Orders order=ordersMapper.selectOne(queryWrapper);
           QueryWrapper<Orders> queryWrapper1=new QueryWrapper<>();
           queryWrapper1.eq("tableid",order.getTableid());
           List<Orders> allOrders=ordersMapper.selectList(queryWrapper1);
           if(allOrders==null)
               return 404;//获取失败
           int sum=0;
           for(Orders o:allOrders)
               sum+=o.getMoney();
           int finishId=iFinishedorderService.addFinishedorder(order.getTableid(),order.getPhonenumber(),sum);
           for(Orders o:allOrders)
           {
               Orders newOrder=new Orders();
               newOrder.setOrderid(o.getOrderid());
               newOrder.setConditions(4);
               ordersMapper.updateById(newOrder);
               iItemsService.addFinishedOrderId(finishId,o.getOrderid());
           }
       }
       return 200; //获取成功
   }

    @Override
    public int urgeOrder(int orderid) { //紧急订单
        Orders o=new Orders();
        o.setOrderid(orderid);
        o.setEmergency(1);
        int r=ordersMapper.updateById(o);
        Orders order = ordersMapper.selectById(orderid);
        String cookNumber=iEmployeeService.findNumberById(order.getCookid());
        ObjectMapper objectMapper = new ObjectMapper();
        WebSocketResponse webSocketR=new WebSocketResponse(1,cookNumber,"订单"+order.getOrderid()+"切换为紧急订单");
       try {
           String jsonString = objectMapper.writeValueAsString(webSocketR);
           WebSocketServer.sendInfo(jsonString);
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
        return 200; //获取成功
    }
    @Override
    public CookAndOrderId addOrder(int tableid, int totalprice) {//添加菜单
        Orders o=new Orders();
        String phonenumber=iTableService.findPhonenumberByTableId(tableid);
        o.setPhonenumber(phonenumber);
        o.setMoney(totalprice);
        o.setTime(LocalDateTime.now());
        o.setConditions(1);
        o.setEmergency(0);
        o.setPaid(0);
        o.setTableid(tableid);
        List<Integer> list=iEmployeeService.findAllCookId();
        o.setCookid(findSuitableCook(list));
        ordersMapper.insert(o);
        return new CookAndOrderId(o.getOrderid(),iEmployeeService.findNumberById(o.getCookid()));
     }

    @Override
    public Integer findSuitableCook(List<Integer> list) { //分配合适的后厨
       if(list.size()==0)
           return null;
       else
       {
           int suitableCookId=list.get(0);
           int givenOrder=Integer.MAX_VALUE;
           for(Integer i:list)
           {
               QueryWrapper<Orders> ordersQueryWrapper=new QueryWrapper<>();
               ordersQueryWrapper.eq("cookid",i);
               int r=ordersMapper.selectCount(ordersQueryWrapper);
               if(r<givenOrder) {
                   suitableCookId = i;
                   givenOrder=r;
               }

           }
           return suitableCookId;
       }
    }

    @Override
    public int editOrderPrice(int orderid, int price) { //编辑订单价格
        QueryWrapper<Orders> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("orderid",orderid);
        Orders order=ordersMapper.selectOne(queryWrapper);
        if(order==null)
            return 404;//获取失败
        order.setMoney(price);
        ordersMapper.updateById(order);
        return 200; //获取成功

    }

    @Override
    public IPage<Orders> pagedLittleOrder(int pagenum, int pagesize) { //服务员小订单
        Page<Orders> page = new Page<Orders>(pagenum, pagesize);
        QueryWrapper<Orders> ordersQueryWrapper=new QueryWrapper<>();
        ordersQueryWrapper.ne("conditions",4);
        ordersQueryWrapper.orderByDesc("time");
        IPage<Orders> pageResult =ordersMapper.selectPage(page,ordersQueryWrapper);
        List<Orders> currentorderList=pageResult.getRecords();
        List littleOrderLists=new ArrayList<LittleOrder>();
        for(Orders myorder: currentorderList)
        {
            int foodnumber=0;
            LittleOrder c1=new LittleOrder();
            BeanUtils.copyProperties(myorder, c1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedDateTime = myorder.getTime().format(formatter);
            c1.setTime(formattedDateTime);
            QueryWrapper<Items> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("orderid",myorder.getOrderid());
            List<Items> items=itemsMapper.selectList(queryWrapper3);
            int count = 0; // 用于计数
            for (Items i : items) {
                foodnumber+=i.getQuantity();
                if (count < 3) { // 只在计数小于3时执行操作
                    {
                        if (count == 0) {
                            c1.setFood1(i.getFoodname());
                            c1.setQuantity1(i.getQuantity());
                            c1.setImg1(i.getImg(). replaceAll("[\r\n]", ""));
                        }
                        if (count == 1) {
                            c1.setFood2(i.getFoodname());
                            c1.setQuantity2(i.getQuantity());
                            c1.setImg2(i.getImg().replaceAll("[\r\n]", ""));
                        }
                        if (count == 2) {
                            c1.setFood3(i.getFoodname());
                            c1.setQuantity3(i.getQuantity());
                            c1.setImg3(i.getImg().replaceAll("[\r\n]", ""));
                        }
                    }
                    count++; // 增加计数
                } else {
                    break; // 如果计数达到3，退出循环
                }
            }
            c1.setFoodnumber(foodnumber);
            littleOrderLists.add(c1);
        }
        pageResult.setRecords(littleOrderLists);
        return pageResult;
    }
    @Override
    public WaiterOrderDetail detailOrderByWaiter(int orderid) { //服务员端订单详情
        QueryWrapper<Orders> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("orderid",orderid);
        Orders order=ordersMapper.selectOne(queryWrapper);
        WaiterOrderDetail waiterOrderDetail=new WaiterOrderDetail();
        BeanUtils.copyProperties(order, waiterOrderDetail);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedDateTime = order.getTime().format(formatter);
        waiterOrderDetail.setTime(formattedDateTime);
        QueryWrapper<Items> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("orderid", order.getOrderid());
        List<Items> items = itemsMapper.selectList(queryWrapper3);
        int foodnumber=0;
        for (Items i : items) {
            foodnumber+=i.getQuantity();
            waiterOrderDetail.getItems().add(new WaiterCustomerItem(i.getFoodname(), i.getQuantity(), i.getPrice(), i.getImg().replaceAll("[\r\n]", ""),i.getFoodid()));
        }
        waiterOrderDetail.setFoodnumber(foodnumber);
        return waiterOrderDetail;
    }
    @Override
    public int findCookIdByOrderId(int orderid) { //通过订单号查询后厨id
        QueryWrapper<Orders> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("orderid",orderid);
        Orders order=ordersMapper.selectOne(queryWrapper);
        return order.getCookid();
    }
}
