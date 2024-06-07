package com.ohgiraffers.section03.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class EntityLifeCycleTests {

    private EntityLifeCycle lifeCycle;

    @BeforeEach
    void setUp() {
        this.lifeCycle = new EntityLifeCycle();
    }

    @DisplayName("비영속 테스트")
    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void testTransient(int menuCode) {

        Menu foundMenu = lifeCycle.findMenuByMenuCode(menuCode);

        Menu newMenu = new Menu(
                foundMenu.getMenuCode(),
                foundMenu.getMenuName(),
                foundMenu.getMenuPrice(),
                foundMenu.getCategoryCode(),
                foundMenu.getOrderableStatus()
        );

        Assertions.assertNotEquals(foundMenu, newMenu);
        Assertions.assertFalse(lifeCycle.getManagerInstance().contains(newMenu));

    }

    @DisplayName("다른 엔티티 매니저가 관리하는 엔티티의 영속성 테스트")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void testOtherManager(int menuCode) {

        Menu menu1 = lifeCycle.findMenuByMenuCode(menuCode);
        Menu menu2 = lifeCycle.findMenuByMenuCode(menuCode);

        Assertions.assertNotEquals(menu1, menu2);
    }

    @DisplayName("같은 엔티티 매니저가 관리하는 엔티티의 영속성 테스트")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void testSameManager(int menuCode) {

        EntityManager manager = EntityManagerGenerator.getManagerInstance();

        Menu menu1 = manager.find(Menu.class, menuCode);
        Menu menu2 = manager.find(Menu.class, menuCode);

        Assertions.assertEquals(menu1, menu2);

    }

    @DisplayName("준영속화 detach 테스트")
    @ParameterizedTest
    @CsvSource({"11, 1000", "12, 1000"})
    void testDetachEntity(int menuCode, int menuPrice){
        EntityManager manager = EntityManagerGenerator.getManagerInstance();
        EntityTransaction transaction = manager.getTransaction();

        Menu foundMenu = manager.find(Menu.class, menuCode);
        transaction.begin();

//        detach 는 특정 엔티티만 준영속 상태로 만듬.
        manager.detach(foundMenu);
        foundMenu.setMenuPrice(1000);

        manager.flush();

        Assertions.assertNotEquals(menuPrice, manager.find(Menu.class, menuCode).getMenuPrice());
        transaction.rollback();
    }

    @DisplayName("준영속화 detach 후 다시 영속화 테스트")
    @ParameterizedTest(name = "[{index}] 준영속화 된 {0}번 메뉴를 {1}원으로 변경하고 다시 영속화 되는 지 확인")
    @CsvSource({"11,1000", "12,1000"})
    void testDetachAndPersis(int menuCode, int menuPrice){

        EntityManager manager = EntityManagerGenerator.getManagerInstance();
        EntityTransaction transaction = manager.getTransaction();

        Menu foundMenu = manager.find(Menu.class, menuCode);

        transaction.begin();
        manager.detach(foundMenu);
        foundMenu.setMenuPrice(menuPrice);

        manager.merge(foundMenu);
        manager.flush();

        Assertions.assertEquals(menuPrice, manager.find(Menu.class, menuCode).getMenuPrice());
        transaction.rollback();

    }

    @DisplayName("detach 준영속 후 merge 한 데이터")
    @Test
    void detachAndMergeSave(){
        EntityManager manager = EntityManagerGenerator.getManagerInstance();
        EntityTransaction transaction = manager.getTransaction();
        Menu foundMenu = manager.find(Menu.class, 20);

        manager.detach(foundMenu);

        transaction.begin();
        foundMenu.setMenuCode(999);
        foundMenu.setMenuName("닭가슴살샐러드");

        manager.merge(foundMenu);
        manager.flush();
        Assertions.assertEquals("마라탕",manager.find(Menu.class, 20).getMenuName());
        Assertions.assertEquals("닭가슴살샐러드",manager.find(Menu.class, 999).getMenuName());

    }

    @DisplayName("준영속화 clear 테스트")
    @ParameterizedTest
    @ValueSource(ints = {1,2,3})
    void testClearPersistenceContext(int menuCode){
        EntityManager manager = EntityManagerGenerator.getManagerInstance();
        Menu foundMenu = manager.find(Menu.class, menuCode);
        // clear(): 영속성 컨텍스트를 초기화 -> 영속성 컨텍스트 내의 모든 엔티티를 준영속화 시킨다.
        manager.clear();

        Menu expectedMenu = manager.find(Menu.class, menuCode);
        Assertions.assertNotEquals(foundMenu,expectedMenu);
    }

    @DisplayName("준영속화 close 테스트")
    @ParameterizedTest
    @ValueSource(ints = {1,2,3})
    void closePersistenceContext(int menuCode){
        EntityManager manager = EntityManagerGenerator.getManagerInstance();
        Menu foundMenu = manager.find(Menu.class, menuCode);
        // close(): 영속성 컨텍스트를 종료한다 -> 영속성 컨텍스트 내 모든 객체를 준영속화 시킨다.
        manager.close();

//        Menu expectedMenu = manager.find(Menu.class, menuCode);
//        Assertions.assertNotEquals(foundMenu,expectedMenu);

        Assertions.assertThrows(IllegalStateException.class, () -> manager.find(Menu.class,menuCode));

    }

    @DisplayName("영속성 엔티티 삭제 remove 테스트")
    @ParameterizedTest
    @ValueSource(ints = {1,2,3})
    void testRemoveEntity(int menuCode){
        EntityManager manager = EntityManagerGenerator.getManagerInstance();
        EntityTransaction transaction = manager.getTransaction();

        Menu foundMenu = manager.find(Menu.class, menuCode);

        transaction.begin();

        /*
        *   필기.
        *       remove()
        *       엔티티를 영속성 컨텍스트 및 데이터베이스에서 삭제한다.
        *       단, 트랜젝션을 제어하지 않으면 데이터베이스에 영구 반영되지는 않는다.
        *       트랜젝션을 커밋, or 플러쉬 하는 순간 영속성 컨텍스트에서
        *       관리하는 엔티티 객체가 데이터 베이스에 반영된다.
        * */
        manager.remove(foundMenu);
        manager.flush();

        Menu refoundMenu = manager.find(Menu.class,menuCode);
        Assertions.assertEquals(null, refoundMenu);

        transaction.rollback();
//        Assertions.assertFalse(manager.contains(foundMenu));

    }



}