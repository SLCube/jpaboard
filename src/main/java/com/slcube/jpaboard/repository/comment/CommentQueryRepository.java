package com.slcube.jpaboard.repository.comment;

import com.slcube.jpaboard.domain.Comment;
import com.slcube.jpaboard.dto.comment.CommentListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CommentQueryRepository {

    Optional<Comment> findOne(Long commentId);

    Page<CommentListResponseDto> findAllDesc(Long boardId, Pageable pageable);
}
