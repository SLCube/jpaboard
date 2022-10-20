package com.slcube.jpaboard.dto.board;

import com.slcube.jpaboard.domain.Board;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardResponseDto {

    @Schema(description = "제목")
    private String title;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "작성자")
    private String author;

    @Schema(description = "조회수")
    private int viewCount;

    @Schema(description = "작성일자")
    private LocalDateTime createdDate;


    public BoardResponseDto(Board board) {
        title = board.getTitle();
        content = board.getContent();
        author = board.getAuthor();
        viewCount = board.getViewCount();
        createdDate = board.getCreatedDate();
    }
}
