package com.example.demo.domain.user.dto;


import com.example.demo.domain.user.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long Pno;
    @NotBlank
    private String title;
    private String details;
    @NotBlank
    private String place;
    private String price;
    private String category;
    private String user;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reg_date;
    private Long count;
    private String dirPath;
    private String filename;
    private MultipartFile[] files;
    private boolean salesCompleted;



    public static ProductDto Of(Product product) {
     ProductDto dto = new ProductDto();

     dto.setPno(product.getPno());
     dto.setTitle(product.getTitle());
     dto.setDetails(product.getDetails());
     dto.setPlace(product.getPlace());
     dto.setCount(product.getCount());





        return dto;
    }






}











