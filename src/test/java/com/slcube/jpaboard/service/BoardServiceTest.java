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
        Board board = new Board();
        board.setTitle("test board title");
        board.setContent("test board content");
        board.setWriter("test board writer");
        board.setViewCount(0);
        board.setDeleteYn("N");

        //when
        Long boardId = boardService.register(board);

        //then
        Board findBoard = boardService.findOne(boardId);
        assertThat(findBoard.getTitle()).isEqualTo("test board title");
        assertThat(findBoard.getContent()).isEqualTo("test board content");
        assertThat(findBoard.getWriter()).isEqualTo("test board writer");
    }
}