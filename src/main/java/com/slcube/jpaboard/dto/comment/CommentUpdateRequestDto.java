package com.slcube.jpaboard.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateRequestDto {

    @Schema(description = "내용")
    private String content;

    @Builder
    public CommentUpdateRequestDto(String content) {
        this.content = content;
    }
}
