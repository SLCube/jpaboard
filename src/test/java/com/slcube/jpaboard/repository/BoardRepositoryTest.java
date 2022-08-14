package com.slcube.jpaboard.repository;

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
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    public void 게시글_작성() {
        //given
        Board board = new Board();
        board.setTitle("test board title");
        board.setContent("test board content");
        board.setWriter("test board writer");
        board.setDeleteYn("N");

        //when
        Long boardId = boardRepository.save(board);

        //then
        Board findBoard = boardRepository.findOne(boardId);
        assertThat(findBoard.getTitle()).isEqualTo("test board title");
        assertThat(findBoard.getContent()).isEqualTo("test board content");
        assertThat(findBoard.getWriter()).isEqualTo("test board writer");
        assertThat(findBoard.getViewCount()).isEqualTo(0);
        assertThat(findBoard.getDeleteYn()).isEqualTo("N");
    }

    @Test
    public void 게시글_삭제(){
        //given
        Board board = new Board();
        board.setTitle("test board title");
        board.setContent("test board content");
        board.setWriter("test board writer");
        board.setViewCount(0);
        board.setDeleteYn("N");

        Long boardId = boardRepository.save(board);

        //when
        Long deleteBoardId = boardRepository.remove(boardId);

        Board findBoard = boardRepository.findOne(deleteBoardId);

        //then
        assertThat(findBoard.getDeleteYn()).isEqualTo("Y");
    }
}