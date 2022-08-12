package com.slcube.jpaboard.repository;

import com.slcube.jpaboard.domain.Board;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    public void 게시글_작성() throws Exception {
        //given
        Board board = new Board();
        board.setTitle("test board title");
        board.setContent("test board content");
        board.setWriter("test board writer");
        board.setDeleteYn("N");

        //when
        Long boardId = boardRepository.save(board);

        //then
//        assertThat(boardId).isEqualTo(board.getId());
        Board findBoard = boardRepository.findOne(boardId);
        assertThat(findBoard.getTitle()).isEqualTo(board.getTitle());
        assertThat(findBoard.getContent()).isEqualTo(board.getContent());
        assertThat(findBoard.getWriter()).isEqualTo(board.getWriter());
        assertThat(findBoard.getDeleteYn()).isEqualTo(board.getDeleteYn());
    }
}