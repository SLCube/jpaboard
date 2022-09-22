package com.slcube.jpaboard.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content;
    private String writer;
    private String deleteYn;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @Builder
    private Comment(String content, String writer, Board board) {
        this.content = content;
        this.writer = writer;
        this.deleteYn = "N";
        this.createdDate = LocalDateTime.now();
    }

    public static Comment createComment(String content, String writer, Board board) {
        Comment comment = Comment.builder()
                .content(content)
                .writer(writer)
                .board(board)
                .build();
        
        return comment;
    }
}
