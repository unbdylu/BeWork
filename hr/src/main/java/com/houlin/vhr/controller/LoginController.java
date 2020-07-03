package com.houlin.vhr.controller;

import com.houlin.vhr.model.RespBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName LonginController
 * @Description: TODO
 * @Author: @houlin Chen
 * @Date 2020/5/3
 * @Version V2.0
 **/
@RestController
public class LoginController {
    @GetMapping("/login")
    public RespBean error(){
        return RespBean.error("尚未登陆，请登陆");
    }
}
