package com.server.watermelonserverv1.domain.comment.domain;

import com.server.watermelonserverv1.domain.comment.domain.type.CommentType;
import com.server.watermelonserverv1.domain.post.domain.Post;
import com.server.watermelonserverv1.domain.writer.domain.Writer;
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
@AttributeOverride(name = "id", column = @Column(name = "comment_id"))
@Entity
public class Comment extends BasedIdEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "comment_type", nullable = false, length = 15)
    private CommentType commentType;

    @Column(nullable = false, length = 200)
    private String content;

    @JoinColumn(name = "post_id", nullable = false)
    @ManyToOne
    private Post post;

    @JoinColumn(name = "writer_id", nullable = false)
    @ManyToOne
    private Writer writer;

    @Builder
    public Comment(CommentType commentType, String content, Post post, Writer writer) {
        this.commentType = commentType;
        this.content = content;
        this.post = post;
        this.writer = writer;
    }
}
