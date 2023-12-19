package com.ljx.chat.controller;

import com.ljx.chat.entity.User;
import com.ljx.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("login")
    public Map login(HttpServletRequest request, @RequestParam Map<String,String> data) {
        User user = new User();
        user.setUsername(data.get("username"));
        user.setPassword(data.get("password"));
        System.out.println(user);
        return userService.login(request,user);
    }
    @GetMapping("logout")
    public Map logout(HttpServletRequest request) {
        request.getSession().removeAttribute("login");
        Map<String,String> result = new HashMap<>();
        result.put("state","success");
        return result;
    }

    @GetMapping("getinfo")
    public Map getinfo(@RequestParam Integer id) {
        return userService.getinfo(id);
    }

    @GetMapping("getUserList")
    public List<User> getUserList() {
        return userService.getUserList();
    }
}
