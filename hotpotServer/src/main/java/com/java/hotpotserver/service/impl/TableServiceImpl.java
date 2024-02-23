package com.java.hotpotserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.hotpotserver.entity.Tables;
import com.java.hotpotserver.mapper.TablesMapper;
import com.java.hotpotserver.service.ITableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class TableServiceImpl extends ServiceImpl<TablesMapper, Tables> implements ITableService { //餐桌操作接口
   @Autowired
   TablesMapper tablesMapper;
    @Override
    public int addCustomer(String phonenumber) { //为每桌添加顾客
        QueryWrapper<Tables> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("vacancy",0);
        List<Tables> table= tablesMapper.selectList(queryWrapper);
        if(table==null)
            return 404;  //获取失败
        Random random = new Random();
        // 生成一个随机索引
        int randomIndex = random.nextInt(table.size());
        // 使用随机索引获取随机元素
        Tables randomTable = table.get(randomIndex);
        randomTable.setPhonenumber(phonenumber);
        randomTable.setVacancy(1);
        int r=tablesMapper.updateById(randomTable);
        if(r>0)
            return randomTable.getTableid();
        else
            return 404;  //获取失败
    }
    @Override
    public int useTable(int tableid) { //使用该桌
        QueryWrapper<Tables> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("tableid",tableid);
        Tables table= tablesMapper.selectOne(queryWrapper);
        table.setVacancy(1);
        int r=tablesMapper.updateById(table);
        if(r>0)
            return 200;//获取成功
        else
            return 404;  //获取失败
    }
    @Override
    public int clearTable(int tableid) { //清空该桌
        QueryWrapper<Tables> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("tableid",tableid);
        Tables table= tablesMapper.selectOne(queryWrapper);
        table.setVacancy(0);
        table.setPhonenumber(null);
        int r=tablesMapper.updateById(table);
        if(r>0)
            return 200;//获取成功
        else
            return 404;  //获取失败
    }
    @Override
    public String findPhonenumberByTableId(int tableid) { //通过桌号查询顾客手机号
        QueryWrapper<Tables> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("tableid",tableid);
        Tables t=tablesMapper.selectOne(queryWrapper);
        if(t==null)
            return null;
        else
            return t.getPhonenumber();
    }
}
