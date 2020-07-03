package com.houlin.vhr.controller.system.basic;

import com.houlin.vhr.model.Menu;
import com.houlin.vhr.model.RespBean;
import com.houlin.vhr.model.Role;
import com.houlin.vhr.service.MenuService;
import com.houlin.vhr.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * @ClassName PermissController
 * @Description: TODO
 * @Author: @houlin Chen
 * @Date 2020/5/14
 * @Version V2.0
 **/
@RestController
@RequestMapping("/system/basic/permiss")
public class PermissController {
    @Autowired
    RoleService roleService;
    @Autowired
    MenuService menuService;
    @GetMapping("/")
    public List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }
    @GetMapping("/menus")
    public List<Menu> getAllMenus(){
        return menuService.getAllMenus();
    }
    @GetMapping("/mids/{rid}")
    public List<Integer> getMidsByRid(@PathVariable Integer rid){
        return menuService.getMidsByRid(rid);
    }
    @PutMapping("/")
    public RespBean updateMenuRole(Integer rid, Integer[] mids){
        if (menuService.updateMenuRole(rid,mids)){
            return RespBean.ok("更新成功！！");
        }
        return RespBean.error("更新失败！！");
    }
    @PostMapping("/role")
    public RespBean addRole(@RequestBody Role role){
        if (roleService.addRole(role)==1){
            return RespBean.ok("增加成功");
        }return RespBean.error("增加失败！");
    }
    @DeleteMapping("/role/{rid}")
    public RespBean deleteRoleById(@PathVariable Integer rid) {
        if (roleService.deleteRoleById(rid) == 1) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }}
