package com.slcube.jpaboard.repository;

import com.slcube.jpaboard.domain.Board;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    Board board;

    @BeforeEach
    public void given() {
        this.board = Board.builder()
                .title("test board title")
                .content("test board content")
                .writer("test board writer")
                .build();
    }

    @Test
    public void 게시글_작성() throws Exception {

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
    public void 게시글_수정() throws Exception {

        Long saveBoardId = boardRepository.save(board);

        Board findBoard = boardRepository.findOne(saveBoardId);

        //when
        findBoard.modifiedBoard("test board modified title", "test board modified content");

        //then
        Board findUpdateBoard = boardRepository.findOne(saveBoardId);
        assertThat(findUpdateBoard.getTitle()).isEqualTo("test board modified title");
        assertThat(findUpdateBoard.getContent()).isEqualTo("test board modified content");
    }

    @Test
    public void 게시글_삭제() throws Exception {

        Long boardId = boardRepository.save(board);

        //when
        Long deleteBoardId = boardRepository.remove(boardId);

        //then
        assertThatThrownBy(() -> boardRepository.findOne(deleteBoardId)).isInstanceOf(NoResultException.class);
    }
}