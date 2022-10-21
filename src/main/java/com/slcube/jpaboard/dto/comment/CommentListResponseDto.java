package com.slcube.jpaboard.dto.comment;

import com.slcube.jpaboard.domain.Comment;
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

    public CommentListResponseDto(Comment comment) {
        commentId = comment.getId();
        author = comment.getAuthor();
        content = comment.getContent();
        createdDate = comment.getCreatedDate();
    }
}
