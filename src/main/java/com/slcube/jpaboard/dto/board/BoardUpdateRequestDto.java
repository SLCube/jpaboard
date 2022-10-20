package com.slcube.jpaboard.dto.board;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardUpdateRequestDto {

    @Schema(description = "제목")
    private String title;

    @Schema(description = "내용")
    private String content;

    @Builder
    public BoardUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
