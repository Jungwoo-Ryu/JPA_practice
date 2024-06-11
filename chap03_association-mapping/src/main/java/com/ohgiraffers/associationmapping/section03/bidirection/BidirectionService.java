package com.ohgiraffers.associationmapping.section03.bidirection;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BidirectionService {
    private BidirectionRepository bidirectionRepository;

    public BidirectionService(BidirectionRepository bidirectionRepository) {
        this.bidirectionRepository = bidirectionRepository;
    }

    public Menu findMenu(int menuCode) {
        return bidirectionRepository.findMenu(menuCode);
    }

    @Transactional
    public Category findCategory(int categoryCode) {
        Category foundCategory = bidirectionRepository.findCategory(categoryCode);
        System.out.println("foundCategory.getMenuList() = " + foundCategory.getMenuList());
        return foundCategory;
    }

    public void registMenu(Menu menu) {
        bidirectionRepository.saveMenu(menu);
    }

    public void registCategory(Category category) {
        bidirectionRepository.registCategory(category);
    }
}
