package com.slcube.jpaboard.service;

import com.slcube.jpaboard.domain.Board;
import com.slcube.jpaboard.domain.Comment;
import com.slcube.jpaboard.repository.BoardRepositoryImpl;
import com.slcube.jpaboard.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepositoryImpl boardRepositoryImpl;

    public Long register(Long boardId, Comment comment) throws Exception {
        Board findBoard = boardRepositoryImpl.findOne(boardId);
        findBoard.addComment(comment);
        return commentRepository.save(comment);
    }

    public List<Comment> findComments(Long boardId) {
        return commentRepository.findComments(boardId);
    }

    public Long modifiedComment(Long boardId, String content) {
        Comment findComment = commentRepository.findOne(boardId);
        return findComment.modifiedComment(content);
    }

    public Long deleteComment(Long commentId) {
        Comment findComment = commentRepository.findOne(commentId);
        return findComment.deleteComment();
    }
}
