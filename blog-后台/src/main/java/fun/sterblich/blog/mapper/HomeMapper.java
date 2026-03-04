package fun.sterblich.blog.mapper;

import fun.sterblich.blog.pojo.Home;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface HomeMapper {
    @Select("select title,subtitle from home where id=1")
    public Home select();

    @Update("update home set title=#{title},subtitle=#{subtitle} where id=1")
    public void update(Home home);
}
