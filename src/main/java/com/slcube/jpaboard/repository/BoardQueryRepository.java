package com.slcube.jpaboard.repository;

import com.slcube.jpaboard.domain.Board;
import com.slcube.jpaboard.dto.BoardListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BoardQueryRepository {

    Optional<Board> findOne(Long id);

    Page<BoardListResponseDto> findAllDesc(Pageable pageable);
}
