package com.houlin.vhr.service;

import com.houlin.vhr.mapper.PositionMapper;
import com.houlin.vhr.model.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName PositionService
 * @Description: TODO
 * @Author: @houlin Chen
 * @Date 2020/5/11
 * @Version V2.0
 **/
@Service
public class PositionService {

    @Autowired
    PositionMapper positionMapper;
    public List<Position> getAllPositions() {
        return positionMapper.getAllPositions();
    }

    public int updatePosition(Position position) {
        return positionMapper.updateByPrimaryKeySelective(position);
    }

    public int addPosition(Position position) {
        position.setEnabled(true);
        position.setCreateDate(new Date());
        return positionMapper.insertSelective(position);
    }

    public int deletePositionById(int id) {
        return positionMapper.deleteByPrimaryKey(id);
    }


    public Integer deletePositionsByIds(Integer[] ids) {
        return positionMapper.deletePositionsByIds(ids);
    }
}
