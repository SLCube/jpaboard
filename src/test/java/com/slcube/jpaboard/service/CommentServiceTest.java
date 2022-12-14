package com.slcube.jpaboard.service;

import com.slcube.jpaboard.dto.board.BoardSaveRequestDto;
import com.slcube.jpaboard.dto.comment.CommentListResponseDto;
import com.slcube.jpaboard.dto.comment.CommentSaveRequestDto;
import com.slcube.jpaboard.dto.comment.CommentUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BoardService boardService;

    private Long boardId;

    @BeforeEach
    void beforeEach() {
        BoardSaveRequestDto requestDto = BoardSaveRequestDto.builder()
                .title("test board title")
                .content("test board content")
                .author("test board author")
                .build();

        boardId = boardService.save(requestDto);
    }

    @Test
    void saveTest() {

        String content = "test comment content";
        String author = "test comment author";
        for (int i = 0; i < 11; i++) {
            CommentSaveRequestDto requestDto = CommentSaveRequestDto.builder()
                    .boardId(boardId)
                    .author(author + (i + 1))
                    .content(content + (i + 1))
                    .build();
            commentService.save(requestDto);
        }
        Long commentId = 11L;


        PageRequest pageRequest = PageRequest.of(0, 10);

        List<CommentListResponseDto> comments = commentService.findAllDesc(boardId, pageRequest).getContent();
        CommentListResponseDto comment = comments.get(0);

        assertThat(comment.getCommentId()).isEqualTo(commentId);
        assertThat(comment.getContent()).isEqualTo(content + commentId);
        assertThat(comment.getAuthor()).isEqualTo(author + commentId);
        assertThat(comments.size()).isEqualTo(10);
    }

    @Test
    void updateTest() {
        CommentSaveRequestDto requestDto = CommentSaveRequestDto.builder()
                .boardId(boardId)
                .author("test comment author")
                .content("test comment content")
                .build();

        Long commentId = commentService.save(requestDto);

        CommentUpdateRequestDto updateRequestDto = new CommentUpdateRequestDto("test comment modified content");
        Long updateId = commentService.update(commentId, updateRequestDto);

        PageRequest pageRequest = PageRequest.of(0, 10);
        CommentListResponseDto responseDto = commentService.findAllDesc(boardId, pageRequest).getContent().get(0);

        assertThat(responseDto.getContent()).isEqualTo(updateRequestDto.getContent());
    }

    @Test
    void deleteTest() {
        CommentSaveRequestDto requestDto = CommentSaveRequestDto.builder()
                .boardId(boardId)
                .author("test comment author")
                .content("test comment content")
                .build();

        Long commentId = commentService.save(requestDto);

        Long deleteId = commentService.delete(commentId);

        PageRequest pageRequest = PageRequest.of(0, 10);
        List<CommentListResponseDto> result = commentService.findAllDesc(boardId, pageRequest).getContent();
        assertThat(result.size()).isEqualTo(0);
    }

    private Long saveComment() {
        CommentSaveRequestDto requestDto = CommentSaveRequestDto.builder()
                .boardId(boardId)
                .author("test comment author")
                .content("test comment content")
                .build();

        return commentService.save(requestDto);
    }
}
