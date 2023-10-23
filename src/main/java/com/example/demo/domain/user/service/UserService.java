package com.example.demo.domain.user.service;

import com.example.demo.domain.user.dto.UserDto;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@Slf4j
public class UserService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User joinMember(UserDto dto, Model model) {
        // 패스워드 일치 여부 확인
        if (!dto.getPassword().equals(dto.getPasswordcheck())) {
            model.addAttribute("repassword", "패스워드가 일치하지 않습니다");
            return null;
        }

        dto.setRole("ROLE_USER");
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        User user = UserDto.dtoToEntity(dto);

        // DB에 사용자 정보 저장 후 결과 검증
        User savedUser = userRepository.save(user);

        if (savedUser == null || savedUser.getId() == null) {
            // TODO: 로그 출력 또는 예외 처리 등 필요한 작업 수행
            return null;
        }

        return savedUser;  // 성공적으로 저장된 사용자 정보 반환
    }


    //프로필업데이트
    public void updateProfile(UserDto dto){
        User user = UserDto.dtoToEntity(dto);

        userRepository.save(user);
    }
}
