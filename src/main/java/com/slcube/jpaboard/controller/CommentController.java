package com.slcube.jpaboard.controller;

import com.slcube.jpaboard.dto.comment.CommentListResponseDto;
import com.slcube.jpaboard.dto.comment.CommentSaveRequestDto;
import com.slcube.jpaboard.dto.comment.CommentUpdateRequestDto;
import com.slcube.jpaboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public Long save(@RequestBody CommentSaveRequestDto requestDto) {
        return commentService.save(requestDto);
    }

    @GetMapping("/comments/{boardId}")
    public Page<CommentListResponseDto> findAllDesc(@PathVariable Long boardId, Pageable pageable) {
        return commentService.findAllDesc(boardId, pageable);
    }

    @PatchMapping("/comments/{id}")
    public Long update(@PathVariable Long commentId, @RequestBody CommentUpdateRequestDto requestDto) {
        return commentService.update(commentId, requestDto);
    }

    @DeleteMapping("/comments/{id}")
    public Long delete(@PathVariable Long commentId) {
        return commentService.delete(commentId);
    }
}
