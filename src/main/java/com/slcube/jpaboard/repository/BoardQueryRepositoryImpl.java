package com.slcube.jpaboard.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.slcube.jpaboard.domain.Board;
import com.slcube.jpaboard.domain.DeleteFlag;
import lombok.RequiredArgsConstructor;

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
    public List<Board> findAllDesc() {
        return query
                .selectFrom(board)
                .where(board.deleteFlag.eq(DeleteFlag.N))
                .orderBy(board.id.desc())
                .fetch();
    }
}
