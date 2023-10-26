package com.example.demo.domain.user.dto;

import com.example.demo.domain.user.entity.Product;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ImageBoardDto {



    private Long Id;
    private String username;
    @NotBlank
    private String title;
    private String details;
    @NotBlank
    private String place;
    private String price;
    private MultipartFile[] files;



    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private Long count;




}