package com.slcube.jpaboard.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany
    @JoinColumn(name = "comment_id")
    private List<Comment> comments;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Builder
    private Board(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        viewCount = 0;
        deleteYn = "N";
        createdDate = LocalDateTime.now();
    }

    public void increaseViewCount() {
        this.viewCount++;
    }

    public void deleteBoard() {
        this.deleteYn = "Y";
    }

    public void modifiedBoard(String modifiedTitle, String modifiedContent) {
        this.title = modifiedTitle;
        this.content = modifiedContent;
    }
}
