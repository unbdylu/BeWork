package com.houlin.vhr.service;

import com.houlin.vhr.mapper.EmployeeMapper;
import com.houlin.vhr.model.Employee;
import com.houlin.vhr.model.RespPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @ClassName EmployeeService
 * @Description: TODO
 * @Author: @houlin Chen
 * @Date 2020/5/19
 * @Version V2.0
 **/
@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    DecimalFormat decimalFormat = new DecimalFormat("##.00");

    public RespPageBean getEmployeeByPage(Integer page, Integer size, Employee employee, Date[] beginDateScope) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<Employee> data = employeeMapper.getEmployeeByPage(page, size, employee, beginDateScope);
        Long total = employeeMapper.getTotal(employee, beginDateScope);
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }

    public Integer addEmps(Employee employee) {
        Date beginContract = employee.getBeginContract();
        Date endContract = employee.getEndContract();
        double month = (Double.parseDouble(yearFormat.format(endContract)) - Double.parseDouble(yearFormat.format(beginContract))) * 12 + (Double.parseDouble(monthFormat.format(endContract)) - Double.parseDouble(monthFormat.format(beginContract)));
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(month / 12)));
        return employeeMapper.insertSelective(employee);
    }

    public Integer getMaxId() {
        return employeeMapper.getMaxId();
    }

    public Integer deleteEmpByEid(Integer id) {
        return employeeMapper.deleteByPrimaryKey(id);
    }

    public Integer updateEmp(Employee employee) {
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }

    public RespPageBean getAllEmployeeByPageWithSalary(Integer page, Integer size) {
        if (page != null && size != null) {
            page=(page-1)*size;
        }
        List<Employee> list=employeeMapper.getAllEmployeeByPageWithSalary(page,size);
        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(list);
        respPageBean.setTotal(employeeMapper.getTotal(null,null));
        return respPageBean;
    }

    public Integer updateEmploySalaryById(Integer eid, Integer sid) {
        return employeeMapper.updateEmploySalaryById(eid,sid);
    }
}
