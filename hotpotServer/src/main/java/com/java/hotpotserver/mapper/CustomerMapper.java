package com.java.hotpotserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.hotpotserver.entity.Customer;
import com.java.hotpotserver.vo.CustomerRank;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CustomerMapper extends BaseMapper<Customer> { //顾客功能的mapper

    @Select("SELECT\n" +
            "    ranks,\n" +
            "    COUNT(*) AS count_per_rank\n" +
            "FROM\n" +
            "    customer\n" +
            "WHERE\n" +
            "    DATEDIFF(CURDATE(), registerdate) <= 30\n" +
            "GROUP BY\n" +
            "    ranks\n" +
            "ORDER BY\n" +
            "    ranks ASC;\n")
    List<CustomerRank> findThisMonth();

    @Select("SELECT\n" +
            "    ranks,\n" +
            "    COUNT(*) AS count_per_rank\n" +
            "FROM\n" +
            "    customer\n" +
            "WHERE\n" +
            "    DATEDIFF(CURDATE(), registerdate) BETWEEN 31 AND 60\n" +
            "GROUP BY\n" +
            "    ranks\n" +
            "ORDER BY\n" +
            "    ranks ASC;")
    List<CustomerRank> findLastMonth();


    @Select("SELECT\n" +
            "    ranks,\n" +
            "    COUNT(*) AS count_per_rank\n" +
            "FROM\n" +
            "    customer\n" +
            "GROUP BY\n" +
            "    ranks\n" +
            "ORDER BY\n" +
            "    ranks ASC;")
    List<CustomerRank> totalRank();

}
