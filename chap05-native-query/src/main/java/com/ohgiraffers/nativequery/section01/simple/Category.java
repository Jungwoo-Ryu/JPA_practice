package com.ohgiraffers.nativequery.section01.simple;

import jakarta.persistence.*;

@Entity(name = "section01Category")
@Table(name = "tbl_category")
@SqlResultSetMappings(value = {
        /*필기.
        *   1. @Column 으로 매핑 설정이 되어 있는 경우 (자동) */
        @SqlResultSetMapping(
                name = "categoryAutoMapping",
                entities = {@EntityResult(entityClass = Category.class)},
                columns = {@ColumnResult(name="menu_count")}
        ),
        // 2. 매핑 설정을 수동으로 하는 경우 (@Column 어노테이션 생략 가능)
        @SqlResultSetMapping(
                name = "categoryManualMapping",
                entities = {
                        @EntityResult(entityClass = Category.class, fields = {
                                @FieldResult(name = "categoryCode", column = "category_code"),
                                @FieldResult(name = "categoryName", column = "category_name"),
                                @FieldResult(name = "refCategoryCode", column = "ref_category_code")
                        })
                },
                columns = {@ColumnResult(name="menu_count")}
        )
})
public class Category {

    @Id
    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "ref_category_code")
    private Integer refCategoryCode;

    public Category() {}

    public Category(int categoryCode, String categoryName, Integer refCategoryCode) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.refCategoryCode = refCategoryCode;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryCode=" + categoryCode +
                ", categoryName='" + categoryName + '\'' +
                ", refCategoryCode=" + refCategoryCode +
                '}';
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getRefCategoryCode() {
        return refCategoryCode;
    }

    public void setRefCategoryCode(Integer refCategoryCode) {
        this.refCategoryCode = refCategoryCode;
    }
}
