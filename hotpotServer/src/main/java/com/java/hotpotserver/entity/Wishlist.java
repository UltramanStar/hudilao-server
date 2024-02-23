package com.java.hotpotserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author fengyu
 * @since 2023-08-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wishlist")
public class Wishlist implements Serializable {//心愿单表

    private static final long serialVersionUID = 1L;
    //id
    @TableId(type = IdType.AUTO)
    private Integer wishlistid;
    //电话
    private String phonenumber;
    //心愿单菜品1
    private String food1;
    //心愿单菜品2
    private String food2;
    //心愿单菜品3
    private String food3;
    //更新时间
    private LocalDate updatetime;

}
