package com.example.demo.listener;

import com.example.demo.domain.entity.Comment;
import org.springframework.context.ApplicationEvent;

public class CommentEvent extends ApplicationEvent {

    private Comment comment;


    public CommentEvent(Object source,Comment comment) {
        super(source);
        this.comment = comment;
    }

    public Comment getComment(){
        return comment;
    }

    @Override
    public String toString() {
        return "CommentEvent{" +
                "comment=" + comment +
                '}';
    }
}
