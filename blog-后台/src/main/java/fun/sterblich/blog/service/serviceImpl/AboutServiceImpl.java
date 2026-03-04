package fun.sterblich.blog.service.serviceImpl;

import fun.sterblich.blog.mapper.AboutMapper;
import fun.sterblich.blog.pojo.About;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AboutServiceImpl {
    @Autowired
    private AboutMapper am;

    public List<About> list(){
        return am.select();
    }

    public void insert(List<About> list){
        am.delete();
        for (About about : list) {
            am.insert(about);
        }
    }
}
