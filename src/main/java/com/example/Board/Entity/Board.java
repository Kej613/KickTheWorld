package com.example.Board.Entity;

import com.example.Member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "writer")
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    @Column(length=100, nullable = false)
    private String title;

    @Column(length=1500, nullable = false)
    private String content;

    @Column
    private String category;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id", nullable = false)
    private String writer;

    @ColumnDefault("0")
    private int viewCount;
//    @Column
//    private Long views = 0L; // 조회수 필드 추가

    //추가
    public void changeTitle(String title) {
        this.title = title;
    }
    public void changeContent(String content) {
        this.content = content;
    }

    public void changeCategory(String category) { this.category = category; }


//    public void increaseViews() {
//        this.views++;
//    }

    @PreUpdate
    public void increaseViewCount() {
        this.viewCount++;
    }

}
