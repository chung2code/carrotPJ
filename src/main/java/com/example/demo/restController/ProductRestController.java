package com.example.demo.restController;

import com.example.demo.domain.user.dto.ReplyDto;
import com.example.demo.domain.user.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user/product")
public class ProductRestController {

    @Autowired
    private ProductService productService;

    //수정하기
    @PutMapping("/put")
    public void put(){
        log.info("PUT /user/product/put");
    }
    //삭제하기
    @DeleteMapping("delete")
    public void delete(){log.info("DELETE /user/product/");}


    //reply add
    @GetMapping("/reply/add")
    public void addReply(Long Pno,String contents,String username){
        log.info("GET /user/product/reply/add"+Pno+" " + contents + ""+username);
        productService.addReply(Pno,contents,username);

    }
    //reply list

    @GetMapping("/reply/list")
    public List<ReplyDto> getreplyList(Long Pno){
        log.info("GET /user/product/reply/list"+Pno);
        List<ReplyDto> list  = productService.getReplyList(Pno);

        return list;
    }

    //reply count

    @GetMapping("/reply/count")
    public Long getCount(Long Pno){
        log.info("GET /user/product/reply/count"+Pno);
        Long cnt = productService.getReplyCount(Pno);

        return cnt;
    }

}
