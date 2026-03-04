package fun.sterblich.blog.behindController;

import fun.sterblich.blog.pojo.Result;
import fun.sterblich.blog.utiles.AliOSSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/behind/upload")
public class UploadController {
    @Autowired
    AliOSSUtils aliOSSUtils;
    @PostMapping
    public Result upload(MultipartFile image){
        String str=null;
        try {
            str=aliOSSUtils.upload(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Result.success(str);
    }
}
