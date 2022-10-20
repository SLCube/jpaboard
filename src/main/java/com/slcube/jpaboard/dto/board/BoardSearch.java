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

    @Schema(description = "제목(Nullable)")
    private String title;
    @Schema(description = "작성자(Nullable)")
    private String author;

    @Schema(description = "내용(Nullable)")
    private String content;

    @Builder
    public BoardSearch(String title, String author, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
    }
}
