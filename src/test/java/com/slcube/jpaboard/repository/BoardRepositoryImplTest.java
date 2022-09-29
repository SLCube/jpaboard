package com.slcube.jpaboard.repository;

import com.slcube.jpaboard.domain.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
class BoardRepositoryImplTest {

    @Autowired
    BoardRepositoryImpl boardRepositoryImpl;

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
    @DisplayName("게시글 작성")
    public void saveBoardTest() throws Exception {

        //when
        Long boardId = boardRepositoryImpl.save(board);

        //then
        Board findBoard = boardRepositoryImpl.findOne(boardId);
        assertThat(findBoard.getTitle()).isEqualTo("test board title");
        assertThat(findBoard.getContent()).isEqualTo("test board content");
        assertThat(findBoard.getWriter()).isEqualTo("test board writer");
        assertThat(findBoard.getViewCount()).isEqualTo(0);
        assertThat(findBoard.getDeleteYn()).isEqualTo("N");
    }

    @Test
    @DisplayName("게시글 수정")
    public void modifiedBoardTest() throws Exception {

        Long saveBoardId = boardRepositoryImpl.save(board);

        Board findBoard = boardRepositoryImpl.findOne(saveBoardId);

        //when
        findBoard.modifiedBoard("test board modified title", "test board modified content");

        //then
        Board findUpdateBoard = boardRepositoryImpl.findOne(saveBoardId);
        assertThat(findUpdateBoard.getTitle()).isEqualTo("test board modified title");
        assertThat(findUpdateBoard.getContent()).isEqualTo("test board modified content");
    }

    @Test
    @DisplayName("게시글 삭제")
    public void deleteBoardTest() throws Exception {

        Long boardId = boardRepositoryImpl.save(board);

        //when
        Long deleteBoardId = boardRepositoryImpl.remove(boardId);

        //then
        assertThatThrownBy(() -> boardRepositoryImpl.findOne(deleteBoardId)).isInstanceOf(NoResultException.class);
    }
}