package com.example.demo.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;
    @ManyToOne
    @JoinColumn(name="pno",foreignKey = @ForeignKey(name = "FK_reply_product",
    foreignKeyDefinition = "FOREIGN KEY(pno)) REFERENCES product(Pno) ON DELETE CASCADE ON UPDATE CASCADE"))//FK setting
    private Product product;
    private String username;
    private String content;
    private Long likeCount;
    private LocalDateTime reg_date;

    private String profileimage;

}
