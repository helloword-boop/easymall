package com.easymall.mapper;

import com.easymall.common.pojo.User;

public interface UserMapper {
    int checkUserByName(String userName);

    void addUser(User user);

    User queryUser(User user);
}
