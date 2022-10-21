package com.slcube.jpaboard.repository.comment;

import com.slcube.jpaboard.domain.Comment;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CommentQueryRepository {

    Optional<Comment> findOne(Long commentId);

    List<Comment> findAllDesc(Long boardId, Pageable pageable);

    long findTotalCount(Long boardId);
}
