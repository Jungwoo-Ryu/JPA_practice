package com.ohgiraffers.section02.crud;

import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

public class EntityManagerCRUDTests {

    private EntityManagerCRUD crud;

    @BeforeEach
    void initManager(){
        this.crud = new EntityManagerCRUD();
    }

    @AfterEach
    void rollback(){
        EntityTransaction transaction = crud.getManagerInstance().getTransaction();
        transaction.rollback();
    }

    @DisplayName("메뉴 코드로 메뉴 조회 테스트")
    @ParameterizedTest
    @CsvSource({"1,1" , "2,2", "3,3"})
    void testFindMethodByMenuCode(int menuCode, int expected){
        // when
        Menu foundMenu = crud.findMenuByMenuCode(menuCode);

        Assertions.assertEquals(expected, foundMenu.getMenuCode());
    }

    private static Stream<Arguments> newMenu(){
        return Stream.of(
                Arguments.of(
                        "신메뉴", 20000, 4, "Y"
                )
        );
    }

    @DisplayName("새로운 메뉴 추가 테스트")
    @ParameterizedTest
    @MethodSource("newMenu")
    void testRegist(String menuName, int menuPrice, int categoryCode, String orderableStatus){
        Menu newMenu = new Menu(menuName, menuPrice, categoryCode, orderableStatus);
        Long count = crud.saveAndReturnAllCount(newMenu);

        Assertions.assertEquals(24, count);

    }

    @DisplayName("메뉴 이름 수정 테스트")
    @ParameterizedTest
    @CsvSource("1, 변경된 메뉴")
    void testModifyMenuName(int menuCode, String menuName){
        // when
        Menu modifyMenu = crud.modifyMenuName(menuCode, menuName);

        Assertions.assertEquals(menuName, modifyMenu.getMenuName());
    }

    @DisplayName("메뉴 삭제 테스트")
    @ParameterizedTest
    @ValueSource(ints = {1})
    void testRemoveMenu(int menuCode){
        Long count = crud.removeAndReturnAllCount(menuCode);

        Assertions.assertEquals(22, count);
    }
}
