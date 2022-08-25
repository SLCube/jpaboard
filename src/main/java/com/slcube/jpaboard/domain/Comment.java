package com.slcube.jpaboard.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    private Comment(String content, String writer, Board board) {
        this.content = content;
        this.writer = writer;
        this.deleteYn = "N";
        this.createdDate = LocalDateTime.now();
    }

    public static Comment createComment(String content, String writer, Board board) {
        Comment comment = new Comment(content, writer, board);

        return comment;
    }
}
