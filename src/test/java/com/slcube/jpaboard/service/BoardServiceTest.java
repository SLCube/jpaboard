package com.slcube.jpaboard.service;

import com.slcube.jpaboard.domain.Board;
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

    @Test
    void 게시글_작성() throws Exception {
        //given
        Board board = new Board("test board title", "test board content", "test board writer");

        //when
        Long boardId = boardService.register(board);

        //then
        Board findBoard = boardService.findOne(boardId);
        assertThat(findBoard.getTitle()).isEqualTo("test board title");
        assertThat(findBoard.getContent()).isEqualTo("test board content");
        assertThat(findBoard.getWriter()).isEqualTo("test board writer");
        assertThat(findBoard.getViewCount()).isEqualTo(1);
    }

    @Test
//    @Rollback(value = false)
    void 게시글_수정() throws Exception {
        //given
        Board board = new Board("test board title", "test board content", "test board writer");

        Long boardId = boardService.register(board);

        Board findBoard = boardService.findOne(boardId);
        findBoard.modifiedBoard("test board modified title", "test board modified content");

        Board findModifiedBoard = boardService.findOne(boardId);

        assertThat(findModifiedBoard.getTitle()).isEqualTo("test board modified title");
        assertThat(findModifiedBoard.getContent()).isEqualTo("test board modified content");
    }
}