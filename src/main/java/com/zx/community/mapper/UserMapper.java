package com.zx.community.mapper;

import com.zx.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user(id,name,account_id,token,gmt_create,gmt_modified,avatar_url)" +
            " values(#{id},#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatar_url})")
     void insert(User user);

    @Select("select id,name,account_id accountId,token,gmt_create gmtCreate,gmt_modified gmtModified,avatar_url from user where token=#{token}")
    User findByToken(String token);
}
