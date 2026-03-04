package fun.sterblich.blog.utiles;

import fun.sterblich.blog.config.JwtProperties;
import fun.sterblich.blog.mapper.UserMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    private final JwtProperties jwtProperties;

    public JwtUtil(JwtProperties jwtProperties){
        this.jwtProperties=jwtProperties;
    }

    public String setJwt(Map<String,Object> claims){
        SecretKey key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
        String jwt= Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256) //密钥和算法
                .setClaims(claims) //载荷
                .setExpiration(new Date(System.currentTimeMillis()+jwtProperties.getExpire()*1000)) //有效期
                .compact();
        return jwt;
    }

    public Map<String,Object> parseJwt(String token) {
        Claims claims=Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes()))//指定密钥
                .parseClaimsJws(token)//解析令牌
                .getBody();//得到Body
        return claims;
    }
}
