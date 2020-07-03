package com.houlin.vhr.controller.system;

import com.houlin.vhr.model.Hr;
import com.houlin.vhr.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName ChatController
 * @Description: TODO
 * @Author: @houlin Chen
 * @Date 2020/6/3
 * @Version V2.0
 **/
@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    HrService hrService;
    @GetMapping("/hrs")
    public List<Hr> getAllHrs(){
        return hrService.getAllHrsExceptCurrentId();
    }
}
