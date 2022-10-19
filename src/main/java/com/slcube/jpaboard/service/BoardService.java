package com.slcube.jpaboard.service;

import com.slcube.jpaboard.domain.Board;
import com.slcube.jpaboard.dto.BoardListResponseDto;
import com.slcube.jpaboard.dto.BoardResponseDto;
import com.slcube.jpaboard.dto.BoardSaveRequestDto;
import com.slcube.jpaboard.dto.BoardUpdateRequestDto;
import com.slcube.jpaboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + boardId));

        board.update(requestDto);

        return boardId;
    }

    public BoardResponseDto findById(Long boardId) {
        Board board = boardRepository.findOne(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + boardId));
        board.plusViewCount();
        return new BoardResponseDto(board);
    }

    @Transactional(readOnly = true)
    public Page<BoardListResponseDto> findAll(Pageable pageable) {
        return boardRepository.findAllDesc(pageable);
    }

    public Long delete(Long boardId) {
        boardRepository.findOne(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + boardId))
                .delete();

        return boardId;
    }
}
