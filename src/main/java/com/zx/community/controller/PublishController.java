package com.zx.community.controller;

import com.zx.community.mapper.QuestionMapper;
import com.zx.community.model.Question;
import com.zx.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class PublishController {
    @Autowired
    QuestionMapper questionMapper;
    @GetMapping("/publish")
    public String Publish(){
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description")String description,
            @RequestParam("tag")String tag,
            HttpServletResponse response,
            HttpServletRequest request
    ){
        Question question=new Question();
        question.setTitle(title);
        question.setTag(tag);
        question.setDescription(description);
        User user=(User)request.getSession().getAttribute("user");
        if(user==null){
            request.getSession().setAttribute("errmsg","用户未登录");
            return "index";
        }
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setAvatarUrl(user.getAvatar_url());

        questionMapper.insert(question);
        return "redirect:/index";
    }
}
