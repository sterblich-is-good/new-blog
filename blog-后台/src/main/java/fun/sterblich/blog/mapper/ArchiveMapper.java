package fun.sterblich.blog.mapper;

import fun.sterblich.blog.pojo.Articles;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArchiveMapper {
    @Select("select *from v_archive")
    public List<Articles> select();
}
