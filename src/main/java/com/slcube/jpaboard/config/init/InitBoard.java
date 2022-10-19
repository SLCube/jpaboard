package com.slcube.jpaboard.config.init;

import com.slcube.jpaboard.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitBoard {

    private final InitBoardService initBoardService;

    @PostConstruct
    public void init() {
        initBoardService.init();
    }

    @Component
    @RequiredArgsConstructor
    static class InitBoardService {

        private final EntityManager em;

        @Transactional
        public void init() {

            for (int i = 0; i < 100; i++) {
                Board board = Board.builder()
                        .title("board title " + (i + 1))
                        .content("board content " + (i + 1))
                        .author("board author " + (i + 1))
                        .build();

                em.persist(board);
            }
        }
    }

}
