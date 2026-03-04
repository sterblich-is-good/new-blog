package fun.sterblich.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordUpdateDTO {
    private String oldPassword; // 旧密码
    private String newPassword; // 新密码
    private String confirmPassword; // 确认密码
}
