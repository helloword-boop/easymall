package com.easymall.controller;

import com.easymall.common.pojo.User;
import com.easymall.common.utils.CookieUtils;
import com.easymall.common.vo.SysResult;
import com.easymall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("user/manage")
public class UserController {
    @Autowired
    private UserService userService;
    /*检查用户名是否存在*/
    @RequestMapping("checkUserName")
    public SysResult checkUserName(String userName){
        return userService.checkUserName(userName);
    }
    /*注册用户*/
    @RequestMapping("save")
    public SysResult addUser(User user){
        try {
            userService.addUser(user);
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return new SysResult(201,"注册失败",null);
        }
    }
    /*用户登录*/
    @RequestMapping("login")
    public SysResult userLogin(User user, HttpServletResponse response, HttpServletRequest request){
        String ticket=userService.userLogin(user);
        if("".equals(ticket)){
            return new SysResult(201,"登录失败",null);
        }else {
            CookieUtils.setCookie(request,response,"EM_TICKET",ticket);
            return SysResult.ok();
        }

    }
    /*查询用户登录信息*/
    @RequestMapping("query/{ticket}")
    public SysResult queryUserLogin(@PathVariable String ticket){
        return userService.queryUserLogin(ticket);
    }
    /*用户登出*/
    @RequestMapping("logout")
    public SysResult userLogout(HttpServletRequest request,HttpServletResponse response){
        CookieUtils.setCookie(request,response,"EM_TICKET",null,0);
        return SysResult.ok();
    }

}
