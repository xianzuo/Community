package com.zx.community.service;

import com.zx.community.dto.QuestionDTO;
import com.zx.community.mapper.QuestionMapper;
import com.zx.community.mapper.UserMapper;
import com.zx.community.model.Question;
import com.zx.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;
    public List<QuestionDTO> list() {
        List<QuestionDTO> questiondtos=new ArrayList<>();
        List<Question> questions=questionMapper.list();
        for (Question question:questions) {
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questiondtos.add(questionDTO);

        }
        return questiondtos;
    }
}
