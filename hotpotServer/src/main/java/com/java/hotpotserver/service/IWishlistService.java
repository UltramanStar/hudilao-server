package com.java.hotpotserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.hotpotserver.entity.Wishlist;
import com.java.hotpotserver.form.WishlistForm;
import com.java.hotpotserver.vo.WishListNoPhone;

public interface IWishlistService extends IService<Wishlist> { //心愿单接口
    int editWishlist(WishlistForm wishlistForm); //编辑心愿单

    WishListNoPhone wishListNoPhone(String phonenumber); //取出顾客三条心愿，不保留手机号
}
