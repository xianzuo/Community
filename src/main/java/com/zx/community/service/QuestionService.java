package com.zx.community.service;

import com.zx.community.dto.PaginationDTO;
import com.zx.community.dto.QuestionDTO;
import com.zx.community.mapper.QuestionMapper;
import com.zx.community.model.Question;
import com.zx.community.model.QuestionExample;
import com.zx.community.model.User;
import org.apache.ibatis.session.RowBounds;
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
    UserService userService;
    public PaginationDTO list(Integer page, Integer size) {
        Integer offset=(page-1)*size;

        QuestionExample example=new QuestionExample();
        example.setOrderByClause("gmt_create desc");
        List<Question> questions=questionMapper.selectByExampleWithRowbounds(example,new RowBounds(offset,size));
        PaginationDTO paginationDTO=new PaginationDTO();
        List<QuestionDTO> questionDTOS=ConvertQuestion(questions);
        paginationDTO.setPage(page);
        paginationDTO.setQuestions(questionDTOS);
        Integer totalCount= (int)questionMapper.countByExample(example);
        paginationDTO.setPagination(totalCount,page,size);
        return paginationDTO;
    }

    public PaginationDTO list(Integer id, Integer page, Integer size) {
        Integer offset=(page-1)*size;
        QuestionExample example=new QuestionExample();
        example.setOrderByClause("gmt_create desc");
        example.createCriteria().andCreatorEqualTo(id);
        List<Question> questions=questionMapper.selectByExampleWithRowbounds(example,new RowBounds(offset,size));
        PaginationDTO paginationDTO=new PaginationDTO();
        List<QuestionDTO> questionDTOS=ConvertQuestion(questions);
        paginationDTO.setPage(page);
        paginationDTO.setQuestions(questionDTOS);
        Integer totalCount= (int)questionMapper.countByExample(example);
        paginationDTO.setPagination(totalCount,page,size);
        return paginationDTO;
    }
    public QuestionDTO getById(Integer id){
        Question question=questionMapper.selectByPrimaryKey(id);
        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user=userService.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }
    public void incView(Integer id){
        Question question=questionMapper.selectByPrimaryKey(id);
        QuestionExample example=new QuestionExample();
        question.setViewCount(question.getViewCount()+1);
        questionMapper.updateByPrimaryKey(question);
    }
    public List<QuestionDTO> ConvertQuestion(List<Question> questions){
        List<QuestionDTO> questionDTOS=new ArrayList<>();
        for (Question question : questions) {
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            User user=userService.findById(question.getCreator());
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        return questionDTOS;
    }

}
