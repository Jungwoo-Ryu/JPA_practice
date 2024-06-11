package com.ohgiraffers.associationmapping.section01.manytoone;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ManyToOneRepository {


    @PersistenceContext
    private EntityManager manager;

    public Menu find(int menuCode) {
        return manager.find(Menu.class, menuCode);
    }

    /*
    *   필기.
    *       Jpql(Java Persistence Query Language)
    *       - 객체 지향 쿼리 언어
    *       - 객체 지향 모델에 맞게 작성된 쿼리를 통해 엔티티를 대상으로 검색하고,
    *       - 검색 결과를 토대로 객체를 조작할 수 있다.
    *       - join 문법이 sql 과는 다소 차이가 있지만, 직접 쿼리를 작성할 수 있는
    *       - 문법을 제공한다.
    *       - 주의점 from 절에 기술할 테이블명에는 반드시 엔티티명을 작성해야한다.
    *       - 콜론(:) :menuCode - 쿼리문에서 변수의 값을 바인딩 하기 위한 jpql ansqjq
    * */
    public String findCategoryName(int menuCode) {
        String jpql = "select c.categoryName" +
                " from menu_and_category m" +
                " join m.category c" +
                " where m.menuCode = :menuCode";

        return manager.createQuery(jpql,String.class)
                .setParameter("menuCode", menuCode)
                .getSingleResult();
    }

    public void regist(Menu newMenu) {
        manager.persist(newMenu);
    }
}