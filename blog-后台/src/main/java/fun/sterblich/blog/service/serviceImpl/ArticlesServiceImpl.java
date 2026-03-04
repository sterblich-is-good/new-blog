package fun.sterblich.blog.service.serviceImpl;

import fun.sterblich.blog.mapper.ArticlesMapper;
import fun.sterblich.blog.pojo.Archive;
import fun.sterblich.blog.pojo.Articles;
import fun.sterblich.blog.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ArticlesServiceImpl {
    @Autowired
    private ArticlesMapper am;

    public Articles select(Long id){
        return am.select(id);
    }

    public void update(Articles articles) {
        //确保准确性
        articles.setArchiveId(null);

        Integer year=Integer.valueOf(articles.getTime().split("-")[0]);

        ArrayList<Archive> list=am.selectArchive();
        for (Archive archive : list) {
            if(archive.getYear().equals(year)){
                articles.setArchiveId(archive.getId());
                break;
            }
        }

        if(articles.getArchiveId()==null){
            am.insertArchive(year);
            articles.setArchiveId(am.selectArchiveByYear(year).getId());
        }


        am.update(articles);
    }

    public void delete(Integer id) {
        am.delete(id);
    }

    public void insert(Articles articles) {
        //确保准确性
        articles.setArchiveId(null);

        Integer year=Integer.valueOf(articles.getTime().split("-")[0]);

        ArrayList<Archive> list=am.selectArchive();
        for (Archive archive : list) {
            if(archive.getYear().equals(year)){
                articles.setArchiveId(archive.getId());
                break;
            }
        }

        if(articles.getArchiveId()==null){
            am.insertArchive(year);
            articles.setArchiveId(am.selectArchiveByYear(year).getId());
        }
        am.insert(articles);
    }
}
