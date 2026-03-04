package fun.sterblich.blog.service.serviceImpl;

import fun.sterblich.blog.mapper.ArchiveMapper;
import fun.sterblich.blog.pojo.Articles;
import fun.sterblich.blog.pojo.Archive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArchiveServiceImpl {
    @Autowired
    private ArchiveMapper am;

    public List<Archive> list() {
        //获取数据
        List<Articles> articlesList = am.select();

        //处理数据
        //要返回的数据
        List<Archive> archiveList =new ArrayList<>();

        //每个Archive类里的集合
        ArrayList<Articles> articlesList2 = new ArrayList<>();

        //设置第一个的值
        Archive archive=new Archive();
        int year=articlesList.get(0).getYear();
        archive.setYear(year);
        archive.setId(articlesList.get(0).getArchiveId());

        for (int i = 0; i < articlesList.size(); i++) {
            Articles articles=articlesList.get(i);
            if(articles.getStatus()==0) continue;
            articles.setTime(articles.getTime().split("-")[1]+"-"+articles.getTime().split("-")[2]);
            if(year!=articles.getYear()){
                //加入上一个
                archive.setArticlesList(articlesList2);
                archiveList.add(archive);
                //开始处理下一个
                articlesList2=new ArrayList<>();
                year=articles.getYear();
                archive=new Archive();
                archive.setYear(articles.getYear());
                archive.setId(articles.getArchiveId());
            }
            articlesList2.add(articles);
        }
        archive.setArticlesList(articlesList2);
        archiveList.add(archive);
        
        //返回数据
        return archiveList;
    }

    public List<Archive> behindList() {
        //获取数据
        List<Articles> articlesList = am.select();

        //处理数据
        //要返回的数据
        List<Archive> archiveList =new ArrayList<>();

        //每个Archive类里的集合
        ArrayList<Articles> articlesList2 = new ArrayList<>();

        //设置第一个的值
        Archive archive=new Archive();
        int year=articlesList.get(0).getYear();
        archive.setYear(year);
        archive.setId(articlesList.get(0).getArchiveId());

        for (int i = 0; i < articlesList.size(); i++) {
            Articles articles=articlesList.get(i);
            articles.setTime(articles.getTime().split("-")[1]+"-"+articles.getTime().split("-")[2]);
            if(year!=articles.getYear()){
                //加入上一个
                archive.setArticlesList(articlesList2);
                archiveList.add(archive);
                //开始处理下一个
                articlesList2=new ArrayList<>();
                year=articles.getYear();
                archive=new Archive();
                archive.setYear(articles.getYear());
                archive.setId(articles.getArchiveId());
            }
            articlesList2.add(articles);
        }
        archive.setArticlesList(articlesList2);
        archiveList.add(archive);
        //返回数据
        return archiveList;
    }

}
