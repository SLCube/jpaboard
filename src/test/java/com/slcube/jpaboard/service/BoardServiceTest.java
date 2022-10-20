package com.slcube.jpaboard.service;


import com.slcube.jpaboard.dto.board.BoardResponseDto;
import com.slcube.jpaboard.dto.board.BoardSaveRequestDto;
import com.slcube.jpaboard.dto.board.BoardUpdateRequestDto;
import com.slcube.jpaboard.exception.BoardNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class BoardServiceTest {

    @Autowired
    BoardService boardService;

    BoardSaveRequestDto saveRequestDto;

    @BeforeEach
    void beforeEach() {
        saveRequestDto = BoardSaveRequestDto.builder()
                .title("test board title")
                .content("test board content")
                .author("test board author")
                .build();
    }

    @Test
    @DisplayName("게시글 저장 및 조회")
    void saveTest() {
        Long saveId = save(saveRequestDto);

        BoardResponseDto responseDto = boardService.findById(saveId);

        assertThat(responseDto.getTitle()).isEqualTo(saveRequestDto.getTitle());
        assertThat(responseDto.getContent()).isEqualTo(saveRequestDto.getContent());
        assertThat(responseDto.getAuthor()).isEqualTo(saveRequestDto.getAuthor());
        assertThat(responseDto.getViewCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("게시글 삭제")
    void deleteTest() {
        Long saveId = save(saveRequestDto);

        Long deletedId = boardService.delete(saveId);

        assertThatThrownBy(() -> boardService.findById(deletedId))
                .isInstanceOf(new BoardNotFoundException().getClass())
                .hasMessage("해당 게시글이 없습니다. id = " + deletedId);
    }

    @Test
    @DisplayName("게시글 수정")
    void updateTest() {
        Long saveId = save(saveRequestDto);

        BoardUpdateRequestDto updateRequestDto = BoardUpdateRequestDto.builder()
                .title("test board modified title")
                .content("test board modified content")
                .build();

        Long updateId = boardService.update(saveId, updateRequestDto);

        BoardResponseDto responseDto = boardService.findById(updateId);

        assertThat(responseDto.getTitle()).isEqualTo(updateRequestDto.getTitle());
        assertThat(responseDto.getContent()).isEqualTo(updateRequestDto.getContent());
    }

    private Long save(BoardSaveRequestDto saveRequestDto) {
        return boardService.save(saveRequestDto);
    }
}