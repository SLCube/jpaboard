package com.slcube.jpaboard.repository;

import com.slcube.jpaboard.domain.Board;

import java.util.List;
import java.util.Optional;

public interface BoardQueryRepository {

    Optional<Board> findOne(Long id);

    List<Board> findAllDesc();
}
