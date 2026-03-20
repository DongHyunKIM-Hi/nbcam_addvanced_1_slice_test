package org.example.nbcam_addvanced_1.domain.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.nbcam_addvanced_1.common.entity.User;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private long id;
    private String username;
    private String email;
    private int age;


    public static UserDto from (User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getAge());
    }

}
