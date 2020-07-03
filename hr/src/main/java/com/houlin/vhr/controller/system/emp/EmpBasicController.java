package com.houlin.vhr.controller.system.emp;

import com.houlin.vhr.model.*;
import com.houlin.vhr.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @ClassName EmpBasicController
 * @Description: TODO
 * @Author: @houlin Chen
 * @Date 2020/5/19
 * @Version V2.0
 **/
@RestController
@RequestMapping("/employee/basic")
public class EmpBasicController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    PoliticsstatusService politicsstatusService;
    @Autowired
    NationSerive nationSerive;
    @Autowired
    JobLevelService jobLevelService;
    @Autowired
    PositionService positionService;
    @Autowired
    DepartmentService departmentService;

    @GetMapping("/")
    public RespPageBean getEmployeeByPage(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer size,
                                          Employee employee,
                                          Date[] beginDateScope) {
        return employeeService.getEmployeeByPage(page, size, employee, beginDateScope);
    }

    @PostMapping("/")
    public RespBean addEmps(@RequestBody Employee employee) {
        if (employeeService.addEmps(employee) == 1) {
            return RespBean.ok("增加成功");
        }
        return RespBean.error("增加失败！");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteEmpByEid(@PathVariable Integer id) {
        if (employeeService.deleteEmpByEid(id) == 1) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    @PutMapping("/")
    public RespBean updateEmp(@RequestBody Employee employee) {
        if (employeeService.updateEmp(employee) == 1) {
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    @GetMapping("/nations")
    public List<Nation> getAllNations() {
        return nationSerive.getAllNations();
    }

    @GetMapping("/politicsstatus")
    public List<Politicsstatus> getAllPoliticsstatus() {
        return politicsstatusService.getAllPoliticsstatus();
    }

    @GetMapping("/joblevel")
    public List<JobLevel> getAlljobLevels() {
        return jobLevelService.getAlljobLevels();
    }

    @GetMapping("/positions")
    public List<Position> getAllPositions() {
        return positionService.getAllPositions();
    }

    @GetMapping("/maxid")
    public RespBean getMaxId() {
        RespBean respBean = RespBean.build().setObj(200)
                .setObj(String.format("%08d", employeeService.getMaxId() + 1));
        return respBean;
    }

    @GetMapping("/deps")
    public List<Department> getAllDeparments() {
        return departmentService.getAllDepartments();
    }

//    @GetMapping("export")
//    public ResponseEntity<byte[]> exportData(){
//        List<Employee> list=(List<Employee>) employeeService.getEmployeeByPage(null,null,null).getData();
//        return POIUtils.emploee2Exel(list);
//    }
}
