package com.ohgiraffers.jpql.section03.paging;

import com.ohgiraffers.jpql.section04.groupfunction.Menu;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PagingRepository {

    @PersistenceContext
    private EntityManager manager;

    public List<Menu> findMenuForPage(int offset, int limit) {

        String jpql = "select m from section03Menu m order by m.menuCode desc";
        // 쿼리문에 직접 limit 을 사용하지 않는 이유는 DBMS 마다 문법이 다르기 때문에 JPA 에서 제공하는 API 를 통해서 처리하여 일관성을 유지한다.

        List<Menu> menus = manager.createQuery(jpql,Menu.class).setFirstResult(offset).setMaxResults(limit).getResultList();

        return menus;
    }
}