package com.slcube.jpaboard.domain;

import com.slcube.jpaboard.dto.comment.CommentUpdateRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;

    private String author;

    @Enumerated(EnumType.STRING)
    private DeleteFlag deleteFlag = DeleteFlag.N;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    private Comment(String content, String author) {
        this.content = content;
        this.author = author;
    }

    public void update(CommentUpdateRequestDto requestDto) {
        content = requestDto.getContent();
    }

    public void delete() {
        deleteFlag = DeleteFlag.Y;
    }

    protected void addBoard(Board board) {
        this.board = board;
    }
}
