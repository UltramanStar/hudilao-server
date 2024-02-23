package com.java.hotpotserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.hotpotserver.entity.Finishedorder;
import com.java.hotpotserver.vo.MonthSale;
import com.java.hotpotserver.vo.Triple;
import com.java.hotpotserver.vo.WeekSale;
import com.java.hotpotserver.vo.YearSale;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FinishedorderMapper extends BaseMapper<Finishedorder> { //历史订单功能的mapper
    @Select("SELECT\n" +
            "    DAYNAME(time) AS day_of_week,\n" +
            "    SUM(money) AS total_money\n" +
            "FROM\n" +
            "    finishedorder\n" +
            "WHERE\n" +
            "    time BETWEEN DATE_SUB(CURDATE() -INTERVAL 1 DAY, INTERVAL 1 WEEK) AND CURDATE()\n" +
            "GROUP BY\n" +
            "    day_of_week\n" +
            "ORDER BY\n" +
            "    FIELD(day_of_week, 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday');\n")
     List<WeekSale>  weekSale();
    @Select("SELECT\n" +
            "    YEARWEEK(time) AS week_number,\n" +
            "    SUM(money) AS total_money\n" +
            "FROM\n" +
            "    finishedorder\n" +
            "WHERE\n" +
            "    time BETWEEN DATE_SUB(CURDATE(), INTERVAL 4 WEEK) AND CURDATE()-INTERVAL 1 DAY\n" +
            "GROUP BY\n" +
            "    week_number\n" +
            "ORDER BY\n" +
            "    week_number;")
    List<MonthSale>  monthSale();

    @Select("SELECT\n" +
            "    MONTH(time) AS month_number,\n" +
            "    SUM(money)*2 AS total_money\n" +
            "FROM\n" +
            "    finishedorder\n" +
            "WHERE\n" +
            "    YEAR(time) = YEAR(CURDATE())  -- 这会筛选出今年的数据\n" +
            "GROUP BY\n" +
            "    month_number\n" +
            "ORDER BY\n" +
            "    month_number;\n")
    List<YearSale> yearSale();

    @Select("SELECT\n" +
            "    DATE(time) AS order_date,\n" +
            "    HOUR(time) AS order_hour,\n" +
            "    COUNT(*) AS order_count,\n" +
            "    MAX(DAYOFWEEK(time) - 1) AS day_of_week\n" +
            "FROM\n" +
            "    finishedorder\n" +
            "WHERE\n" +
            "    time BETWEEN DATE_SUB(CURDATE(), INTERVAL 1 WEEK) AND CURDATE() + INTERVAL 1 DAY\n" +
            "GROUP BY\n" +
            "    order_date, order_hour\n" +
            "ORDER BY\n" +
            "    order_date, order_hour;")
      List<Triple> hotMap();
    @Select("SELECT DISTINCT c.phonenumber, c.ranks\n" +
            "FROM customer c\n" +
            "LEFT JOIN finishedorder fo ON c.phonenumber = fo.phonenumber\n" +
            "WHERE fo.time >= DATE_FORMAT(CURRENT_DATE - INTERVAL 1 MONTH, '%Y-%m-01')\n" +
            "  AND fo.time < DATE_FORMAT(CURRENT_DATE, '%Y-%m-01');\n")
    List<String> getColdCustomer();
}
