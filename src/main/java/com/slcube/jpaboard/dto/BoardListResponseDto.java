package com.slcube.jpaboard.dto;

import com.slcube.jpaboard.domain.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardListResponseDto {

    private String title;
    private String author;
    private int viewCount;
    private LocalDateTime createdDate;

    public BoardListResponseDto(Board board) {
        title = board.getTitle();
        author = board.getAuthor();
        viewCount = board.getViewCount();
        createdDate = board.getCreatedDate();
    }
}
