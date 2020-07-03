package com.houlin.vhr.controller.system.basic;

import com.houlin.vhr.model.Hr;
import com.houlin.vhr.model.RespBean;
import com.houlin.vhr.model.Role;
import com.houlin.vhr.service.HrService;
import com.houlin.vhr.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName HrController
 * @Description: TODO
 * @Author: @houlin Chen
 * @Date 2020/5/18
 * @Version V2.0
 **/
@RestController
@RequestMapping("/system/hr")
public class HrController {
    @Autowired
    HrService hrService;
    @Autowired
    RoleService roleService;

    @GetMapping("/")
    public List<Hr> getAllHrs(String keywords){
        return hrService.getAllHrs(keywords);
        }
        @PutMapping("/")
    public RespBean upDataHr(@RequestBody Hr hr){
        if (hrService.upDataHr(hr)==1){
            return RespBean.ok("更新成功！");
        }return RespBean.error("更新失败！");
    }
    @GetMapping("/roles")
    public List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }

    @PutMapping("/role")
    public RespBean updateHrRole(Integer hrid, Integer[] rids) {
        if (hrService.updateHrRole(hrid, rids)) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }
    @DeleteMapping("/{id}")
    public RespBean deleteByHrId(@PathVariable Integer id){
        if (hrService.deleteByHrId(id)==1){
            return RespBean.ok("删除成功！");
        }return RespBean.error("删除失败！");
    }
}
