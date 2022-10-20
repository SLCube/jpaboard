package com.slcube.jpaboard.config.init;

import com.slcube.jpaboard.domain.Board;
import com.slcube.jpaboard.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitBoard {

    private final InitBoardService initBoardService;

    @PostConstruct
    public void init() {
        initBoardService.boardInit();
        initBoardService.commentInit();
    }

    @Component
    @RequiredArgsConstructor
    static class InitBoardService {

        private final EntityManager em;

        @Transactional
        public void boardInit() {

            for (int i = 0; i < 100; i++) {
                Board board = Board.builder()
                        .title("board title " + (i + 1))
                        .content("board content " + (i + 1))
                        .author("board author " + (i + 1))
                        .build();

                em.persist(board);
            }
        }

        @Transactional
        public void commentInit() {
            List<Board> boards = em.createQuery("select b from Board b", Board.class)
                    .getResultList();

            for (Board board : boards) {
                for (int i = 0; i < 20; i++) {
                    Comment comment = Comment.builder()
                            .content("comment content " + (i + 1))
                            .author("comment author " + (i + 1))
                            .build();
                    board.addComment(comment);
                    em.persist(comment);
                }
            }

            em.flush();
            em.clear();
        }
    }

}
