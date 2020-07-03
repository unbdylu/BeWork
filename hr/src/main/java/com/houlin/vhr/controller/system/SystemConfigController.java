package com.houlin.vhr.controller.system;

import com.houlin.vhr.model.Menu;
import com.houlin.vhr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName SystemConfigController
 * @Description: TODO
 * @Author: @houlin Chen
 * @Date 2020/5/4
 * @Version V2.0
 **/
@RestController
@RequestMapping("/system/config")
public class SystemConfigController {
@Autowired
MenuService menuService;
    @GetMapping("/menu")
    public List<Menu> getMenusByHrId() {
        return menuService.getMenusByHrId();
    }
}
