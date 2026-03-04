package fun.sterblich.blog.service.serviceImpl;

import fun.sterblich.blog.mapper.UserMapper;
import fun.sterblich.blog.pojo.PasswordUpdateDTO;
import fun.sterblich.blog.pojo.Result;
import fun.sterblich.blog.pojo.User;
import fun.sterblich.blog.utiles.JwtUtil;
import fun.sterblich.blog.utiles.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl {
    @Autowired
    private UserMapper um;

    private JwtUtil jwtUtil;

    public UserServiceImpl(JwtUtil jwtUtil){
        this.jwtUtil=jwtUtil;
    }

    public Result login(String username, String password) {
        User user=um.login(username);
        if(user==null) return Result.error("用户名或密码错误");
        if (PasswordUtil.matches(password,user.getPassword())) {
            Map<String,Object> map=new HashMap<>();
            map.put("id",user.getId());
            map.put("username",user.getUsername());
            map.put("tokenVersion",user.getTokenVersion());
            String token=jwtUtil.setJwt(map);
            Map<String,String> map1=new HashMap<>();
            map1.put("token",token);
            return Result.success(map1);
        }
        return Result.error("用户名或密码错误");
    }

    public Result modify(User user, PasswordUpdateDTO updateDTO) {
        User user1=um.login(user.getUsername());
        if (!PasswordUtil.matches(updateDTO.getOldPassword(),user1.getPassword())) return Result.error("旧密码错误");
        if (!updateDTO.getNewPassword().equals(updateDTO.getConfirmPassword())) return Result.error("两次密码不一致");
        if (!PasswordUtil.isValid(updateDTO.getNewPassword())) return Result.error("密码不符合规范");

        user1.setPassword(PasswordUtil.encode(updateDTO.getNewPassword()));
        um.update(user1);
        exit(user1);
        return Result.success();
    }

    public void exit(User user) {
        um.updateTokenVersionById(user);
    }
}
