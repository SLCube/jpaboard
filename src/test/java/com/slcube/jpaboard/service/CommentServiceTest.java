package com.slcube.jpaboard.service;

import com.slcube.jpaboard.domain.Board;
import com.slcube.jpaboard.domain.Comment;
import com.slcube.jpaboard.repository.BoardRepository;
import com.slcube.jpaboard.repository.CommentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @Autowired
    BoardService boardService;

    Board board;

    Comment comment;
    private Long boardId;

    private Long commentId;

    @BeforeEach
    void given() throws Exception {
        board = Board.builder()
                .title("board test title")
                .content("board test content")
                .writer("board test writer")
                .build();

        boardId = boardService.register(board);

        comment = Comment.builder()
                .content("comment test title")
                .writer("comment test writer")
                .build();

        commentId = commentService.register(boardId, comment);
    }

    @DisplayName("댓글 등록")
    @Test
    void registerCommentTest() throws Exception {


        List<Comment> comments = commentService.findComments(boardId);

        assertThat(comments).contains(comment);
    }

    @DisplayName("댓글 수정")
    @Test
    void modifiedCommentTest() throws Exception {
        commentService.modifiedComment(commentId, "test comment modified content");

        List<Comment> comments = commentService.findComments(boardId);
        assertThat(comments).contains(comment);
    }

    @DisplayName("댓글 삭제")
    @Test
    void deleteCommentTest() {
        commentService.deleteComment(this.commentId);

        List<Comment> comments = commentService.findComments(boardId);
        assertThat(comments).isNullOrEmpty();
    }
}