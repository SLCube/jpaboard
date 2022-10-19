package com.slcube.jpaboard.controller;

import com.slcube.jpaboard.dto.BoardListResponseDto;
import com.slcube.jpaboard.dto.BoardResponseDto;
import com.slcube.jpaboard.dto.BoardSaveRequestDto;
import com.slcube.jpaboard.dto.BoardUpdateRequestDto;
import com.slcube.jpaboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<BoardListResponseDto> findAllDesc() {
        return boardService.findAll();
    }

    @DeleteMapping("/boards/{id}")
    public Long delete(@PathVariable Long id) {
        return boardService.delete(id);
    }
}
