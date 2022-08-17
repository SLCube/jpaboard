package com.slcube.jpaboard.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public Board(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        viewCount = 0;
        deleteYn = "N";
        createdDate = LocalDateTime.now();
    }
    
    // 비즈니스 로직

    public void increaseViewCount() {
        this.viewCount++;
    }

    public void deleteBoard() {
        this.deleteYn = "Y";
    }

    public void modifiedBoard(String title, String content) {
        this.title = title;
        this.content = content;
    }

//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
}
