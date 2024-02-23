package com.java.hotpotserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.hotpotserver.entity.Food;
import com.java.hotpotserver.vo.ColdFood;
import com.java.hotpotserver.vo.HotFood;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FoodMapper extends BaseMapper<Food> { //菜品操作的mapper
    @Select("SELECT * FROM food WHERE inuse <> 1 ORDER BY foodtype ASC, top DESC") //待测试
    List<Food> getAllDataOrderedByType();
    @Select("SELECT * FROM food ORDER BY foodtype ASC, top DESC")
    List<Food> adminGetFood();
    @Select("SELECT\n" +
            "    foodtype,\n" +
            "    name,\n" +
            "    monthsale\n" +
            "FROM (\n" +
            "    SELECT\n" +
            "        foodtype,\n" +
            "        name,\n" +
            "        monthsale,\n" +
            "\t\t\t\tfoodid,\n" +
            "        DENSE_RANK() OVER(PARTITION BY foodtype ORDER BY monthsale DESC, foodid ASC) AS rank_within_type\n" +
            "    FROM\n" +
            "        food\n" +
            ") ranked\n" +
            "WHERE\n" +
            "    rank_within_type <= 5;")
    List<HotFood> findHotFood();


    @Select("SELECT\n" +
            "    foodtype,\n" +
            "    name,\n" +
            "    repository\n" +
            "FROM (\n" +
            "    SELECT\n" +
            "        foodtype,\n" +
            "        name,\n" +
            "        repository,\n" +
            "\t\t\t\tfoodid,\n" +
            "        DENSE_RANK() OVER(PARTITION BY foodtype ORDER BY repository ASC, foodid ASC) AS rank_within_type\n" +
            "    FROM\n" +
            "        food\n" +
            ") ranked\n" +
            "WHERE\n" +
            "    rank_within_type <= 5 and repository<100")
    List<ColdFood> findColdFood();



}
