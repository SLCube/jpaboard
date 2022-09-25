package com.slcube.jpaboard.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

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

    public Long deleteComment() {
        this.deleteYn = "Y";
        return this.getId();
    }

    public Long modifiedComment(String content) {
        this.content = content;
        this.modifiedDate = LocalDateTime.now();
        return this.getId();
    }
}
