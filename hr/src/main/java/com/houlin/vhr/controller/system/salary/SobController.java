package com.houlin.vhr.controller.system.salary;

import com.houlin.vhr.model.RespBean;
import com.houlin.vhr.model.Salary;
import com.houlin.vhr.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName salary
 * @Description: TODO
 * @Author: @houlin Chen
 * @Date 2020/5/30
 * @Version V2.0
 **/
@RestController
@RequestMapping("/salary/sob")
public class SobController {
    @Autowired
    SalaryService salaryService;

    @GetMapping("/")
    public List<Salary> getAllsalaries() {
        return salaryService.getAllsalaries();
    }

    @PostMapping("/")
    public RespBean addSalary(@RequestBody Salary salary) {
        if (salaryService.addSalary(salary) == 1) {
            return RespBean.ok("增加成功！");
        }
        return RespBean.error("增加失败！");
    }
    @DeleteMapping("/{id}")
    public RespBean deleteSalaryById(@PathVariable Integer id){
        if (salaryService.deleteSalaryById(id)==1){
            return RespBean.ok("删除成功!");
        }
            return RespBean.error("删除失败!");
    }
    @PutMapping("/")
    public RespBean upadteSalary(@RequestBody Salary salary){
        if (salaryService.upadteSalary(salary)==1){
            return RespBean.ok("更新成功！");
        }
            return RespBean.error("更新失败！");
    }
}
