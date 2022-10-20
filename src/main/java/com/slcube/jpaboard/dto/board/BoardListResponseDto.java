package com.slcube.jpaboard.dto.board;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardListResponseDto {

    @Schema(description = "게시글 Id")
    private Long boardId;

    @Schema(description = "제목")
    private String title;

    @Schema(description = "작성자")
    private String author;

    @Schema(description = "조회수")
    private int viewCount;

    @Schema(description = "작성일자")
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
