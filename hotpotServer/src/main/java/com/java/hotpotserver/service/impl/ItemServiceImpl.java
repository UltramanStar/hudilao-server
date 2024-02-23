package com.java.hotpotserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.hotpotserver.entity.Food;
import com.java.hotpotserver.entity.Items;
import com.java.hotpotserver.mapper.FoodMapper;
import com.java.hotpotserver.mapper.ItemsMapper;
import com.java.hotpotserver.service.IFoodService;
import com.java.hotpotserver.service.IItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ItemServiceImpl extends ServiceImpl<ItemsMapper, Items> implements IItemsService{ //顾客的每一条点餐记录

   @Autowired
   ItemsMapper itemsMapper;
   @Autowired
   IFoodService iFoodService;

    @Override
    public int addItems(int orderid,int foodid, int quantity, int price) { //添加一条点餐记录
        String img=iFoodService.findImgById(foodid);
        String foodname=iFoodService.findFoodNameById(foodid);
        Items i=new Items();
        i.setImg(img);
        i.setFoodname(foodname);
        i.setQuantity(quantity);
        i.setPrice(price);
        i.setFoodid(foodid);
        i.setOrderid(orderid);
        int r=itemsMapper.insert(i);
        if(r>0)
            return 200; //获取成功
        else
            return 404; //获取失败
    }

    @Override
    public int deleteItems(int orderid) { //删除一条点餐记录
        QueryWrapper<Items> itemsQueryWrapper=new QueryWrapper<>();
        itemsQueryWrapper.eq("orderid",orderid);
        List<Items> itemList=itemsMapper.selectList(itemsQueryWrapper);
        for(Items i: itemList)
            iFoodService.delete(i.getFoodid(),i.getQuantity());
        int r=itemsMapper.delete(itemsQueryWrapper);
        if(r>0)
            return 200; //获取成功
        else
            return 404; //获取失败
    }

    @Override
    public int addFinishedOrderId(int finishedorderid,int orderid) { //将本点餐记录添加到历史订单
        QueryWrapper<Items> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("orderid",orderid);
        List<Items> list=itemsMapper.selectList(queryWrapper);
        for(Items i:list)
        {
            i.setFinishedorderid(finishedorderid);
            itemsMapper.updateById(i);
        }
        return 200; //获取成功
    }
}
