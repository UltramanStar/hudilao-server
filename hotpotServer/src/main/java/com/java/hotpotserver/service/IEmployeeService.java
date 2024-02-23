package com.java.hotpotserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.hotpotserver.entity.Employee;


import com.java.hotpotserver.form.AddEmployeeForm;
import com.java.hotpotserver.form.EditEmployeeForm;
import com.java.hotpotserver.vo.CookOrderList;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fengyu
 * @since 2023-08-29
 */
public interface IEmployeeService extends IService<Employee> { //员工接口
     public int employeeLogin(String number,String password,int type); //后厨登录
     public List<CookOrderList> orderList(String number); //筛选订单列表
     public List<Integer> findAllCookId(); //返回所有后厨id

     public List<Employee> listEmployee(); //员工列表

     public int addEmployee(AddEmployeeForm add); //新增员工

     public int editEmployee(EditEmployeeForm edit); //编辑员工

     public int deleteEmployee(int id); //删除员工

     public String findNumberById(int id); //通过id返回电话号码


}
