package com.zx.community.controller;

import com.zx.community.dto.AccesstokenDTO;
import com.zx.community.dto.GithubUser;
import com.zx.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    GithubProvider githubProvider;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state){
        AccesstokenDTO accesstokenDTO = new AccesstokenDTO();
        accesstokenDTO.setClient_id("e49f459a23998292ae8a");
        accesstokenDTO.setClient_secret("5542289aa9f627bb4ca8458e620f1037ca7ebd00");
        accesstokenDTO.setCode(code);
        accesstokenDTO.setRedirect_uri("http://169.254.139.164/callback");
        accesstokenDTO.setState(state);
        String accessToken=githubProvider.getAccessToken(accesstokenDTO);
        GithubUser githubUser=githubProvider.getUser(accessToken);
        System.out.println(githubUser);
        return "index";
    }
}
