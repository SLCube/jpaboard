package com.slcube.jpaboard.service;

import com.slcube.jpaboard.domain.Board;
import com.slcube.jpaboard.dto.BoardListResponseDto;
import com.slcube.jpaboard.dto.BoardResponseDto;
import com.slcube.jpaboard.dto.BoardSaveRequestDto;
import com.slcube.jpaboard.dto.BoardUpdateRequestDto;
import com.slcube.jpaboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Long save(BoardSaveRequestDto requestDto) {
        return boardRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long boardId, BoardUpdateRequestDto requestDto) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + boardId));

        board.update(requestDto);

        return boardId;
    }

    public BoardResponseDto findById(Long boardId) {
        Board board = boardRepository.findOne(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + boardId));
        return new BoardResponseDto(board);
    }

    public List<BoardListResponseDto> findAll() {
        return boardRepository.findAllDesc().stream()
                .map(BoardListResponseDto::new)
                .collect(Collectors.toList());
    }

    public Long delete(Long boardId) {
        boardRepository.findOne(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + boardId))
                .delete();

        return boardId;
    }
}
