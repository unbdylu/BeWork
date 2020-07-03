package com.houlin.vhr.service;

import com.houlin.vhr.mapper.RoleMapper;
import com.houlin.vhr.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName RoleService
 * @Description: TODO
 * @Author: @houlin Chen
 * @Date 2020/5/14
 * @Version V2.0
 **/
@Service
public class RoleService {
    @Autowired
        RoleMapper roleMapper;
    public List<Role> getAllRoles() {
        return roleMapper.getAllRoles();
    }

    public Integer addRole(Role role) {
        if (!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_" + role.getName());
        }
        return roleMapper.insert(role);
    }

    public Integer deleteRoleById(Integer rid) {
        return roleMapper.deleteByPrimaryKey(rid);
    }
}
