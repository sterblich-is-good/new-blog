package fun.sterblich.blog1;

import fun.sterblich.blog.utiles.PasswordUtil;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;

public class Test {
    @org.junit.jupiter.api.Test
    public void matches(){
        System.out.println(PasswordUtil.encode("123456"));
    }



    @org.junit.jupiter.api.Test
    public void genKey() {
        String key = Base64.getEncoder()
                .encodeToString(Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded());
        System.out.println(key);
    }


}
