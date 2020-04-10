package com.yiyuclub.springshiro.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yiyuclub.springshiro.mapper.IdeaRoleMapper;
import com.yiyuclub.springshiro.mapper.IdeaUserMapper;
import com.yiyuclub.springshiro.models.IdeaRole;
import com.yiyuclub.springshiro.models.IdeaUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private IdeaUserMapper userMapper;

    @Autowired
    private IdeaRoleMapper roleMapper;

    public List<IdeaUser> getUserList(){
        QueryWrapper<IdeaUser> queryWrapper = new QueryWrapper<IdeaUser>();

        List<IdeaUser> ideaUsers = userMapper.selectList(queryWrapper);

        return ideaUsers;
    }

    public IdeaUser getUserByName(String username){
        QueryWrapper<IdeaUser> queryWrapper = new QueryWrapper<IdeaUser>();
        queryWrapper.eq("username",username);
        IdeaUser ideaUser = userMapper.selectOne(queryWrapper);

        return ideaUser;
    }

    public IdeaRole getRoleById(int role_id){
        QueryWrapper<IdeaRole> queryWrapper = new QueryWrapper<IdeaRole>();
        queryWrapper.eq("role_id",role_id);
        IdeaRole ideaRole = roleMapper.selectOne(queryWrapper);

        return ideaRole;
    }
}
