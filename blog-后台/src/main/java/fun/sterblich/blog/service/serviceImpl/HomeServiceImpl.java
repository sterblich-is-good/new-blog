package fun.sterblich.blog.service.serviceImpl;

import fun.sterblich.blog.mapper.HomeMapper;
import fun.sterblich.blog.pojo.Home;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl {
    @Autowired
    private HomeMapper hm;

    public Home select(){
        return hm.select();
    }

    public void update(Home home){
        hm.update(home);
    }
}
