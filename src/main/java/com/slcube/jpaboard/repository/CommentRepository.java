package com.slcube.jpaboard.repository;

import com.slcube.jpaboard.domain.Board;
import com.slcube.jpaboard.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public Long save(Comment comment) {
        em.persist(comment);
        return comment.getId();
    }

    public Comment findOne(Long commentId) {
        return em.createQuery("select c from Comment  c where c.id = :commentId and c.deleteYn = 'N'", Comment.class)
                .setParameter("commentId", commentId)
                .getSingleResult();
    }

    public List<Comment> findComments(Long boardId) {
        return em.createQuery("select c from Comment c where c.board.id = :boardId and c.deleteYn = 'N'", Comment.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }

    public Long remove(Long commentId) {
        Comment findComment = em.find(Comment.class, commentId);
        return findComment.deleteComment();
    }
}
