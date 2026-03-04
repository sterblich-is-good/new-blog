package fun.sterblich.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@ServletComponentScan//当前项目已经开启对servlet的支持
@SpringBootApplication
public class Blog1Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Blog1Application.class, args);
    }

}
