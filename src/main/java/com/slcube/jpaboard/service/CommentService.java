package com.slcube.jpaboard.service;

import com.slcube.jpaboard.domain.Board;
import com.slcube.jpaboard.domain.Comment;
import com.slcube.jpaboard.dto.comment.CommentListResponseDto;
import com.slcube.jpaboard.dto.comment.CommentSaveRequestDto;
import com.slcube.jpaboard.dto.comment.CommentUpdateRequestDto;
import com.slcube.jpaboard.exception.BoardNotFoundException;
import com.slcube.jpaboard.repository.board.BoardRepository;
import com.slcube.jpaboard.repository.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public Long save(CommentSaveRequestDto requestDto) {
        Board board = boardRepository.findOne(requestDto.getBoardId())
                .orElseThrow(() -> new BoardNotFoundException("해당 게시글이 없습니다. id = " + requestDto.getBoardId()));
        Comment comment = requestDto.toEntity();
        board.addComment(comment);
        return comment.getId();
    }

    public Long update(Long commentId, CommentUpdateRequestDto requestDto) {
        Comment comment = commentRepository.findOne(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. id = " + commentId));

        comment.update(requestDto);
        return commentId;
    }

    @Transactional(readOnly = true)
    public Page<CommentListResponseDto> findAllDesc(Long boardId, Pageable pageable) {
        return commentRepository.findAllDesc(boardId, pageable);
    }

    public Long delete(Long commentId) {
        Comment comment = commentRepository.findOne(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. id = " + commentId));
        comment.delete();

        return commentId;
    }
}
