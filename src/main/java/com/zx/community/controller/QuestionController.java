package com.zx.community.controller;

import com.zx.community.dto.QuestionDTO;
import com.zx.community.exception.CustomizeErrorCode;
import com.zx.community.exception.CustomizeException;
import com.zx.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class QuestionController{
    @Autowired
    QuestionService questionService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id")Integer id,
                           HttpServletRequest request,
                           HttpServletResponse response,
                           Model model){
        if(questionService.incView(id)<=0){
            throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO=questionService.getById(id);
        if(questionDTO==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }

        model.addAttribute("question",questionDTO);
        return "question";
    }
}
