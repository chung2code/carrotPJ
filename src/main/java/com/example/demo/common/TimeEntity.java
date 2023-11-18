package com.example.demo.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@EntityListeners(AutoCloseable.class)
@MappedSuperclass
public class TimeEntity {


    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;
}
