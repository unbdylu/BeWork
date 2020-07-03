package com.houlin.vhr.service;

import com.houlin.vhr.mapper.PoliticsstatusMapper;
import com.houlin.vhr.model.Politicsstatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName PoliticsstatusService
 * @Description: TODO
 * @Author: @houlin Chen
 * @Date 2020/5/23
 * @Version V2.0
 **/
@Service
public class PoliticsstatusService {
    @Autowired
    PoliticsstatusMapper politicsstatusMapper;
    public List<Politicsstatus> getAllPoliticsstatus() {
        return politicsstatusMapper.getAllPoliticsstatus();
    }
}
