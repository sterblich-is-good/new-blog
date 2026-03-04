package fun.sterblich.blog.behindController;

import fun.sterblich.blog.pojo.About;
import fun.sterblich.blog.pojo.Block;
import fun.sterblich.blog.pojo.Home;
import fun.sterblich.blog.pojo.Result;
import fun.sterblich.blog.service.serviceImpl.AboutServiceImpl;
import fun.sterblich.blog.service.serviceImpl.HomeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/behind/misce")
public class MiscellaneousController {
    @Autowired
    private AboutServiceImpl asi;
    @Autowired
    private HomeServiceImpl hsi;

    //最近使用的技术
    @GetMapping("/tech")
    public Result tech(){
        return Result.success(new Block<About>(asi.list().toArray(new About[0])));
    }

    @PostMapping("/techupdate")
    public Result update(@RequestBody List<About> list){
        asi.insert(list);
        return Result.success();
    }

    @GetMapping("/home")
    public Result home(){
        return Result.success(hsi.select());
    }

    @PostMapping("/homeupdate")
    public Result update(@RequestBody Home home){
        hsi.update(home);
        return Result.success();
    }
}
