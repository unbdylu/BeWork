package com.houlin.vhr.service;

import com.houlin.vhr.mapper.PositionMapper;
import com.houlin.vhr.model.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName PosTestService
 * @Description: TODO
 * @Author: @houlin Chen
 * @Date 2020/6/19
 * @Version V2.0
 **/
@Service
public class PosTestService {
    @Autowired
    PositionMapper positionMapper;
    public List<Position> testAllPos() {
        return positionMapper.getAllPos();
    }
    public Integer testAddPos(Position position){
        position.setCreateDate(new Date());
        position.setEnabled(true);
        return positionMapper.insertSelective(position);
    }

    public Integer testUpdatePos(Position position) {
        return positionMapper.updateByPrimaryKeySelective(position);
    }

    public Integer testDeletePos(Integer id) {
        return positionMapper.deleteByPrimaryKey(id);
    }

    public Integer testDeleteArray(Integer[] ids) {
        return positionMapper.testDeleteArray(ids);
    }
}
