package com.java.hotpotserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.hotpotserver.entity.Waitinglist;
import com.java.hotpotserver.entity.Wishlist;
import com.java.hotpotserver.form.WishlistForm;
import com.java.hotpotserver.mapper.WaitinglistMapper;
import com.java.hotpotserver.mapper.WishlistMapper;
import com.java.hotpotserver.service.IWaitinglistService;
import com.java.hotpotserver.service.IWishlistService;
import com.java.hotpotserver.vo.WishListNoPhone;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class WishlistServiceImpl extends ServiceImpl<WishlistMapper, Wishlist> implements IWishlistService { //心愿单接口
   @Autowired
    WishlistMapper wishlistMapper;
    @Override
    public int editWishlist(WishlistForm wishlistForm) { //编辑心愿单
        QueryWrapper<Wishlist> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("phonenumber",wishlistForm.getPhonenumber());
        Wishlist wishlist=wishlistMapper.selectOne(queryWrapper);
        BeanUtils.copyProperties(wishlistForm,wishlist);
        wishlist.setUpdatetime(LocalDate.now());
        int r=wishlistMapper.updateById(wishlist);
        if(r==0)
            return 404;//获取失败
        else
            return 200;//获取成功
    }

    @Override
    public WishListNoPhone wishListNoPhone(String phonenumber) { //取出顾客三条心愿，不保留手机号
        QueryWrapper<Wishlist> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("phonenumber",phonenumber);
        Wishlist wishlist=wishlistMapper.selectOne(queryWrapper);
        if(wishlist==null)
            return null;
        else
        {
            WishListNoPhone no=new WishListNoPhone();
            BeanUtils.copyProperties(wishlist,no);
            return no;
        }
    }
}

