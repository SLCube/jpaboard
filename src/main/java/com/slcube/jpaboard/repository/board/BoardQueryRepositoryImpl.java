package com.slcube.jpaboard.repository.board;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.slcube.jpaboard.domain.Board;
import com.slcube.jpaboard.domain.DeleteFlag;
import com.slcube.jpaboard.dto.board.BoardListResponseDto;
import com.slcube.jpaboard.dto.board.BoardSearch;
import com.slcube.jpaboard.dto.board.QBoardListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.slcube.jpaboard.domain.QBoard.board;
import static org.springframework.util.StringUtils.*;

@RequiredArgsConstructor
public class BoardQueryRepositoryImpl implements BoardQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Optional<Board> findOne(Long id) {
        return Optional.ofNullable(query
                .selectFrom(board)
                .where(
                        board.id.eq(id),
                        board.deleteFlag.eq(DeleteFlag.N)
                )
                .fetchOne());
    }

    @Override
    public Page<BoardListResponseDto> findAllDesc(BoardSearch boardSearch, Pageable pageable) {
        List<BoardListResponseDto> content = query
                .select(new QBoardListResponseDto(
                        board.id.as("boardId"),
                        board.title,
                        board.author,
                        board.viewCount,
                        board.createdDate
                ))
                .from(board)
                .where(
                        board.deleteFlag.eq(DeleteFlag.N),
                        authorLike(boardSearch.getAuthor()),
                        contentLike(boardSearch.getContent())
                )
                .orderBy(board.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = query
                .select(new QBoardListResponseDto(
                        board.id.as("boardId"),
                        board.title,
                        board.author,
                        board.viewCount,
                        board.createdDate
                ))
                .from(board)
                .where(
                        board.deleteFlag.eq(DeleteFlag.N),
                        authorLike(boardSearch.getAuthor()),
                        contentLike(boardSearch.getContent())
                )
                .orderBy(board.id.desc())
                .fetch()
                .size();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression authorLike(String authorCond) {
        return hasText(authorCond) ? board.author.likeIgnoreCase(authorCond) : null;
    }

    private BooleanExpression contentLike(String contentCond) {
        return hasText(contentCond) ? board.content.likeIgnoreCase(contentCond) : null;
    }
}
