package com.zx.community.mapper;

import com.zx.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("insert into user(id,name,account_id,token,gmt_create,gmt_modified,avatar_url)" +
            " values(#{id},#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatar_url})")
     void insert(User user);

    @Select("select id,name,account_id accountId,token,gmt_create gmtCreate,gmt_modified gmtModified,avatar_url from user where token=#{token}")
    User findByToken(String token);
    @Select("select id,name,account_id accountId,token,gmt_create gmtCreate,gmt_modified gmtModified,avatar_url from user where account_id=#{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update user set token=#{token},gmt_modified=#{gmtModified} where account_id=#{accountId}")
    void update(User user);
}
