package com.example.demo.domain.user.dto;

import com.example.demo.domain.user.entity.User;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UserDto {
    @NotBlank(message = "Id을 입력하세요")
    @Email(message = "올바른 이메일 주소를 입력하세요")
    private String Id;

    @NotBlank(message = "password를 입력하세요")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @NotBlank(message = "password를 다시 입력하세요")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String repassword;
    private String role;
    private String phone;
    private String NickName;



    //OAUTH2
    private String provider;
    private String providerId;

    public static User dtoToEntity(UserDto dto){
        User user = User.builder()
                .Id(dto.getId())
                .password(dto.getPassword())
                .role(dto.getRole())
                .phone(dto.getPhone())

                .build();
        return user;
    }
}
