package com.app.ecoshare.Models;

public class Category {

    private Integer id;

    private String category;

    public Category(Integer id, String category) {
        this.id = id;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
