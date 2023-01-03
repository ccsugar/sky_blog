package com.sky.controller;

import com.sky.entity.User;
import com.sky.queryvo.UserBaseinfo;
import com.sky.service.UserService;
import com.sky.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Description: 用户信息显示与修改控制器
 */
@Controller
public class UserInfoController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    public String userInfo(HttpSession session) {
        if(session.getAttribute("user")!=null)
            return "userInfo";
        else
            return "redirect:/";
    }

    @PostMapping("/userBaseinfoModify")
    @ResponseBody
    public List<String> userBaseinfoModify(HttpSession session, UserBaseinfo userBaseinfo) {
        List<String> jsonMsg=new ArrayList<>();
        Long userId=((User) session.getAttribute("user")).getId();
        if(session.getAttribute("user")!=null){
            if(userService.checkOtherNickname(userBaseinfo.getNickname(),userId)){
                jsonMsg.add("fail");
                jsonMsg.add("用户名已被使用!");
            } else {
                userBaseinfo.setId(userId);
                if (userService.updateUserBaseinfo(userBaseinfo) == 1) {
                    session.setAttribute("user", userService.getUserById(userId));
                    jsonMsg.add("success");
                    jsonMsg.add("修改成功");
                } else {
                    jsonMsg.add("fail");
                    jsonMsg.add("修改失败");
                }
            }
        } else {
            jsonMsg.add("expired");
        }
        return jsonMsg;
    }

    @PostMapping("/userPasswordModify")
    @ResponseBody
    public List<String> userPasswordModify(HttpSession session, @RequestParam String new_password,@RequestParam String original_password) {
        original_password =MD5Utils.code(original_password);
        new_password =MD5Utils.code(new_password);
        List<String> jsonMsg=new ArrayList<>();
        Long userId=((User) session.getAttribute("user")).getId();
        User user=userService.getUserById(userId);
        if(session.getAttribute("user")!=null){
            if(!Objects.equals(user.getPassword(), original_password)){
                jsonMsg.add("fail");
                jsonMsg.add("原密码错误!");
            } else {
                if (userService.updateUserPassword(new_password,userId) == 1) {
                    jsonMsg.add("success");
                    jsonMsg.add("修改成功");
                } else {
                    jsonMsg.add("fail");
                    jsonMsg.add("修改失败");
                }
            }
        } else {
            jsonMsg.add("expired");
        }
        return jsonMsg;
    }

}
