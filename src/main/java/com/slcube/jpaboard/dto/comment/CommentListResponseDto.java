package com.slcube.jpaboard.dto.comment;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentListResponseDto {

    private Long commentId;
    private String author;
    private String content;
    private LocalDateTime createdDate;

    @QueryProjection
    public CommentListResponseDto(Long commentId, String author, String content, LocalDateTime createdDate) {
        this.commentId = commentId;
        this.author = author;
        this.content = content;
        this.createdDate = createdDate;
    }
}
