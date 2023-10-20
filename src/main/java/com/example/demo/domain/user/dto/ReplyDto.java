package com.example.demo.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDto {

    private Long rno;
    private Long Pno;
    private String username;
    private String content;
    private Long likeCount;
    private LocalDateTime reg_Date;

    private String profileimage;

}
