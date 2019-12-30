package test.majiang.community.demo.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import test.majiang.community.demo.dao.User;


@Mapper
public interface UserMapper {

    @Insert("insert into account_user(account_id,name,token,gmt_create,gmt_modified) " +
            "values(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);
}
