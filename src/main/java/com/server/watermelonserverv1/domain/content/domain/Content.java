package com.server.watermelonserverv1.domain.content.domain;

import com.server.watermelonserverv1.domain.content.domain.type.ContentType;
import com.server.watermelonserverv1.domain.post.domain.Post;
import com.server.watermelonserverv1.global.entity.BasedIdEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "content_id"))
@Entity
public class Content extends BasedIdEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContentType type;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @JoinColumn(name = "post_id", nullable = false)
    @ManyToOne
    private Post post;

    @Builder
    public Content(ContentType type, String content, Post post) {
        this.type = type;
        this.content = content;
        this.post = post;
    }
}
