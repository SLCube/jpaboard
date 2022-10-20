package com.slcube.jpaboard.dto.comment;

import com.slcube.jpaboard.domain.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentSaveRequestDto {

    private Long boardId;
    private String author;
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
