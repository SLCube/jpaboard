package com.slcube.jpaboard.repository;

import com.slcube.jpaboard.domain.Board;
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

    @Test
    public void 게시글_작성() throws Exception {
        //given
        Board board = new Board("test board title", "test board content", "test board writer");

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

//    @Test
//    public void 게시글_수정() throws Exception {
//        //given
//        Board board = new Board("test board title", "test board content", "test board writer");
//
//        Long saveBoardId = boardRepository.save(board);
//
//        Board findBoard = boardRepository.findOne(saveBoardId);
//        findBoard.setTitle("test board modified title");
//        findBoard.setContent("test board modified content");
//
//        //when
//        Long updateBoardId = boardRepository.update(findBoard);
//
//        //then
//        Board findUpdateBoard = boardRepository.findOne(updateBoardId);
//        assertThat(findUpdateBoard.getTitle()).isEqualTo("test board modified title");
//        assertThat(findUpdateBoard.getContent()).isEqualTo("test board modified content");
//    }

    @Test
    public void 게시글_삭제() throws Exception {
        //given
        Board board = new Board("test board title", "test board content", "test board writer");

        Long boardId = boardRepository.save(board);

        //when
        Long deleteBoardId = boardRepository.remove(boardId);

        //then
        assertThatThrownBy(() -> boardRepository.findOne(deleteBoardId)).isInstanceOf(NoResultException.class);
    }
}