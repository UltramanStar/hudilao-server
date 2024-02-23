package com.java.hotpotserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.hotpotserver.entity.Employee;

import com.java.hotpotserver.entity.Items;
import com.java.hotpotserver.entity.Orders;
import com.java.hotpotserver.form.AddEmployeeForm;
import com.java.hotpotserver.form.EditEmployeeForm;
import com.java.hotpotserver.mapper.EmployeeMapper;
import com.java.hotpotserver.mapper.ItemsMapper;
import com.java.hotpotserver.mapper.OrdersMapper;
import com.java.hotpotserver.service.IEmployeeService;
import com.java.hotpotserver.vo.CookItem;
import com.java.hotpotserver.vo.CookOrderList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fengyu
 * @since 2023-08-29
 */
@Service
@Slf4j
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService { //员工接口
     @Autowired
    private EmployeeMapper employeeMapper;
     @Autowired
     private OrdersMapper ordersMapper;
     @Autowired
     private ItemsMapper itemsMapper;

    @Override
    public int employeeLogin(String number, String password,int type) { //后厨登录
        // 根据number和password和type查询数据库中是否存在匹配的数据
            QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("number", number).eq("password", password).eq("type",type);
            Employee employee = employeeMapper.selectOne(queryWrapper); // 查询单条数据
            if (employee != null) {
                return 200; // 匹配成功，返回200
            } else {
                return 404; // 没有匹配的数据，返回404
            }
        }
    @Override
    public List<CookOrderList> orderList(String number) { //筛选订单列表
        QueryWrapper<Employee> queryWrapper1 = new QueryWrapper<>();
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper1.eq("number",number);
        Employee employee=employeeMapper.selectOne(queryWrapper1);
        log.info(employee.getId()+"");
        queryWrapper.eq("cookid",employee.getId()).eq("conditions",1);
        List<Orders> orderList= ordersMapper.selectList(queryWrapper);
        List cookOrderList=new ArrayList<CookOrderList>();
        for(Orders order: orderList)
        {
             CookOrderList c1=new CookOrderList();
             BeanUtils.copyProperties(order, c1);
             QueryWrapper<Items> queryWrapper3 = new QueryWrapper<>();
             queryWrapper3.eq("orderid",order.getOrderid());
             List<Items> items=itemsMapper.selectList(queryWrapper3);
             for(Items i:items) {
                 c1.getItems().add(new CookItem(i.getFoodname(), i.getQuantity()));
             }
             cookOrderList.add(c1);
        }
        return cookOrderList;
    }
    @Override
    public List<Integer> findAllCookId() { //返回所有后厨id
       QueryWrapper<Employee> queryWrapper=new QueryWrapper<>();
       queryWrapper.eq("type",2);
       List<Employee> employees=employeeMapper.selectList(queryWrapper);
       List<Integer> i=new ArrayList<>();
       for(Employee e:employees)
         i.add(e.getId());
        return i;
    }

    @Override
    public List<Employee> listEmployee() { //员工列表
        List<Employee> e=employeeMapper.selectList(null);
        return e;
    }

    @Override
    public int addEmployee(AddEmployeeForm add) { //新增员工
        Employee employee=new Employee();
        BeanUtils.copyProperties(add,employee);
        employee.setRegistertime(LocalDate.now());
        QueryWrapper<Employee> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("number",add.getNumber());
        if(employeeMapper.selectOne(queryWrapper)!=null)
            return 404; //获取失败
        else
        {
            employeeMapper.insert(employee);
            return 200; //获取成功
        }
    }

    @Override
    public int editEmployee(EditEmployeeForm edit) { //编辑员工
        Employee employee=new Employee();
        employee.setType(edit.getType());
        employee.setId(edit.getId());
        employee.setPassword(edit.getPassword());
        employee.setPhonenumber(edit.getPhonenumber());
        employeeMapper.updateById(employee);
        if(employee==null)
            return 404; //获取失败
        else
            return 200; //获取成功
    }
    @Override
    public int deleteEmployee(int id) { //删除员工
        int r=employeeMapper.deleteById(id);
        if(r==0)
            return 404; //获取失败
        else
            return 200; //获取成功
    }

    @Override
    public String findNumberById(int id) { //通过id返回电话号码
        QueryWrapper<Employee> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);
        Employee e=employeeMapper.selectOne(queryWrapper);
        return e.getNumber();
    }

}

