package com.ohgiraffers.mapping.section03.compositekey.subsection01.embeddedid;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class LikedCompositeKey {

    @Embedded
    private LikedMemberNo memberNo;

    @Embedded
    private LikedBookNo bookNo;

    protected LikedCompositeKey(){}

    public LikedCompositeKey(LikedMemberNo likedMemberNo, LikedBookNo likedBookNo) {
        this.memberNo = likedMemberNo;
        this.bookNo = likedBookNo;
    }
}