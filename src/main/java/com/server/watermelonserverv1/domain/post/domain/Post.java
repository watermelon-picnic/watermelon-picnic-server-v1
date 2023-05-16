package com.server.watermelonserverv1.domain.post.domain;

import com.server.watermelonserverv1.domain.post.domain.type.PostType;
import com.server.watermelonserverv1.domain.region.domain.Region;
import com.server.watermelonserverv1.domain.writer.domain.Writer;
import com.server.watermelonserverv1.global.entity.BasedIdEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.Instant;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "post_id"))
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@Entity
public class Post extends BasedIdEntity {

    @Column(nullable = false, length = 100)
    private String title;

    @ColumnDefault(value = "0")
    @Column(nullable = false)
    private Long view;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column
    private String image;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 10, nullable = false)
    private PostType postType;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Instant date;

    @ManyToOne
    @JoinColumn(name = "writer_id", nullable = false)
    private Writer writer;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @Builder
    public Post(String title, Long view, String content, String image, PostType postType, Writer writer, Region region) {
        this.title = title;
        this.view = view;
        this.content = content;
        this.image = image;
        this.postType = postType;
        this.writer = writer;
        this.region = region;
    }
}
