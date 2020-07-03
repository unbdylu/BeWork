package com.houlin.vhr.service;

import com.houlin.vhr.mapper.JobLevelMapper;
import com.houlin.vhr.model.JobLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName JobLevelService
 * @Description: TODO
 * @Author: @houlin Chen
 * @Date 2020/5/12
 * @Version V2.0
 **/
@Service
public class JobLevelService {
    @Autowired
    JobLevelMapper jobLevelMapper;

    public  int updateJobLevel(JobLevel jobLevel) {
        return jobLevelMapper.updateByPrimaryKeySelective(jobLevel);
    }

    public  int addJobLevel(JobLevel jobLevel) {
        jobLevel.setCreateDate(new Date());
        jobLevel.setEnabled(true);
        return jobLevelMapper.insertSelective(jobLevel);
    }

    public List<JobLevel> getAllJobLevels() {
            return jobLevelMapper.getAllJobLevels();
    }

    public int deleteJobLevel(int id) {
        return jobLevelMapper.deleteByPrimaryKey(id);
    }



    public Integer deleteJobLevelsByIds(Integer[] ids) {
        return jobLevelMapper.deleteJobLevelsByIds(ids);
    }

    public List<JobLevel> getAlljobLevels() {
        return jobLevelMapper.getAllJobLevels();
    }
}
