package test.majiang.community.demo.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import test.majiang.community.demo.dao.User;


@Mapper
public interface UserMapper {

    @Insert("insert into account_user(account_id,name,token,gmt_create,gmt_modified,avatar_url,type) " +
            "values(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl},#{type})")
    void insert(User user);

    @Select("select * from account_user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from account_user where id = #{param1}")
    User findUserById(Integer id);
}
