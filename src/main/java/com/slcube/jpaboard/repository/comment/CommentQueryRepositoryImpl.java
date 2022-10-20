package com.slcube.jpaboard.repository.comment;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.slcube.jpaboard.domain.Comment;
import com.slcube.jpaboard.domain.DeleteFlag;
import com.slcube.jpaboard.dto.comment.CommentListResponseDto;
import com.slcube.jpaboard.dto.comment.QCommentListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    public Page<CommentListResponseDto> findAllDesc(Long boardId, Pageable pageable) {
        JPAQuery<CommentListResponseDto> findAllQuery = query
                .select(new QCommentListResponseDto(
                        comment.id.as("commentId"),
                        comment.author,
                        comment.content,
                        comment.createdDate
                ))
                .from(comment)
                .where(
                        comment.board.id.eq(boardId),
                        comment.deleteFlag.eq(DeleteFlag.N)
                );

        List<CommentListResponseDto> content = findAllQuery
                .orderBy(comment.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = findAllQuery
                .fetch()
                .size();

        return new PageImpl<>(content, pageable, total);
    }
}
