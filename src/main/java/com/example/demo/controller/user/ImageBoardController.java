package com.example.demo.controller.user;

import com.example.demo.config.auth.PrincipalDetails;
import com.example.demo.domain.user.dto.ImageBoardDto;
import com.example.demo.domain.user.entity.ImageBoard;
import com.example.demo.domain.user.service.ImageBoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/user/product")
public class ImageBoardController {

    @Autowired
    private ImageBoardService imageBoardService;

    @GetMapping("list")
    public void f1(String reset, Model model) {
        log.info("GET /user/product/list");
        List<ImageBoard> list = imageBoardService.getImageboardList();
        System.out.println(list);
        list.stream().forEach(item-> System.out.println(item));

        model.addAttribute("list",list);
    }

    @GetMapping("/add")
    public void f2(){

    }
    @PostMapping("/add")
    public ResponseEntity<?> f2_post(
            @RequestParam("title") String title,
            @RequestParam("details") String details,
            @RequestParam("price") String price,
            @RequestParam("place") String place,
            @RequestParam("files") List<MultipartFile> files,
            Authentication authentication) throws IOException {
        log.info("POST /user/product/add");

        ImageBoardDto dto = new ImageBoardDto();
        dto.setTitle(title);
        dto.setDetails(details);
        dto.setPrice(price);
        dto.setPlace(place);


        if (!files.isEmpty()) {
            // If the file list is not empty, add it to the DTO
            dto.setFiles(files);
        }

        dto.setCreatedAt(LocalDateTime.now());

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        if (principal != null) {
            dto.setUsername(principal.getUsername());
            // ... call the service method to save the data
            imageBoardService.addImageBoard(dto);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @GetMapping("/read")
    public void f3(Long id,Model model){
        System.out.println("GET/user/product/read.."+id);
        ImageBoard imageBoard = imageBoardService.getImageboard(id);

        System.out.println(imageBoard);
        List<String> files = imageBoard.getFiles();
        System.out.println(files);
        model.addAttribute("imageBoard",imageBoard);
        model.addAttribute("files",files);
    }
}
