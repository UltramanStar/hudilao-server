package com.java.hotpotserver.vo;

import lombok.Data;
import java.util.List;
@Data
public class NewCustomer {//管理员请求的本月、上月新注册会员和总会员人数（三个表对应）
    List<CustomerRank> list1;
    List<CustomerRank> list2;
    List<CustomerRank> list3;
}
