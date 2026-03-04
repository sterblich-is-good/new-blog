package fun.sterblich.blog.mapper;

import fun.sterblich.blog.pojo.About;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AboutMapper {
    @Select("select id,tech,status from about_techs ")
    public List<About> select();

    @Delete("DELETE FROM about_techs")
    public void delete();

    @Insert("INSERT INTO about_techs (tech, status) VALUES(#{tech},#{status})")
    public void insert(About about);
}
