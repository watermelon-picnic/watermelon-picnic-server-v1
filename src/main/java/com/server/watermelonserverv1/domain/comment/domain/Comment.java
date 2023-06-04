package com.server.watermelonserverv1.domain.comment.domain;

import com.server.watermelonserverv1.domain.post.domain.Post;
import com.server.watermelonserverv1.domain.writer.domain.Writer;
import com.server.watermelonserverv1.global.entity.BasedIdEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "comment_id"))
@DynamicUpdate
@Entity
public class Comment extends BasedIdEntity {

    @Column(nullable = false, length = 200)
    private String content;

    private String password;

    @JoinColumn(name = "post_id", nullable = false)
    @ManyToOne
    private Post post;

    @JoinColumn(name = "writer_id", nullable = false)
    @ManyToOne
    private Writer writer;

    public Comment updateContent(String content) {
        this.content = content;
        return this;
    }

    @Builder
    public Comment(String content, Post post, Writer writer, String password) {
        this.content = content;
        this.post = post;
        this.writer = writer;
        this.password = password;
    }
}
