package fun.sterblich.blog.behindController;

import fun.sterblich.blog.pojo.PasswordUpdateDTO;
import fun.sterblich.blog.pojo.Result;
import fun.sterblich.blog.pojo.User;
import fun.sterblich.blog.service.serviceImpl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/behind/user")
public class UserController {
    @Autowired
    UserServiceImpl usi;

    @PostMapping("/login")//登录
    public Result login(@RequestBody User user){return usi.login(user.getUsername(),user.getPassword());}

    @PostMapping("/modify")//修改密码
    public Result modify(@RequestAttribute("user") User user, @RequestBody PasswordUpdateDTO updateDTO){
        return usi.modify(user,updateDTO);
    }

    @GetMapping("/refresh")//刷新页面
    public Result refresh(){
        return Result.success();
    }

    @GetMapping("/exit")//退出登录
    public Result exit(HttpServletRequest request){
        usi.exit((User)request.getAttribute("user"));
        return Result.success();
    }

}
