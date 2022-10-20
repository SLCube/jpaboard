package com.slcube.jpaboard.dto.board;

import com.slcube.jpaboard.domain.Board;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardSaveRequestDto {

    @Schema(description = "제목")
    private String title;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "작성자")
    private String author;

    @Builder
    public BoardSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
