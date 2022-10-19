package com.slcube.jpaboard.dto;

import com.slcube.jpaboard.domain.Board;
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

    public BoardListResponseDto(Board board) {
        boardId = board.getId();
        title = board.getTitle();
        author = board.getAuthor();
        viewCount = board.getViewCount();
        createdDate = board.getCreatedDate();
    }
}
