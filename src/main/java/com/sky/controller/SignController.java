package com.sky.controller;

import com.sky.entity.User;
import com.sky.service.UserService;
import com.sky.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 用户登录控制器
 */
@Controller
public class SignController {
    @Autowired
    private UserService userService;

    @Value("${user.defaultAvatar}")
    private String avatar;

    @PostMapping("/userSignUp")
    @ResponseBody
    public List<String> signUp(@RequestParam String username, @RequestParam String nickname, @RequestParam String password,@RequestParam String email){
        List<String> jsonMsg=new ArrayList<>();
        Boolean userNameValidation=userService.checkUsername(username);
        Boolean nickNameValidation=userService.checkNickname(nickname);
        if (userNameValidation||nickNameValidation){
            jsonMsg.add("fail");
            if(userNameValidation){
                jsonMsg.add("用户名已被使用!");
            }
            if(nickNameValidation){
                jsonMsg.add("昵称已被使用!");
            }
        }
        else{
            Date date=new Date();
            User user=new User();
            user.setType(0);
            user.setUsername(username);
            user.setNickname(nickname);
            user.setPassword(MD5Utils.code(password));
            user.setAvatar(avatar);
            user.setEmail(email);
            user.setCreateTime(date);
            user.setUpdateTime(date);
            userService.saveUser(user);
            jsonMsg.add("success");
        }
        return jsonMsg;
    }

    @PostMapping("/userSignIn")
    @ResponseBody
    public List<String> signIn(@RequestParam String username, @RequestParam String password, HttpSession session){
        List<String> jsonMsg=new ArrayList<>();
        if(session.getAttribute("user")!=null){
            jsonMsg.add("deplicated");
        }else {
            User user = userService.checkUser(username, password);
            if (user != null) {
                user.setPassword(null);
                session.setAttribute("user", user);
                jsonMsg.add("success");
            } else {
                jsonMsg.add("fail");
                jsonMsg.add("用户名或密码错误");
            }
        }
        return jsonMsg;
    }

    @GetMapping("/userSignOut")
    @ResponseBody
    public String getUserSignOut(HttpSession session){
        session.removeAttribute("user");
        return "success";
    }

    @RequestMapping("/signInStateCheck")
    @ResponseBody
    public String signStateCheck(HttpSession session){
        if(session.getAttribute("user")!=null)
            return "success";
        else
            return "fail";
    }
}
