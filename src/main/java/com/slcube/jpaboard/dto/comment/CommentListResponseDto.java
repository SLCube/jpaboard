package com.slcube.jpaboard.dto.comment;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentListResponseDto {

    @Schema(description = "댓글 Id")
    private Long commentId;

    @Schema(description = "작성자")
    private String author;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "작성일자")
    private LocalDateTime createdDate;

    @QueryProjection
    public CommentListResponseDto(Long commentId, String author, String content, LocalDateTime createdDate) {
        this.commentId = commentId;
        this.author = author;
        this.content = content;
        this.createdDate = createdDate;
    }
}
