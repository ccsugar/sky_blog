package com.sky.dao;

import com.sky.entity.User;
import com.sky.queryvo.UserBaseinfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Description: 用户持久层接口
 */
@Mapper
@Repository
public interface UserDao {
    User findByUsernameAndPassword(@Param("username") String username,@Param("password") String password);

    User findByUsername(@Param("username") String username);

    User findByNickname(@Param("nickname") String nickname);

    User findByOtherNickname(@Param("nickname") String nickname,@Param("id") Long id);

    int saveUser(User user);

    int updateUserBaseinfo(UserBaseinfo userBaseinfo);

    User getUserById(Long id);

    int updataUserPassword(String userPassword,Long id);
}