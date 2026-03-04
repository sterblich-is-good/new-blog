package fun.sterblich.blog.behindController;

import fun.sterblich.blog.pojo.Archive;
import fun.sterblich.blog.pojo.Block;
import fun.sterblich.blog.pojo.Result;
import fun.sterblich.blog.service.serviceImpl.ArchiveServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * 归档页面
 * */
@RestController
@RequestMapping("/behind/archive")
public class BehindArchiveController {
    @Autowired
    ArchiveServiceImpl asi;

    @GetMapping("/list")
    public Result archive(){
        List<Archive> list=asi.behindList();
        return Result.success(new Block<Archive>(list.toArray(new Archive[0])));
    }
}
