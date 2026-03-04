package fun.sterblich.blog.controller;

import fun.sterblich.blog.pojo.Result;
import fun.sterblich.blog.service.serviceImpl.ArticlesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
* 文章界面
* */
@RestController
@RequestMapping("/articles")
public class ArticlesController {
    @Autowired
    private ArticlesServiceImpl asi;

    @GetMapping
    public Result classificationToHtml1(Long id){
        //文章返回数据
        return Result.success(asi.select(id));
    }
}
