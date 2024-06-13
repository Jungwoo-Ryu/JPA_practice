package com.ohgiraffers.jpql.section02.parameter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ParameterBindingRepository {

    @PersistenceContext
    private EntityManager manager;

    public List<Menu> selectMenuByBindingName(String menuName) {
        String jpql = "select m from section02Menu m where m.menuName = :menuName";
        List<Menu> resultMenuList = manager.createQuery(jpql, Menu.class)
                .setParameter("menuName",menuName)
                .getResultList();
        return resultMenuList;
    }

    public List<Menu> selectMenuByBindingPosition(String menuName) {
        String jpql = "select m from section02Menu m where m.menuName = ?1";
        List<Menu> resultMenuList = manager.createQuery(jpql, Menu.class)
                .setParameter(1,menuName)
                .getResultList();
        return resultMenuList;
    }
}
