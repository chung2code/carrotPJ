package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {


    private Long rno;
    private Long bno;
    private String username;
    private String content;
    private Long likecount;       //좋아요 Count
    private LocalDateTime createAt;  // 등록날자
    private String profileimage;




}
