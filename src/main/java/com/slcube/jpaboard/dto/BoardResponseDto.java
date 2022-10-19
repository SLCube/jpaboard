package com.slcube.jpaboard.dto;

import com.slcube.jpaboard.domain.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {

    private String title;
    private String content;
    private String author;
    private int viewCount;
    private LocalDateTime createdDate;


    public BoardResponseDto(Board board) {
        title = board.getTitle();
        content = board.getContent();
        author = board.getAuthor();
        viewCount = board.getViewCount();
        createdDate = board.getCreatedDate();
    }
}
