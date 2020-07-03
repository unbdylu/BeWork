package com.houlin.vhr.service;

import com.houlin.vhr.mapper.NationMapper;
import com.houlin.vhr.model.Nation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName NationSerive
 * @Description: TODO
 * @Author: @houlin Chen
 * @Date 2020/5/23
 * @Version V2.0
 **/
@Service
public class NationSerive {
    @Autowired
    NationMapper nationMapper;
    public List<Nation> getAllNations() {
            return nationMapper.getAllNations();
    }

 }
