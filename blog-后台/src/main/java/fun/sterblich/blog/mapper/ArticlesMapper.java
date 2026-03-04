package fun.sterblich.blog.mapper;

import fun.sterblich.blog.pojo.Archive;
import fun.sterblich.blog.pojo.Articles;
import fun.sterblich.blog.pojo.Result;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface ArticlesMapper {

    @Select("select id,text,title,`desc`,`time`,`status` from articles where id=#{id}")
    public Articles select(Long id);

    @Update("update articles set title=#{title},`desc`=#{desc},text=#{text},status=#{status},archive_id=#{archiveId},time=#{time} where id=#{id}")
    public void update(Articles articles);

    @Select("SELECT id,`year` FROM `archive`")
    public ArrayList<Archive> selectArchive();

    @Insert("INSERT into archive(year) values(#{year})")
    public void insertArchive(Integer year);

    @Select("SELECT id,`year` FROM `archive`where year=#{year}")
    public Archive selectArchiveByYear(Integer year);

    @Delete("DELETE from articles where id=#{id}")
    public void delete(Integer id);

    @Insert("INSERT into articles(title,time,`desc`,archive_id,text,status) values (#{title},#{time},#{desc},#{archiveId},#{text},#{status})")
    public void insert(Articles articles);
}
