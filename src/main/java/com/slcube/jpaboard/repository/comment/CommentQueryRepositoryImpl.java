package com.slcube.jpaboard.repository.comment;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.slcube.jpaboard.domain.Comment;
import com.slcube.jpaboard.domain.DeleteFlag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.slcube.jpaboard.domain.QComment.comment;

@RequiredArgsConstructor
public class CommentQueryRepositoryImpl implements CommentQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Optional<Comment> findOne(Long commentId) {
        return Optional.ofNullable(query
                .selectFrom(comment)
                .where(
                        comment.id.eq(commentId),
                        comment.deleteFlag.eq(DeleteFlag.N)
                )
                .fetchOne());
    }

    @Override
    public List<Comment> findAllDesc(Long boardId, Pageable pageable) {

         return findAllQuery(boardId)
                .orderBy(comment.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public long findTotalCount(Long boardId) {
        return findAllQuery(boardId)
                .fetch()
                .size();
    }

    private JPAQuery<Comment> findAllQuery(Long boardId) {
        return query
                .selectFrom(comment)
                .where(
                        comment.board.id.eq(boardId),
                        comment.deleteFlag.eq(DeleteFlag.N)
                );
    }
}
