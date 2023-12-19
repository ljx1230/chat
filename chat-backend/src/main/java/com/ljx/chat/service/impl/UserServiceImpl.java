package com.ljx.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ljx.chat.entity.User;
import com.ljx.chat.mapper.UserMapper;
import com.ljx.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public Map login(HttpServletRequest request, User user) {
        Map<String,Object> result = new HashMap<>();
        String username = user.getUsername();
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername()).eq(User::getPassword, user.getPassword());
        User user1 = userMapper.selectOne(wrapper);
        if(user1 == null) {
            result.put("state","fail");
            System.out.println("失败!");
            return result;
        }
        request.getSession().setAttribute("login", true);
        result.put("state","success");
        user1.setPassword("");
        result.put("user",user1);
        System.out.println("成功");
        return result;
    }

    @Override
    public Map getinfo(Integer id) {
        User user = userMapper.selectById(id);
        Map result = new HashMap();
        result.put("username",user.getUsername());
        result.put("nickname",user.getNickname());
        return result;
    }

    @Override
    public List<User> getUserList() {
        List<User> users = userMapper.selectList(null);
        users.forEach(user -> user.setPassword(""));
        return users;
    }

}
