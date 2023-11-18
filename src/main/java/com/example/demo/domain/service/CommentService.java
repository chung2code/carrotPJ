package com.example.demo.domain.service;

import com.example.demo.domain.dto.CommentDto;
import com.example.demo.domain.entity.Comment;
import com.example.demo.domain.entity.ImageBoard;
import com.example.demo.domain.repository.CommentRepository;
import com.example.demo.domain.repository.ImageBoardRepository;
import com.example.demo.listener.CommentEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ImageBoardRepository imageBoardRepository;

    @Autowired
    private ApplicationEventPublisher publisher;



    // REPLY ADD
    //----------------------------------------------------------------
    public void commemtWrite(Long bno,String content, String username,String profileimage) {
        Comment commemt = new Comment();
       ImageBoard imageBoard = new ImageBoard();
        imageBoard.setId(bno);

        commemt.setImageBoard(imageBoard);
        commemt.setContent(content);
        commemt.setUsername(username);

        commemt.setLikecount(0L);

        commemt.setProfileimage(profileimage);

        commentRepository.save(commemt);


    }

    //----------------------------------------------------------------
    // REPLY LIST
    //----------------------------------------------------------------
    public List<CommentDto> getReplyList(Long bno) {
       List<Comment> commentList = commentRepository.GetCommentByBnoDesc(bno);

        List<CommentDto> returnReply  = new ArrayList();
        CommentDto dto = null;

        if(!commentList.isEmpty()) {
            for(int i=0;i<commentList.size();i++) {

                dto = new CommentDto();
                dto.setBno(commentList.get(i).getImageBoard().getId());
                dto.setRno(commentList.get(i).getRno());
                dto.setUsername(commentList.get(i).getUsername());
                dto.setContent(commentList.get(i).getContent());
                dto.setLikecount(commentList.get(i).getLikecount());


                dto.setProfileimage(commentList.get(i).getProfileimage());

                returnReply.add(dto);

            }
            return returnReply;
        }

        return null;

    }


    //----------------------------------------------------------------
    // REPLY COUNT By BNO
    //----------------------------------------------------------------

    public Long getReplyCount(Long bno) {
        return commentRepository.GetCommentCountByBnoDesc(bno);

    }


    public void deleteReply(Long rno) {
        commentRepository.deleteById(rno);
    }

    public void thumbsUp(Long rno) {
        Comment comment =  commentRepository.findById(rno).get();
        comment.setLikecount(comment.getLikecount()+1L);
        commentRepository.save(comment);
    }


}
