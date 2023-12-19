package com.ljx.chat.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljx.chat.entity.History;
import com.ljx.chat.entity.User;
import com.ljx.chat.entity.Dto;
import com.ljx.chat.mapper.HistoryMapper;
import com.ljx.chat.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class HistoryController {
    @Autowired
    private HistoryMapper historyMapper;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("getHistory")
    public List getHistory() {
        ArrayList<Dto> list = new ArrayList<>();
        List<History> historyList = historyMapper.selectList(null);
        for(int i = 0;i < historyList.size();i++) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(historyList.get(i).getSendTime());
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("nickname").eq("id",historyList.get(i).getUserId());
            User user = userMapper.selectOne(queryWrapper);
            String name = user.getNickname();
            String message = historyList.get(i).getMessage();
            Dto dto = new Dto(name,message,date);
            list.add(dto);
        }
        return list;
    }
}
