package com.slcube.jpaboard.dto.board;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardSearch {

    @Schema(description = "작성자")
    private String author;

    @Schema(description = "내용")
    private String content;

    @Builder
    public BoardSearch(String author, String content) {
        this.author = author;
        this.content = content;
    }
}
