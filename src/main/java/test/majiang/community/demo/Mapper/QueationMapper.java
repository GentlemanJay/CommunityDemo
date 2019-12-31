package test.majiang.community.demo.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import test.majiang.community.demo.dao.Question;

@Mapper
public interface QueationMapper {

    @Insert("insert into publish_question(title,content,tag,gmt_create,gmt_modified,creator)" +
            " values(#{title},#{content},#{tag},#{gmtCreate},#{gmtModified},#{creator})")
    void publishQuestion(Question question);
}


