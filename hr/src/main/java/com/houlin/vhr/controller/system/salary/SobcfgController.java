package com.houlin.vhr.controller.system.salary;

import com.houlin.vhr.model.RespBean;
import com.houlin.vhr.model.RespPageBean;
import com.houlin.vhr.model.Salary;
import com.houlin.vhr.service.EmployeeService;
import com.houlin.vhr.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName SobcfgController
 * @Description: TODO
 * @Author: @houlin Chen
 * @Date 2020/5/31
 * @Version V2.0
 **/
@RestController
@RequestMapping("/salary/sobcfg")
public class SobcfgController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    SalaryService salaryService;

    @GetMapping("/")
    public RespPageBean getAllEmployeeByPageWithSalary(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return employeeService.getAllEmployeeByPageWithSalary(page, size);
    }

    @GetMapping("/salaries")
    public List<Salary> getAllSalaries() {
        return salaryService.getAllsalaries();
    }

    @PutMapping("/")
    public RespBean updateEmploySalaryById( Integer sid,
                                           Integer eid) {
        Integer res = employeeService.updateEmploySalaryById(eid, sid);
        if (res == 1 || res == 2) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败！");
    }
}
