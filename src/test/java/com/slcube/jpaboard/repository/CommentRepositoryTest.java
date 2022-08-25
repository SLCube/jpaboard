package com.slcube.jpaboard.repository;

import com.slcube.jpaboard.domain.Board;
import com.slcube.jpaboard.domain.Comment;
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
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BoardRepository boardRepository;

    @Test
    void 댓글_등록() throws Exception {
        //given
        Board board = Board.builder()
                .title("test board title")
                .content("test board content")
                .writer("test board writer")
                .build();

        Long boardId = boardRepository.save(board);
        Board findBoard = boardRepository.findOne(boardId);

        Comment comment = Comment.createComment("comment test content", "comment test writer", findBoard);

        //when
        Long commentId = commentRepository.save(comment);

        //then
        Comment findComment = commentRepository.findOnd(commentId);
        assertThat(findComment.getContent()).isEqualTo("comment test content");
        assertThat(findComment.getWriter()).isEqualTo("comment test writer");
    }
}