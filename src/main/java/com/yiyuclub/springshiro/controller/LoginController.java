package com.yiyuclub.springshiro.controller;

import com.yiyuclub.springshiro.models.IdeaUser;
import com.yiyuclub.springshiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.yiyuclub.springshiro.config.tools.MD5Pwd;

@RestController
public class LoginController {

    @Autowired
    public UserService userService;

    @GetMapping(value = "/login")
    public List<IdeaUser> login(String username, String password) {
        List<IdeaUser> list = null;
        try {
            MD5Pwd(password);
            Subject subject = SecurityUtils.getSubject();

            UsernamePasswordToken token = new UsernamePasswordToken(username, password);

            subject.login(token);
            System.out.println("成功");
            list = userService.getUserList();
            return list;
        } catch (AuthenticationException e) {
            System.out.println("失败");
            return null;
        }
    }

    @GetMapping(value = "/user/getlist")
    public List<IdeaUser> getuserlist() {
        System.out.println("成功");
        List<IdeaUser> list = userService.getUserList();

        return list;
    }

    @GetMapping(value = "/admin/getlist")
    public List<IdeaUser> getadminlist() {
        System.out.println("成功");
        List<IdeaUser> list = userService.getUserList();

        return list;
    }

}
