package com.zx.community.controller;

import com.zx.community.dto.QuestionDTO;
import com.zx.community.exception.CustomizeErrorCode;
import com.zx.community.exception.CustomizeException;
import com.zx.community.mapper.QuestionMapper;
import com.zx.community.model.Question;
import com.zx.community.model.User;
import com.zx.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class PublishController {
    @Autowired
    QuestionService questionService;
    @GetMapping("/publish")
    public String Publish(){
        return "publish";
    }
    @GetMapping("/publish/{id}")
    public String Edit(@PathVariable("id")Integer id,
                       HttpServletRequest request,
                       Model model)
    {

        QuestionDTO questionDTO=questionService.getById(id);
        if(questionDTO==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user= (User) request.getSession().getAttribute("user");
        if(questionDTO.getCreator()!=user.getId()){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        model.addAttribute("id",id);
        model.addAttribute("title",questionDTO.getTitle());
        model.addAttribute("description",questionDTO.getDescription());
        model.addAttribute("tag",questionDTO.getTag());
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("id")Integer id,
            @RequestParam("title") String title,
            @RequestParam("description")String description,
            @RequestParam("tag")String tag,
            HttpServletResponse response,
            HttpServletRequest request
    ){
        Question question=new Question();
        question.setId(id);
        question.setTitle(title);
        question.setTag(tag);
        question.setDescription(description);
        User user=(User)request.getSession().getAttribute("user");
        question.setCreator(user.getId());
        if(questionService.insertOrUpdate(question)<=0){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        return "redirect:/index";
    }
}
