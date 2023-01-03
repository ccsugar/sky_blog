package com.sky.service.Impl;

import com.sky.dao.UserDao;
import com.sky.entity.User;
import com.sky.queryvo.UserBaseinfo;
import com.sky.service.UserService;
import com.sky.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 用户业务层接口实现类

 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User checkUser(String username, String password) {
        User user = userDao.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }

    @Override
    public Boolean checkUsername(String username) {
        User user=userDao.findByUsername(username);
        return user != null;
    }

    @Override
    public Boolean checkNickname(String nickname) {
        User user=userDao.findByNickname(nickname);
        return user != null;
    }

    @Override
    public Boolean checkOtherNickname(String nickname, Long id) {
        User user=userDao.findByOtherNickname(nickname,id);
        return user != null;
    }

    @Override
    public int saveUser(User user) {
        return userDao.saveUser(user);
    }

    @Override
    public int updateUserBaseinfo(UserBaseinfo userBaseinfo) {
        return userDao.updateUserBaseinfo(userBaseinfo);
    }

    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    public int updateUserPassword(String userPassword,Long id) {
        return userDao.updataUserPassword(userPassword,id);
    }
}