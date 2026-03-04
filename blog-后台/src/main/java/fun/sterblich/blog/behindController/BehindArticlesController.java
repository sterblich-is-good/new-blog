package fun.sterblich.blog.behindController;

import fun.sterblich.blog.pojo.Articles;
import fun.sterblich.blog.pojo.Result;
import fun.sterblich.blog.service.serviceImpl.ArticlesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/behind/articles")
public class BehindArticlesController {

    @Autowired
    private ArticlesServiceImpl asi;

    @GetMapping
    public Result classificationToHtml1(Long id){
        //文章返回数据
        return Result.success(asi.select(id));
    }

    @PostMapping("/update")
    public Result articlesUpdate(@RequestBody Articles articles){
        asi.update(articles);
        return Result.success();
    }

    @PostMapping("/insert")
    public Result articlesInsert(@RequestBody Articles articles){
        asi.insert(articles);
        return Result.success();
    }

    @PostMapping("/delete")
    public Result articlesDelete(@RequestBody Articles articles){
        asi.delete(articles.getId());
        return Result.success();
    }
}
