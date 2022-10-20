package com.slcube.jpaboard.dto.comment;

import com.slcube.jpaboard.domain.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentSaveRequestDto {

    @Schema(description = "게시글 Id")
    private Long boardId;

    @Schema(description = "작성자")
    private String author;

    @Schema(description = "내용")
    private String content;

    @Builder
    public CommentSaveRequestDto(Long boardId, String author, String content) {
        this.boardId = boardId;
        this.author = author;
        this.content = content;
    }

    public Comment toEntity() {
        return Comment.builder()
                .author(author)
                .content(content)
                .build();
    }
}
