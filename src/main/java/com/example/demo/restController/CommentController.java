//package com.example.demo.restController;
//
//import com.example.demo.domain.dto.CommentDto;
//import com.example.demo.domain.entity.Comment;
//import com.example.demo.domain.entity.ImageBoard;
//import com.example.demo.domain.repository.CommentRepository;
//import com.example.demo.domain.service.CommentService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@Slf4j
//@RequiredArgsConstructor
//public class CommentController {
//
//
//    @Autowired
//    private CommentService commentService;
//
//
//    //-------------------
//    //댓글추가
//    //-------------------
//    @GetMapping("/reply/add")
//    public void addReply(Long bno,String contents , String username,String profileimage){
//        log.info("GET /board/reply/add " + bno + " " + contents + " " + username);
//        commentService.commemtWrite(bno,contents, username,profileimage);
//    }
//    //-------------------
//    //댓글 조회
//    //-------------------
//    @GetMapping("/reply/list")
//    public List<CommentDto> getListReply(Long bno){
//        log.info("GET /board/reply/list " + bno);
//        List<CommentDto> list =  commentService.getReplyList(bno);
//        return list;
//    }
//    //-------------------
//    //댓글 카운트
//    //-------------------
//    @GetMapping("/reply/count")
//    public Long getCount(Long bno){
//        log.info("GET /board/reply/count " + bno);
//        Long cnt = commentService.getReplyCount(bno);
//
//        return cnt;
//    }
//}
