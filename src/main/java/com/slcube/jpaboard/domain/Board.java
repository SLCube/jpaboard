package com.slcube.jpaboard.domain;

import com.slcube.jpaboard.dto.board.BoardUpdateRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(length = 3000, nullable = false)
    private String content;

    @ColumnDefault(value = "0")
    private int viewCount;

    @Enumerated(EnumType.STRING)
    private DeleteFlag deleteFlag = DeleteFlag.N;

    @OneToMany(mappedBy = "board", cascade = CascadeType.PERSIST)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    private Board(String title, String author, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
    }

    public void update(BoardUpdateRequestDto requestDto) {
        title = requestDto.getTitle();
        content = requestDto.getContent();
    }

    public void delete() {
        deleteFlag = DeleteFlag.Y;
    }

    public void plusViewCount() {
        viewCount++;
    }

    public void addComment(Comment comment) {
        this.getComments().add(comment);
        comment.addBoard(this);
    }
}
