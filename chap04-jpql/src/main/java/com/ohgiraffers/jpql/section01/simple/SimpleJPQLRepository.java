package com.ohgiraffers.jpql.section01.simple;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SimpleJPQLRepository {

    @PersistenceContext
    private EntityManager manager;

    public String selectSingleMenuByTypedQuery() {
        String jpql = "select m.menuName from section01Menu m where m.menuCode = 8";
        TypedQuery<String> query = manager.createQuery(jpql, String.class);

        String resultMenuName = query.getSingleResult();

        return resultMenuName;

    }

    public Menu findMenu(int menuCode) {
        return manager.find(Menu.class, menuCode);
    }

    public Object selectSingleMenuByQuery() {
        String jpql = "select m.menuName from section01Menu m where m.menuCode = 8";
        Query query = manager.createQuery(jpql);
        Object resultMenuName = query.getSingleResult();

        return resultMenuName;
    }

    public Menu selectSingleRowByTypedQuery() {
        String jpql = "select m from section01Menu m where m.menuCode = 8";
        TypedQuery<Menu> query = manager.createQuery(jpql, Menu.class);
        Menu resultMenu = query.getSingleResult();

        return resultMenu;
    }

    public List<Menu> selectMultiRowByTypedQuery() {
        String jpql = "select m from section01Menu m";
        TypedQuery<Menu> query = manager.createQuery(jpql, Menu.class);
        List<Menu> resultMenus = query.getResultList();
        return resultMenus;
    }

    public List<Menu> selectMultiRowByQuery() {
        String jpql = "select m from section01Menu m";
        Query query = manager.createQuery(jpql);

        List<Menu> resultMenuList = query.getResultList();
        return resultMenuList;
    }

    public List<Integer> selectUseDistinct() {
        String jpql = "select distinct m.categoryCode from section01Menu m where m.categoryCode in (11, 20)";
        TypedQuery<Integer> query = manager.createQuery(jpql, Integer.class);
        List<Integer> resultCategoryCodeList = query.getResultList();

        return resultCategoryCodeList;
    }
}
