package fun.sterblich.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Articles {
    private Integer id;
    private String title;
    private String time;
    private Integer year;
    private String desc;
    private String text;
    private Integer archiveId;
    private Integer status;
}
