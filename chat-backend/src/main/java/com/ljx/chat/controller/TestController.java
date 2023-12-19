package com.ljx.chat.controller;

import com.ljx.chat.mapper.HistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private HistoryMapper historyMapper;
    @GetMapping("/test")
    public String test() {
        return historyMapper.selectList(null).toString();
    }
}
