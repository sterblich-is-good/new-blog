package fun.sterblich.blog.utiles;

import fun.sterblich.blog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PasswordUtil {

    @Autowired
    UserMapper um;

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()])[A-Za-z\\d!@#$%^&*()]{8,16}$"
    );

    // 单例即可，线程安全
    private static final PasswordEncoder encoder = new BCryptPasswordEncoder(10);

    //明文密码 → 密文（用于存数据库）
    public static String encode(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    //校验密码（登录用）
    public static boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }

    //验证密码是否符合规范
    public static boolean isValid(String password){
        if (password == null) {
            return false;
        }
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    //查询tokenVersion
    public boolean isVersion(Integer id, Integer version){
        return version.equals(um.selectTokenVersionById(id));
    }
}

