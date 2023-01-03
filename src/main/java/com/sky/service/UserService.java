package com.sky.service;

import com.sky.entity.User;
import com.sky.queryvo.ShowBlog;
import com.sky.queryvo.UserBaseinfo;

/**
 * @Description: 用户业务层接口

 */
public interface UserService {

//    核对用户名和密码
    User checkUser(String username, String password);

    Boolean checkUsername(String username);

    Boolean checkNickname(String nickname);

    Boolean checkOtherNickname(String nickname, Long id);

    int saveUser(User user);

    int updateUserBaseinfo(UserBaseinfo userBaseinfo);

    User getUserById(Long id);

    int updateUserPassword(String userPassword, Long id);
}