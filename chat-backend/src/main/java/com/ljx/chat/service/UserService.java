package com.ljx.chat.service;

import com.ljx.chat.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface UserService {
    Map login(HttpServletRequest request, User user);

    Map getinfo(Integer id);

    List<User> getUserList();
}
