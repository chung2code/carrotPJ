package com.example.demo.domain.user.repository;

import com.example.demo.domain.user.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply,Long> {

    @Query("SELECT r FROM Reply r WHERE Pno = :pno ORDER BY rno DESC")
    List<Reply> GetReplyByBnoDesc(@Param("pno") Long pno);

    @Query("SELECT COUNT(r) FROM Reply r WHERE Pno = :pno")
    Long GetReplyCountByBnoDesc(@Param("pno") Long pno);
}
