package com.slcube.jpaboard.controller;

import com.slcube.jpaboard.dto.board.BoardListResponseDto;
import com.slcube.jpaboard.dto.board.BoardResponseDto;
import com.slcube.jpaboard.dto.board.BoardSaveRequestDto;
import com.slcube.jpaboard.dto.board.BoardUpdateRequestDto;
import com.slcube.jpaboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    @PostMapping("/boards")
    public Long save(@RequestBody BoardSaveRequestDto requestDto) {
        return boardService.save(requestDto);
    }

    @PatchMapping("/boards/{id}")
    public Long update(@PathVariable Long id, @RequestBody BoardUpdateRequestDto requestDto) {
        return boardService.update(id, requestDto);
    }

    @GetMapping("/boards/{id}")
    public BoardResponseDto findById(@PathVariable Long id) {
        return boardService.findById(id);
    }

    @GetMapping("/boards")
    public Page<BoardListResponseDto> findAllDesc(Pageable pageable) {
        return boardService.findAll(pageable);
    }

    @DeleteMapping("/boards/{id}")
    public Long delete(@PathVariable Long id) {
        return boardService.delete(id);
    }
}
