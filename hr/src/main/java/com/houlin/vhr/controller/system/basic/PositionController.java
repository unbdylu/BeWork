package com.houlin.vhr.controller.system.basic;

import com.houlin.vhr.model.Position;
import com.houlin.vhr.model.RespBean;
import com.houlin.vhr.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName PositionController
 * @Description: TODO
 * @Author: @houlin Chen
 * @Date 2020/5/11
 * @Version V2.0
 **/
@RestController
@RequestMapping("/system/basic/pos")
public class PositionController {

    @Autowired
    PositionService positionService;

//    查询
    @GetMapping("/")
    public List<Position> getAllPositions( ){
        return positionService.getAllPositions();
    }

//    增加
    @PostMapping("/")
    public RespBean addPosition(@RequestBody Position position){
     if (positionService.addPosition(position)==1){
         return RespBean.ok("添加成功");
     }return RespBean.error("添加失败！");
    }
    @DeleteMapping("/{id}")
    public RespBean deletePositionById(@PathVariable int id){
        if (positionService.deletePositionById(id)==1){
            return RespBean.ok("删除成功");
        }return RespBean.error("删除shibai");
    }
    @DeleteMapping("/")
    public RespBean deletePositionsByIds(Integer[] ids) {
        if (positionService.deletePositionsByIds(ids) == ids.length) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }
    @PutMapping("/")
    public RespBean updatePosition(@RequestBody Position position){
        if (positionService.updatePosition(position)==1){
            return RespBean.ok("更新成功");
        }
        return RespBean.error("genxin 失败");
    }
}
