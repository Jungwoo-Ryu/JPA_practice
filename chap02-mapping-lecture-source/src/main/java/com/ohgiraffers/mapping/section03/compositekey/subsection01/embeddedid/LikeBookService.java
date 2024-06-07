package com.ohgiraffers.mapping.section03.compositekey.subsection01.embeddedid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeBookService {

    @Autowired
    private LikeRepository likeRepository;

    @Transactional
    public void generateLikeBook(LikeDTO likeDTO) {

        Like like = new Like(
                new LikedCompositeKey(
                        new LikedMemberNo(likeDTO.getLikedMemberNo()),
                        new LikedBookNo(likeDTO.getLikedBookNo())
                )
        );

        likeRepository.save(like);

    }


}
