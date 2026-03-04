package fun.sterblich.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Archive {
    private Integer id;
    private Integer year;
    private ArrayList<Articles> articlesList;
}