package com.example.demo.domain.repository;

import com.example.demo.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("SELECT c FROM Comment c WHERE bno = :bno ORDER BY rno DESC")
    List<Comment> GetCommentByBnoDesc(@Param("bno") Long bno);

    @Query("SELECT COUNT(c) FROM Comment c WHERE bno = :bno")
    Long GetCommentCountByBnoDesc(@Param("bno") Long bno);


}
