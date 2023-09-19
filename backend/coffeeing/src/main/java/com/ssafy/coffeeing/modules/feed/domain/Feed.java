package com.ssafy.coffeeing.modules.feed.domain;

import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.tag.domain.Tag;
import com.ssafy.coffeeing.modules.tag.domain.TagType;
import com.ssafy.coffeeing.modules.util.base.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

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
    private Long likeCount;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "tag_category")
    @Enumerated(EnumType.STRING)
    private TagType tagType;

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
        this.tagType = tag.category();
        this.tagName = tag.name();
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        if(this.likeCount > 0) {
            this.likeCount--;
        }
    }
}
