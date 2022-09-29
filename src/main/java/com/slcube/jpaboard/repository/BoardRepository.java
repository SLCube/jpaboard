package com.slcube.jpaboard.repository;

import com.slcube.jpaboard.domain.Board;

import java.util.List;

public interface BoardRepository {
    public Long save(Board board);
    public Board findOne(Long boardId) throws Exception;
    public List<Board> findAll();
    public Long remove(Long boardId) throws Exception;
}
