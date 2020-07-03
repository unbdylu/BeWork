package com.houlin.vhr.service;

import com.houlin.vhr.mapper.HrMapper;
import com.houlin.vhr.mapper.HrRoleMapper;
import com.houlin.vhr.model.Hr;
import com.houlin.vhr.untils.HrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName HrService
 * @Description: TODO
 * @Author: @houlin Chen
 * @Date 2020/4/21
 * @Version V2.0
 **/
@Service
public class HrService implements UserDetailsService {
    @Autowired
    HrMapper hrMapper;
    @Autowired
    HrRoleMapper hrRoleMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Hr hr=hrMapper.loadUserByUsername(username);
        if(hr==null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        hr.setRoles(hrMapper.getHrRolesById(hr.getId()));//登陆成功 返回用户id=》mapper.java>>mapper.xml
        return hr;
    }


     public List<Hr> getAllHrs(String keywords) {
        return hrMapper.getAllHrs(HrUtils.getCurrentHr().getId(),keywords);
    }

    public Integer upDataHr(Hr hr) {
            return hrMapper.updateByPrimaryKeySelective(hr);
    }
    @Transactional
    public boolean updateHrRole(Integer hrid, Integer[] rids) {
        hrRoleMapper.deleteByHrid(hrid);
        return hrRoleMapper.addRole(hrid, rids) == rids.length;
    }

    public Integer deleteByHrId(Integer id) {
        return hrMapper.deleteByPrimaryKey(id);
    }

    public List<Hr> getAllHrsExceptCurrentId() {
        return hrMapper.getAllHrsExceptCurrentId(HrUtils.getCurrentHr().getId());
    }
}
