package com.slcube.jpaboard.repository.comment;

import com.slcube.jpaboard.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentQueryRepository {
}
