package com.houlin.vhr.controller.system.cfg;

import com.houlin.vhr.model.Position;
import com.houlin.vhr.model.RespBean;
import com.houlin.vhr.service.PosTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName PosTestController
 * @Description: TODO
 * @Author: @houlin Chen
 * @Date 2020/6/19
 * @Version V2.0
 **/
@RestController
@RequestMapping("/system/cfg")
public class PosTestController {
    @Autowired
    PosTestService posTestService;

    @GetMapping("/positions")
    public List<Position> testAllPos() {
        return posTestService.testAllPos();
    }

    @PostMapping("/position")
    public RespBean testAddPos(@RequestBody Position position) {
        if (posTestService.testAddPos(position) == 1) {
            return RespBean.ok("增加ok");
        }
        return RespBean.error("增加error");
    }

    @PutMapping("/position")
    public RespBean testUpdatePos(@RequestBody Position position) {
        if (posTestService.testUpdatePos(position) == 1) {
            return RespBean.ok("update.ok");
        }
        return RespBean.error("update.error");
    }

    @DeleteMapping("/{id}")
    public RespBean testDeletePos(@PathVariable Integer id) {
        if (posTestService.testDeletePos(id) == 1){
            return RespBean.ok("delete.ok");
    }
        return RespBean.error("delete.error");}
         @DeleteMapping("/")
        public RespBean testDeleteArray(Integer[] ids){
        if (posTestService.testDeleteArray(ids)==ids.length){
            return RespBean.ok("delete.ok");
        }
            return RespBean.error("delete /error");
         }
}