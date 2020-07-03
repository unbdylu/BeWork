package com.houlin.vhr.controller.system.basic;

import com.houlin.vhr.model.JobLevel;
import com.houlin.vhr.model.RespBean;
import com.houlin.vhr.service.JobLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName JobLevelController
 * @Description: TODO
 * @Author: @houlin Chen
 * @Date 2020/5/12
 * @Version V2.0
 **/
@RestController
@RequestMapping("/system/basic/joblevel")
public class JobLevelController {
    @Autowired
    JobLevelService jobLevelService;
    @GetMapping("/")
    public List<JobLevel> getAllJobLevels(){
        return jobLevelService.getAllJobLevels();
    }
    //增加
    @PostMapping("/")
    public RespBean addJobLevel(@RequestBody JobLevel jobLevel){
        if (jobLevelService.addJobLevel(jobLevel)==1){
        return RespBean.ok("增加成功！");
        }
        return RespBean.error("增加成功！");
    }
    //g更新
    @PutMapping("/")
    public RespBean updateJobLevel(@RequestBody JobLevel jobLevel){
        if (jobLevelService.updateJobLevel(jobLevel)==1){
            return RespBean.ok("更新成功！");
        }
            return RespBean.error("更新失败！");
    }
    @DeleteMapping("/{id}")
    public RespBean deleteJobLevel(@PathVariable int id){
        if (jobLevelService.deleteJobLevel(id)==1){
            return RespBean.ok("删除成功！");
        }return RespBean.error("删除失败！");
    }
    @DeleteMapping("/")
    public RespBean deleteJobLevelsByIds(Integer[] ids) {
        if (jobLevelService.deleteJobLevelsByIds(ids) == ids.length) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }
}
