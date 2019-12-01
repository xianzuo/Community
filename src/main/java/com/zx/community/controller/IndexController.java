package com.zx.community.controller;

import com.zx.community.mapper.UserMapper;
import com.zx.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {
    @Autowired
    UserMapper userMapper;
    @GetMapping(value = {"/","/index"})
    public String index(HttpServletResponse response,
                        HttpServletRequest request){
        Cookie cookies[]=request.getCookies();
        if(cookies==null)return "index";
        for (Cookie cookie:cookies) {
            if(cookie.getName().equals("token")){
                String token=cookie.getValue();
                User user= userMapper.findByToken(token);
                if(user!=null) {
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }
        return "index";
    }

}
