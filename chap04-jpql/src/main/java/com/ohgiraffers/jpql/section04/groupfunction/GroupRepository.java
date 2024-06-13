package com.ohgiraffers.jpql.section04.groupfunction;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupRepository {

    @PersistenceContext
    private EntityManager manager;

    public long countMenuOfCategory(int categoryCode) {
        String jpql = "select count(m.menuPrice) from section04Menu m where m.categoryCode = :categoryCode";
        long countOfMenu = manager.createQuery(jpql, Long.class)
                .setParameter("categoryCode", categoryCode).getSingleResult();
        return countOfMenu;
    }


    public long noResult(int categoryCode) {
        String jpql = "select sum(m.menuPrice) from section04Menu m where m.categoryCode = :categoryCode";
        long sumOfPrice = manager.createQuery(jpql, Long.class)
                .setParameter("categoryCode", categoryCode)
                .getSingleResult();

        return sumOfPrice;
    }

    public List<Object[]> selectGroupAndHaving(long minPrice) {
        String jpql = "select m.categoryCode, SUM(m.menuPrice)" +
                " from section04Menu m" +
                " Group by m.categoryCode Having SUM(m.menuPrice) >= :minPrice";
        List<Object[]> sumPriceCategoryList = manager.createQuery(jpql)
                .setParameter("minPrice", minPrice)
                .getResultList();

        return sumPriceCategoryList;
    }
}
