package fun.sterblich.blog.mapper;

import fun.sterblich.blog.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("SELECT id,username,password,token_version FROM `user`where username=#{username}")
    public User login(String username);

    @Select("SELECT token_version FROM `user`where id=#{id}")
    public Integer selectTokenVersionById(Integer id);

    @Update("update `user` set password=#{password},token_version = token_version+1 where username=#{username}")
    public void update(User user);

    @Update("update `user` set token_version = token_version+1 where id=#{id}")
    public void updateTokenVersionById(User user);
}
