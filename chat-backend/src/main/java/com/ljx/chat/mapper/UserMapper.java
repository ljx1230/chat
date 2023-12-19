package com.ljx.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljx.chat.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
