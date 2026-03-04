package fun.sterblich.blog.controller;

import fun.sterblich.blog.pojo.About;
import fun.sterblich.blog.pojo.Result;
import fun.sterblich.blog.pojo.Block;
import fun.sterblich.blog.service.serviceImpl.AboutServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/*
* 关于页面
* */
@RestController
@RequestMapping("/about")
public class AboutController {

    @Autowired
    private AboutServiceImpl asi;
    //最近使用的技术
    @GetMapping("/tech")
    public Result tech(){
        List<About> list =asi.list();
        List<About> newList=new ArrayList<>();
        for (About about : list) {
            if(about.getStatus()==1){
                newList.add(about);
            }
        }
        return Result.success(new Block<About>(newList.toArray(new About[0])));
    }

}
