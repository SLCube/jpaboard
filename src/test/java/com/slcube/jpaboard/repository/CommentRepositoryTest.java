package com.slcube.jpaboard.repository;

import com.slcube.jpaboard.domain.Board;
import com.slcube.jpaboard.domain.Comment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    Comment comment;

    @BeforeEach
    void given() throws Exception {
        this.comment = Comment.builder()
                .content("test comment content")
                .writer("test comment writer")
                .build();
    }

    @Test
    @DisplayName("댓글 등록")
    void saveCommentTest() throws Exception {
        //when
        Long commentId = commentRepository.save(comment);

        //then
        Comment findComment = commentRepository.findOne(commentId);
        assertThat(findComment.getContent()).isEqualTo("comment test content");
        assertThat(findComment.getWriter()).isEqualTo("comment test writer");
    }

    @Test
    @DisplayName("댓글 수정")
    void modifiedCommentTest() {
        Long commentId = commentRepository.save(comment);

        Comment findComment = commentRepository.findOne(commentId);
        findComment.modifiedComment("test comment modified content");


    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteCommentTest() {

    }
}