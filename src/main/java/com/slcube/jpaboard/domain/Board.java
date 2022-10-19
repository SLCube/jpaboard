package com.slcube.jpaboard.domain;

import com.slcube.jpaboard.dto.BoardUpdateRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ColumnDefault(value = "0")
    private int viewCount;

    @Enumerated(EnumType.STRING)
    private DeleteFlag deleteFlag = DeleteFlag.N;

    @Builder
    private Board(Long id, String title, String author, String content) {
        this.id = id;
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
}
