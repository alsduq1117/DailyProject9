package com.example.project9.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE post SET deleted_at = NOW() WHERE id = ?")
//@SQLRestriction("deleted_at IS NULL")
@Slf4j
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


    @Builder
    public Post(String title, String content, LocalDateTime deletedAt) {
        this.title = title;
        this.content = content;
        this.deletedAt = deletedAt;
    }

    public PostEditor.PostEditorBuilder toEditor() {
        return PostEditor.builder()
                .title(title)
                .content(content);
    }

    public void edit(PostEditor postEditor) {
        this.title = postEditor.getTitle();
        this.content = postEditor.getContent();
    }

    public void markAsDeleted() {
        this.deletedAt = LocalDateTime.now();
        log.info("Deleted at: {}", this.deletedAt); // 로그 추가
    }
}
