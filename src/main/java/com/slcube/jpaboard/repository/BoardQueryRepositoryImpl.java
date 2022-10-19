package com.slcube.jpaboard.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.slcube.jpaboard.domain.Board;
import com.slcube.jpaboard.domain.DeleteFlag;
import com.slcube.jpaboard.dto.BoardListResponseDto;
import com.slcube.jpaboard.dto.QBoardListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.slcube.jpaboard.domain.QBoard.board;

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
    public Page<BoardListResponseDto> findAllDesc(Pageable pageable) {
        List<BoardListResponseDto> content = query
                .select(new QBoardListResponseDto(
                        board.id.as("boardId"),
                        board.title,
                        board.author,
                        board.viewCount,
                        board.createdDate
                ))
                .from(board)
                .where(board.deleteFlag.eq(DeleteFlag.N))
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
                .where(board.deleteFlag.eq(DeleteFlag.N))
                .orderBy(board.id.desc())
                .fetch()
                .size();

        return new PageImpl<>(content, pageable, total);
    }
}
