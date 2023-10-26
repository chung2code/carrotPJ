package com.example.demo.domain.user.service;

import com.example.demo.domain.user.dto.ImageBoardDto;
import com.example.demo.domain.user.entity.ImageBoard;
import com.example.demo.domain.user.repository.ImageBoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ImageBoardService {

    private String imageBoardPath = "c:\\imageboard\\";

    @Autowired
    private ImageBoardRepository imageBoardRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    //list
    @Transactional(rollbackFor = Exception.class)
    public List<ImageBoard>getImageboardList(){
        return imageBoardRepository.findAll(Sort.by(Sort.Direction.DESC,"Id"));
    }

    //add
    @Transactional(rollbackFor = Exception.class)
    public void addImageBoard(ImageBoardDto dto)throws IOException {
        System.out.println("ImageBoardService's addImage..");

        //DB저장후 처리

        ImageBoard imageBoard = new ImageBoard();
        imageBoard.setTitle(dto.getTitle());
        imageBoard.setDetails(dto.getDetails());
        imageBoard.setPrice(dto.getPrice());
        imageBoard.setPlace(dto.getPlace());
        imageBoard.setUsername(dto.getUsername());
        imageBoard.setCreatedAt(dto.getCreatedAt());

        imageBoardRepository.save(imageBoard);
        System.out.println("저장확인 ID:" + imageBoard.getId());

        //저장 폴더 지정

        String uploadPath = imageBoardPath + File.separator + dto.getUsername() + File.separator + imageBoard.getId();
        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }


        //게시물당 파일은 5장까지


        List<String> boardlist = new ArrayList<>();

        for(MultipartFile files : dto.getFiles()){
            System.out.println("---------------------");
            System.out.println("FILE NAME:"+files.getOriginalFilename());
            System.out.println("FILE SIZE:" + files.getSize()+"Byte");
            System.out.println("----------------------");
            boardlist.add(files.getOriginalFilename());
            System.out.println(dto.getFiles());

            File fileobj = new File(uploadPath,files.getOriginalFilename());
            files.transferTo(fileobj);





            //thumbnail
            File thumbnailFile = new File(uploadPath,"s_"+files.getOriginalFilename());
            BufferedImage bo_image = ImageIO.read(fileobj);
            BufferedImage bt_image = new BufferedImage(300,500,BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D graphic = bt_image.createGraphics();
            graphic.drawImage(bo_image,0,0,300,500,null);
            ImageIO.write(bt_image,"jpa",thumbnailFile);


        }
        imageBoard.setFiles(boardlist);
        imageBoardRepository.save(imageBoard);




    }
//        List<String> boardlist = new ArrayList<>();
//
//// dto 객체와 getFiles() 메소드의 null 체크
//        if (dto != null && dto.getFiles() != null) {
//            for (MultipartFile files : dto.getFiles()) {
//                System.out.println("---------------------");
//                System.out.println("FILE NAME:" + files.getOriginalFilename());
//                System.out.println("FILE SIZE:" + files.getSize() + "Byte");
//                System.out.println("----------------------");
//                boardlist.add(files.getOriginalFilename());
//                System.out.println(dto.getFiles());
//
//                File fileobj = new File(uploadPath, files.getOriginalFilename());
//                files.transferTo(fileobj);
//
//                //thumbnail
//                File thumbnailFile = new File(uploadPath, "s_" + files.getOriginalFilename());
//                BufferedImage bo_image = ImageIO.read(fileobj);
//                BufferedImage bt_image = new BufferedImage(300, 500, BufferedImage.TYPE_3BYTE_BGR);
//                Graphics2D graphic = bt_image.createGraphics();
//                graphic.drawImage(bo_image, 0, 0, 300, 500, null);
//                ImageIO.write(bt_image, "jpa", thumbnailFile);
//            }
//            imageBoard.setFiles(boardlist);
//            imageBoardRepository.save(imageBoard);
//        } else {
//            // dto 객체 또는 getFiles() 메소드가 null인 경우 처리
//            System.out.println("Dto object or files are null");
//        }
//
//    }

    @Transactional(rollbackFor = Exception.class)
    public ImageBoard getImageboard(Long Id){
        Optional<ImageBoard> optionalImageBoard = imageBoardRepository.findById(Id);
        if (!optionalImageBoard.isEmpty()){
            return optionalImageBoard.get();
        }
        return  null;
    }

}
