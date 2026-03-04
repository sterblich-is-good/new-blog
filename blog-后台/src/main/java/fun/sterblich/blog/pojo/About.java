package fun.sterblich.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class About {
    private Long id;
    private String tech;
    private Integer status;
}
