package com.slcube.jpaboard.service;

import com.slcube.jpaboard.domain.Board;
import com.slcube.jpaboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    public Long register(Board board) {
        return boardRepository.save(board);
    }

    public Board findOne(Long boardId) throws Exception {
        Board findBoard = boardRepository.findOne(boardId);
        findBoard.increaseViewCount();
        return findBoard;
    }
}
