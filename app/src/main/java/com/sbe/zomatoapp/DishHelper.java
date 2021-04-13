package com.sbe.zomatoapp;

class DishHelper {
    String name,category;

    public DishHelper(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public DishHelper() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
