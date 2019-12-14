package com.zx.community.mapper;

import com.zx.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag,avatar_url) " +
            "values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag},#{avatarUrl})")
     void insert(Question question);
    @Select("select id,title,description,gmt_create gmtCreate,gmt_modified gmtModified,comment_count commentCount,view_count viewCount,like_count likeCount,creator,tag,avatar_url avatarUrl from question " +
            "order by gmt_create desc " +
            "limit #{offset},#{size}")
    List<Question> list(@Param("offset") Integer offset,@Param("size") Integer size);
    @Select("select count(1) from question")
    Integer count();
    @Select("select id,title,description,gmt_create gmtCreate,gmt_modified gmtModified,comment_count commentCount,view_count viewCount,like_count likeCount,creator,tag,avatar_url avatarUrl from question " +
            "where creator=#{id} " +
            "order by gmt_create desc " +
            "limit #{offset},#{size}")
    List<Question> listById(@Param("id") Integer id,@Param("offset") Integer offset,@Param("size") Integer size);
    @Select("select count(1) from question where creator=#{id}")
    Integer countById(@Param("id") Integer id);
    @Update("update question set view_count=view_count+1 where id=#{id}")
    void incView(@Param("id") Integer id);
    @Select("select id,title,description,gmt_create gmtCreate,gmt_modified gmtModified,comment_count commentCount,view_count viewCount,like_count likeCount,creator,tag,avatar_url avatarUrl from question " +
            "where id=#{id}" )
    Question getByid(@Param("id")Integer id);
}
