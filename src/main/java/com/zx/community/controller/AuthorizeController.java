package com.zx.community.controller;

import com.zx.community.dto.AccesstokenDTO;
import com.zx.community.dto.GithubUser;
import com.zx.community.mapper.UserMapper;
import com.zx.community.model.User;
import com.zx.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserMapper userMapper            ;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        AccesstokenDTO accesstokenDTO = new AccesstokenDTO();
        accesstokenDTO.setClient_id(clientId);
        accesstokenDTO.setClient_secret(clientSecret);
        accesstokenDTO.setCode(code);
        accesstokenDTO.setRedirect_uri(redirectUri);
        accesstokenDTO.setState(state);
        String accessToken=githubProvider.getAccessToken(accesstokenDTO);
        GithubUser githubUser=githubProvider.getUser(accessToken);
        if(githubUser!=null&&githubUser.getId()!=null){
            //登陆成功
            //request.getSession().setAttribute("user",githubUser);

            User user = new User();
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());
            user.setAvatar_url(githubUser.getAvatar_url());
            if(userMapper.findByAccountId(user.getAccountId())!=null){
                userMapper.update(user);
            }
            else{
               userMapper.insert(user);
            }
            response.addCookie(new Cookie("token",user.getToken()));
            return "redirect:index";
        }
        else{
            //登陆失败
        }
        //System.out.println(githubUser);
        return "redirect:index";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie=new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:index";

    }
}
