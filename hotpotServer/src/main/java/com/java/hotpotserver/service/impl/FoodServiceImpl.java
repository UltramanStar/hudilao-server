package com.java.hotpotserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.hotpotserver.entity.Food;
import com.java.hotpotserver.mapper.FoodMapper;
import com.java.hotpotserver.service.IFoodService;
import com.java.hotpotserver.form.AddFoodForm;
import com.java.hotpotserver.form.EditFoodForm;
import com.java.hotpotserver.vo.ColdFood;
import com.java.hotpotserver.vo.HotFood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodServiceImpl  extends ServiceImpl<FoodMapper, Food> implements IFoodService { //菜品操作接口
     @Autowired
     FoodMapper foodMapper;

    @Override
    public List<Food> foodList() { //菜品列表
        List<Food> f=foodMapper.getAllDataOrderedByType();
       for(Food f1: f)
       {
           String originalString = f1.getImg();
           String cleanedString = originalString.replaceAll("[\r\n]", "");
           f1.setImg(cleanedString);
       }
       return f;
    }

    @Override
    public String findImgById(int foodid) { //通过id查菜品图片
//        QueryWrapper<Food> foodQueryWrapper=new QueryWrapper<>();
//        foodQueryWrapper.eq("foodid",foodid);
        Food f=foodMapper.selectById(foodid);
        return f.getImg();

    }

    @Override
    public String findFoodNameById(int foodid) { //通过id找查菜名
//        QueryWrapper<Food> foodQueryWrapper=new QueryWrapper<>();
//        foodQueryWrapper.eq("foodid",foodid);
        Food f=foodMapper.selectById(foodid);
        return f.getName();
    }

    @Override
    public List<Food> adminFoodList() { //管理员端查看的菜品列表
        List<Food> f=foodMapper.adminGetFood();
        return f;
    }

    @Override
    public int setTop(int foodid, int type) { //菜品置顶
        Food food=new Food();
        food.setFoodid(foodid);
        food.setTop(type);
        foodMapper.updateById(food);
        return 200; //获取成功
    }

    @Override
    public int addFood(AddFoodForm add) { //添加菜品
        Food food=new Food();
        food.setImg(add.getImg());
        food.setTop(0);
        food.setInuse(0);
        food.setFoodtype(add.getFoodtype());
        food.setPrice(add.getPrice());
        food.setMonthsale(0);
        food.setRepository(add.getRepository());
        food.setName(add.getName());
        int r=foodMapper.insert(food);
        if(r>0)
            return 200; //获取成功
        else
            return 404; //获取失败
    }
    @Override
    public int editFood(EditFoodForm edit) { //编辑菜品
        Food food=new Food();
        food.setPrice(edit.getPrice());
        food.setRepository(edit.getRepository());
        food.setFoodid(edit.getFoodid());
        int r=foodMapper.updateById(food);
        if(r>0)
            return 200; //获取成功
        else
            return 404; //获取失败
    }

    @Override
    public int setUse(int foodid, int type) { //设为使用
        Food food=new Food();
        food.setFoodid(foodid);
        food.setInuse(type);
        foodMapper.updateById(food);
        return 200; //获取成功
    }
    @Override
    public void insert(int foodid, int quantity) { // 新增一定数量菜品
        Food f=foodMapper.selectById(foodid);
        int s1=f.getMonthsale();
        s1+=quantity;
        int s2=f.getRepository();
        s2-=quantity;
        f.setMonthsale(s1);
        f.setRepository(s2);
        foodMapper.updateById(f);
    }

    @Override
    public void delete(int foodid, int quantity) { //删除一定数量菜品
        Food f=foodMapper.selectById(foodid);
        int s1=f.getMonthsale();
        s1-=quantity;
        int s2=f.getRepository();
        s2+=quantity;
        f.setMonthsale(s1);
        f.setRepository(s2);
        foodMapper.updateById(f);
    }

    @Override
    public List<HotFood> hotFood() {
        return foodMapper.findHotFood();
    } //热门菜品

    @Override
    public List<ColdFood> coldFood() {
       return foodMapper.findColdFood();
    } //库存紧张的菜品

}
