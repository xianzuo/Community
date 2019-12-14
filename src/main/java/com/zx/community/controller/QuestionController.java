package com.zx.community.controller;

import com.zx.community.dto.PaginationDTO;
import com.zx.community.dto.QuestionDTO;
import com.zx.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class QuestionController{
    @Autowired
    QuestionService questionService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id")Integer id,
                           HttpServletRequest request,
                           HttpServletResponse response,
                           Model model){
        questionService.incView(id);
        QuestionDTO questionDTO=questionService.getById(id);

        model.addAttribute("question",questionDTO);
        return "question";
    }
}
