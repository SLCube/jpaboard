package com.slcube.jpaboard.service;

import com.slcube.jpaboard.domain.Board;
import com.slcube.jpaboard.repository.BoardRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FakeBoardRepository implements BoardRepository {
    private Map<Long, Board> boardLinkedMap = new LinkedHashMap<>();

    @Override
    public Long save(Board board) {
        boardLinkedMap.put(board.getId(), board);
        return board.getId();
    }

    @Override
    public Board findOne(Long boardId) throws Exception {
        return boardLinkedMap.get(boardId);
    }

    @Override
    public List<Board> findAll() {
        List<Board> boardList = new ArrayList<>();
        for (Board board : boardLinkedMap.values()) {
            if (board.getDeleteYn().equals("N")) {
                boardList.add(board);
            }
        }
        return boardList;
    }

    @Override
    public Long remove(Long boardId) throws Exception {
        boardLinkedMap.remove(boardId);
        return boardId;
    }
}
