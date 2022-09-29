package com.slcube.jpaboard.service;

import com.slcube.jpaboard.domain.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class BoardServiceTest {
    BoardService boardService = new BoardService(new FakeBoardRepository());

    Board board;

    @BeforeEach
    public void given() throws Exception {
        this.board = Board.builder()
                .title("test board title")
                .content("test board content")
                .writer("test board writer")
                .build();
    }

    @Test
    void 게시글_작성() throws Exception {
        //when
        Long boardId = boardService.register(board);

        //then
        Board findBoard = boardService.findBoard(boardId);
        assertThat(findBoard.getTitle()).isEqualTo("test board title");
        assertThat(findBoard.getContent()).isEqualTo("test board content");
        assertThat(findBoard.getWriter()).isEqualTo("test board writer");
        assertThat(findBoard.getViewCount()).isEqualTo(1);
    }

    @Test
    void 게시글_수정() throws Exception {
        //given
        Long boardId = boardService.register(board);

        Board findBoard = boardService.findBoard(boardId);
        findBoard.modifiedBoard("test board modified title", "test board modified content");

        Board findModifiedBoard = boardService.findBoard(boardId);

        assertThat(findModifiedBoard.getTitle()).isEqualTo("test board modified title");
        assertThat(findModifiedBoard.getContent()).isEqualTo("test board modified content");
    }
}