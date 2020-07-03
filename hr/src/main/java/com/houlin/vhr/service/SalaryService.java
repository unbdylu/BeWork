package com.houlin.vhr.service;

import com.houlin.vhr.mapper.SalaryMapper;
import com.houlin.vhr.model.Salary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.Date;
import java.util.List;

/**
 * @ClassName SalaryService
 * @Description: TODO
 * @Author: @houlin Chen
 * @Date 2020/5/30
 * @Version V2.0
 **/
@Service
public class SalaryService {
    @Autowired
    SalaryMapper salaryMapper;

    public List<Salary> getAllsalaries() {
        return salaryMapper.getAllsalaries();
    }

    public Integer addSalary(Salary salary) {
        salary.setCreateDate(new Date());
        return salaryMapper.insertSelective(salary);
    }

    public Integer deleteSalaryById(Integer id) {
        return salaryMapper.deleteByPrimaryKey(id);
    }

    public Integer upadteSalary(Salary salary) {
        return salaryMapper.updateByPrimaryKeySelective(salary);
    }
}
