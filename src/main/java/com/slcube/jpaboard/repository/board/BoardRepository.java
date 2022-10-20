package com.slcube.jpaboard.repository.board;

import com.slcube.jpaboard.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardQueryRepository {
}
