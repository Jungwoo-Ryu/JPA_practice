package com.ohgiraffers.problem;

public class Category {
    private int categoryCode;
    private String categoryName;

    public Category(int categoryCode, String categoryName) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
    }

    public Category() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoruCode) {
        this.categoryCode = categoruCode;
    }
}
