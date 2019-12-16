package com.zx.community.service;

import com.zx.community.mapper.UserMapper;
import com.zx.community.model.User;
import com.zx.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    public User getByToken(String token){
        UserExample example=new UserExample();
        example.createCriteria().andTokenEqualTo(token);
        List<User> users=userMapper.selectByExample(example);
        if(users==null||users.size()==0)return null;
        return users.get(0);
    }

    public User findById(Integer creator) {
        return userMapper.selectByPrimaryKey(creator);
    }
    public User findByAccountId(String accountId){
        UserExample example=new UserExample();
        example.createCriteria().andAccountIdEqualTo(accountId);
        List<User> users = userMapper.selectByExample(example);
        if(users==null||users.size()==0)return null;
        return users.get(0);
    }
    public int createOrUpdate(User user) {
        if(findByAccountId(user.getAccountId())==null){
            //新用户创建用户
            return userMapper.insertSelective(user);
        }
        else{
            //老用户更新token
            UserExample example=new UserExample();
            example.createCriteria().andAccountIdEqualTo(user.getAccountId());
            return userMapper.updateByExampleSelective(user,example);
        }
    }
}
