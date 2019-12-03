package com.zx.community.mapper;

import com.zx.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag,avatar_url) " +
            "values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag},#{avatarUrl})")
     void insert(Question question);
    @Select("select id,title,description,gmt_create gmtCreate,gmt_modified gmtModified,comment_count commentCount,view_count viewCount,like_count likeCount,creator,tag,avatar_url avatarUrl from question")
    List<Question> list();
}
