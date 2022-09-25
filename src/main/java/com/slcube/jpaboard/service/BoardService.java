package com.slcube.jpaboard.service;

import com.slcube.jpaboard.domain.Board;
import com.slcube.jpaboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    public Long register(Board board) {
        return boardRepository.save(board);
    }

    public List<Board> findBoards() {
        return boardRepository.findAll();
    }

    public Board findBoard(Long boardId) throws Exception {
        Board findBoard = boardRepository.findOne(boardId);
        findBoard.increaseViewCount();
        return findBoard;
    }

    @Transactional
    public Long modifiedBoard(Long boardId, String title, String content) throws Exception {
        Board findBoard = boardRepository.findOne(boardId);
        return findBoard.modifiedBoard(title, content);
    }

    public Long deleteBoard(Long boardId) throws Exception {
        Board findBoard = boardRepository.findOne(boardId);
        return findBoard.deleteBoard();
    }
}
