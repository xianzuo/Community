package com.zx.community.interceptor;

import com.zx.community.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user =(User)request.getSession().getAttribute("user");
        if(user==null){
            request.setAttribute("errmsg","用户未登录");
            request.getRequestDispatcher("/error").forward(request,response);
            return false;
        }
        return true;
    }
}
