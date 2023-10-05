package com.ssafy.coffeeing.modules.feed.domain;

import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.product.domain.ProductType;
import com.ssafy.coffeeing.modules.search.domain.Tag;
import com.ssafy.coffeeing.modules.util.base.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "feed_id"))
@Entity
public class Feed extends BaseEntity {

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "like_count", nullable = false)
    @Builder.Default
    private Integer likeCount = 0;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "product_category")
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Column(name = "tag_name")
    private String tagName;

    @Column(name = "tag_id")
    private Long tagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateTag(Tag tag) {
        this.tagId = tag.tagId();
        this.productType = tag.category();
        this.tagName = tag.name();
    }

    public void updateLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }
}
