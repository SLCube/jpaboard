package com.slcube.jpaboard.controller;

import com.slcube.jpaboard.dto.comment.CommentListResponseDto;
import com.slcube.jpaboard.dto.comment.CommentSaveRequestDto;
import com.slcube.jpaboard.dto.comment.CommentUpdateRequestDto;
import com.slcube.jpaboard.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Tag(name = "댓글")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 등록")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!", content = @Content),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR !!", content = @Content),
    })
    @PostMapping("/comments")
    public Long save(@RequestBody CommentSaveRequestDto requestDto) {
        return commentService.save(requestDto);
    }

    @Operation(summary = "댓글 리스트 조회, size는 10으로 고정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!", content = @Content),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR !!", content = @Content),
    })
    @GetMapping("/comments/{boardId}")
    public Page<CommentListResponseDto> findAllDesc(@PathVariable Long boardId, @ParameterObject Pageable pageable) {
        return commentService.findAllDesc(boardId, pageable);
    }

    @Operation(summary = "댓글 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!", content = @Content),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR !!", content = @Content),
    })
    @PatchMapping("/comments/{id}")
    public Long update(@PathVariable Long commentId, @RequestBody CommentUpdateRequestDto requestDto) {
        return commentService.update(commentId, requestDto);
    }

    @Operation(summary = "게시글 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!", content = @Content),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR !!", content = @Content),
    })
    @DeleteMapping("/comments/{id}")
    public Long delete(@PathVariable Long commentId) {
        return commentService.delete(commentId);
    }
}
