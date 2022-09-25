package com.slcube.jpaboard.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String title;
    private String content;
    private String writer;
    private int viewCount;
    private String deleteYn;

    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

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
        createdDate = LocalDateTime.now();
        viewCount = 0;
        deleteYn = "N";
    }

    public void increaseViewCount() {
        this.viewCount++;
    }

    public Long deleteBoard() {
        this.deleteYn = "Y";
        return this.getId();
    }

    public Long modifiedBoard(String modifiedTitle, String modifiedContent) {
        this.title = modifiedTitle;
        this.content = modifiedContent;
        this.modifiedDate = LocalDateTime.now();
        return this.getId();
    }

    public void addComment(Comment comment) {
        comment.setBoard(this);
        this.getComments().add(comment);
    }
}
