package com.slcube.jpaboard.repository;

import com.slcube.jpaboard.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    public Long save(Board board) {
        em.persist(board);
        return board.getId();
    }

    public Board findOne(Long boardId) throws Exception {
        return em.createQuery("select b from Board b where b.id = :boardId and b.deleteYn = 'N'", Board.class)
                .setParameter("boardId", boardId)
                .getSingleResult();
    }

    public List<Board> findAll() {
        return em.createQuery("select b from Board b where b.deleteYn = 'N'", Board.class)
                .getResultList();
    }

//    public Long update(Board board) throws Exception {
//        Board findBoard = findOne(board.getId());
//        findBoard.setTitle(board.getTitle());
//        findBoard.setContent(board.getContent());
//
//        return board.getId();
//    }

    public Long remove(Long boardId) throws Exception {
        Board findBoard = findOne(boardId);
        findBoard.deleteBoard();
        return boardId;
    }
}
