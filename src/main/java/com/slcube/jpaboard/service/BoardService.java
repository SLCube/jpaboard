package com.slcube.jpaboard.service;

import com.slcube.jpaboard.domain.Board;
import com.slcube.jpaboard.dto.board.*;
import com.slcube.jpaboard.exception.BoardNotFoundException;
import com.slcube.jpaboard.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    public Long save(BoardSaveRequestDto requestDto) {
        return boardRepository.save(requestDto.toEntity()).getId();
    }

    public Long update(Long boardId, BoardUpdateRequestDto requestDto) {
        Board board = boardRepository.findOne(boardId)
                .orElseThrow(() -> new BoardNotFoundException("해당 게시글이 없습니다. id = " + boardId));

        board.update(requestDto);

        return boardId;
    }

    public BoardResponseDto findById(Long boardId) {
        Board board = boardRepository.findOne(boardId)
                .orElseThrow(() -> new BoardNotFoundException("해당 게시글이 없습니다. id = " + boardId));

        board.plusViewCount();

        return new BoardResponseDto(board);
    }

    @Transactional(readOnly = true)
    public Page<BoardListResponseDto> findAll(BoardSearch boardSearch, Pageable pageable) {
        List<BoardListResponseDto> content = boardRepository.findAllDesc(boardSearch, pageable)
                .stream()
                .map(BoardListResponseDto::new)
                .collect(Collectors.toList());
        long total = boardRepository.findTotalCount(boardSearch);

        return new PageImpl<>(content, pageable, total);
    }

    public Long delete(Long boardId) {
        Board board = boardRepository.findOne(boardId)
                .orElseThrow(() -> new BoardNotFoundException("해당 게시글이 없습니다. id = " + boardId));

        board.delete();

        return boardId;
    }
}
