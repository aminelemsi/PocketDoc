package pocketDock.com.pocketDock.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pocketDock.com.pocketDock.entity.OurUsers;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserDto {

    private Integer id;
    private String name;
    private String email;
    private String password;

    public static UserDto fromEntity (OurUsers user){
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public static OurUsers toEntity (UserDto user){
        return OurUsers.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

}
