package com.ohgiraffers.mapping.section03.compositekey.subsection01.embeddedid;

public class LikeDTO {
    private int likedMemberNo;
    private int likedBookNo;

    public LikeDTO() {

    }

    public int getLikedMemberNo() {
        return likedMemberNo;
    }

    public void setLikedMemberNo(int likedMemberNo) {
        this.likedMemberNo = likedMemberNo;
    }

    public int getLikedBookNo() {
        return likedBookNo;
    }

    public void setLikedBookNo(int likedBookNo) {
        this.likedBookNo = likedBookNo;
    }

    public LikeDTO(int likedMemberNo, int likedBookNo) {
        this.likedMemberNo = likedMemberNo;
        this.likedBookNo = likedBookNo;
    }
}
