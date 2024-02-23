package com.java.hotpotserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.hotpotserver.entity.Wishlist;
import com.java.hotpotserver.vo.FoodRanking;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WishlistMapper extends BaseMapper<Wishlist> { //心愿单功能的mapper

    @Select("SELECT food, SUM(ranks) AS total_ranked_score\n" +
            "FROM (\n" +
            "    SELECT c.ranks, food\n" +
            "    FROM (\n" +
            "        SELECT phonenumber, food1 AS food, updatetime FROM wishlist WHERE food1 IS NOT NULL\n" +
            "        UNION ALL\n" +
            "        SELECT phonenumber, food2 AS food, updatetime FROM wishlist WHERE food2 IS NOT NULL\n" +
            "        UNION ALL\n" +
            "        SELECT phonenumber, food3 AS food, updatetime FROM wishlist WHERE food3 IS NOT NULL\n" +
            "    ) AS combined_food\n" +
            "    JOIN customer c ON combined_food.phonenumber = c.phonenumber\n" +
            "    WHERE updatetime >= DATE_SUB(NOW(), INTERVAL 3 MONTH)\n" +
            ") AS combined_data\n" +
            "GROUP BY food\n" +
            "ORDER BY total_ranked_score DESC\n" +
            "LIMIT 5;")
    List<FoodRanking> findTop5FoodRankings();
}
