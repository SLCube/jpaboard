package com.slcube.jpaboard.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Board {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;
    private String content;
    private String writer;
    private int viewCount;
    private String deleteYn;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public Board() {
        viewCount = 0;
        createdDate = LocalDateTime.now();    }
}
