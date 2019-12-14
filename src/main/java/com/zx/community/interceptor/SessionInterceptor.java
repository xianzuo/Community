package com.zx.community.interceptor;

import com.zx.community.mapper.UserMapper;
import com.zx.community.model.User;
import com.zx.community.model.UserExample;
import com.zx.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie cookies[]=request.getCookies();
        if(cookies==null)return true;
        for (Cookie cookie:cookies) {
            if(cookie.getName().equals("token")){
                String token=cookie.getValue();

                User user= userService.getByToken(token);

                if(user!=null) {
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
