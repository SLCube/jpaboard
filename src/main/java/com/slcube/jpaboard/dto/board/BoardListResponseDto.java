package com.slcube.jpaboard.dto.board;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardListResponseDto {

    private Long boardId;
    private String title;
    private String author;
    private int viewCount;
    private LocalDateTime createdDate;

    @QueryProjection
    public BoardListResponseDto(Long boardId, String title, String author, int viewCount, LocalDateTime createdDate) {
        this.boardId = boardId;
        this.title = title;
        this.author = author;
        this.viewCount = viewCount;
        this.createdDate = createdDate;
    }
}
