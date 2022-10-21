package com.slcube.jpaboard.repository.board;

import com.slcube.jpaboard.domain.Board;
import com.slcube.jpaboard.dto.board.BoardSearch;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BoardQueryRepository {

    Optional<Board> findOne(Long id);

    List<Board> findAllDesc(BoardSearch boardSearch, Pageable pageable);

    long findTotalCount(BoardSearch boardSearch);
}
