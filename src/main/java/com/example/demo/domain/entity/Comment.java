package com.example.demo.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Comment  {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long rno;
    @ManyToOne
    @JoinColumn(name = "bno",foreignKey = @ForeignKey(name = "FK_reply_board",
            foreignKeyDefinition = "FOREIGN KEY (bno) REFERENCES ImageBoard(id) ON DELETE CASCADE ON UPDATE CASCADE") ) //FK설정\
    private ImageBoard imageBoard;
    private String username;
    private String content;
    private Long likecount;       //좋아요 Count

    private LocalDateTime createAt;  // 등록날자

    private String profileimage;










}
