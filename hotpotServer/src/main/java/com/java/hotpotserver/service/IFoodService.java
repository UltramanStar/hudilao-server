package com.java.hotpotserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.hotpotserver.entity.Food;
import com.java.hotpotserver.form.AddFoodForm;
import com.java.hotpotserver.form.EditFoodForm;
import com.java.hotpotserver.vo.ColdFood;
import com.java.hotpotserver.vo.HotFood;

import java.util.List;


public interface IFoodService extends IService<Food> { //菜品操作接口
    List<Food> foodList(); //菜品列表

    String findImgById(int foodid); //通过id查菜品图片

    String findFoodNameById(int foodid); //通过id找查菜名

    List<Food> adminFoodList(); //管理员端查看的菜品列表

    int setTop(int foodid,int type); //菜品置顶

    int addFood(AddFoodForm add); //添加菜品

    int editFood(EditFoodForm edit); //编辑菜品

    int setUse(int foodid,int type); //设为使用

    void insert(int foodid,int quantity); // 新增一定数量菜品

    void delete(int foodid,int quantity); //删除一定数量菜品

    List<HotFood> hotFood(); //热门菜品

    List<ColdFood> coldFood(); //库存紧张的菜品
}
