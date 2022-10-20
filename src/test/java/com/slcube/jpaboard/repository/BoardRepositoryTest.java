package com.slcube.jpaboard.repository;

import com.slcube.jpaboard.domain.Board;
import com.slcube.jpaboard.dto.board.BoardSaveRequestDto;
import com.slcube.jpaboard.dto.board.BoardUpdateRequestDto;
import com.slcube.jpaboard.repository.board.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    Board board;

    @BeforeEach
    void beforeEach() {
        board = BoardSaveRequestDto.builder()
                .title("test board title")
                .content("test board content")
                .author("test board author")
                .build()
                .toEntity();
        boardRepository.save(board).getId();
    }

    @Test
    @DisplayName("게시글 저장")
    void saveTest() {

        // when
        Long saveId = board.getId();

        // then
        Board findBoard = boardRepository.findOne(saveId).get();

        assertThat(findBoard).isEqualTo(board);
    }

    @Test
    @DisplayName("게시글 삭제")
    void deleteTest() {
        // given
        Long saveId = board.getId();
        Board findBoard = boardRepository.findOne(saveId).get();

        // when
        findBoard.delete();

        // then
        assertThatThrownBy(() -> boardRepository.findOne(findBoard.getId()).get()).isInstanceOf(new NoSuchElementException().getClass());
    }

    @Test
    @DisplayName("게시글 수정")
    void updateTest() {
        Long saveId = board.getId();

        Board findBoard = boardRepository.findOne(saveId).get();

        BoardUpdateRequestDto updateRequestDto = BoardUpdateRequestDto.builder()
                .title("test board modified title")
                .content("test board modified content")
                .build();
        findBoard.update(updateRequestDto);

        Board modifiedBoard = boardRepository.findOne(saveId).get();

        assertThat(modifiedBoard.getTitle()).isEqualTo(updateRequestDto.getTitle());
        assertThat(modifiedBoard.getContent()).isEqualTo(updateRequestDto.getContent());
    }
}
