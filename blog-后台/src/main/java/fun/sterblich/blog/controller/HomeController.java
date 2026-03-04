package fun.sterblich.blog.controller;

import fun.sterblich.blog.pojo.Result;
import fun.sterblich.blog.pojo.Home;
import fun.sterblich.blog.service.serviceImpl.HomeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private HomeServiceImpl hsi;

    @GetMapping
    public Result home(){
        return Result.success(hsi.select());
    }
}
