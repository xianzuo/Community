package com.zx.community.service;

import com.zx.community.dto.PaginationDTO;
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
    public PaginationDTO list(Integer page, Integer size) {
        Integer offset=(page-1)*size;
        List<QuestionDTO> questiondtos=new ArrayList<>();
        List<Question> questions=questionMapper.list(offset,size);
        PaginationDTO paginationDTO=new PaginationDTO();
        for (Question question:questions) {
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questiondtos.add(questionDTO);
        }
        paginationDTO.setPage(page);
        paginationDTO.setQuestions(questiondtos);
        Integer totalCount= questionMapper.count();
        paginationDTO.setPagination(totalCount,page,size);
        return paginationDTO;
    }

    public PaginationDTO list(Integer id, Integer page, Integer size) {
        Integer offset=(page-1)*size;
        List<QuestionDTO> questiondtos=new ArrayList<>();
        List<Question> questions=questionMapper.listById(id,offset,size);
        PaginationDTO paginationDTO=new PaginationDTO();
        for (Question question:questions) {
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questiondtos.add(questionDTO);
        }
        paginationDTO.setPage(page);
        paginationDTO.setQuestions(questiondtos);
        Integer totalCount= questionMapper.countById(id);
        paginationDTO.setPagination(totalCount,page,size);
        return paginationDTO;
    }
}
