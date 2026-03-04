package fun.sterblich.blog.filter;

import com.alibaba.fastjson.JSONObject;
import fun.sterblich.blog.pojo.Result;
import fun.sterblich.blog.pojo.User;
import fun.sterblich.blog.utiles.JwtUtil;
import fun.sterblich.blog.utiles.PasswordUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;

@WebFilter(urlPatterns = "/behind/*")
public class BackgroundFilter implements Filter {

    private final JwtUtil jwtUtil;
    private final PasswordUtil passwordUtil;

    public BackgroundFilter(JwtUtil jwtUtil,PasswordUtil passwordUtil){
        this.jwtUtil=jwtUtil;
        this.passwordUtil=passwordUtil;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;
        //1.获取请求路径
        String url = request.getRequestURL().toString();

        //2.判断是否是登录请求
        if(url.contains("login")){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        //3.获取请求头token
        String token = request.getHeader("token");

        //4.判断是否有token
        if(!StringUtils.hasLength(token)){
            Result error=Result.error("NOT_LOGIN");
            //手动转成json
            String notLogin = JSONObject.toJSONString(error);
            //返回json
            response.getWriter().write(notLogin);
            return;
        }

        //5.存入token中的body
        Map<String,Object> body;

        //6.解析token
        try {
            body=jwtUtil.parseJwt(token);
        } catch (Exception e) {
            Result error=Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            return;
        }

        User user=new User();
        user.setId((Integer) body.get("id"));
        user.setUsername((String) body.get("username"));
        user.setTokenVersion((Integer) body.get("tokenVersion"));

        //7.验证密码版本如果不是一个版本就立即返回错误
        if (!passwordUtil.isVersion(user.getId(),user.getTokenVersion())) {
            Result error=Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            return;
        }

        //8.把user在存到request
        request.setAttribute("user",user);

        //9.放行
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
