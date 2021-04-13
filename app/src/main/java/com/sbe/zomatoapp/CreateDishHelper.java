package com.sbe.zomatoapp;

class CreateDishHelper {
    String name,cont,category;

    public CreateDishHelper() {
    }
    public CreateDishHelper(String name, String cont, String category) {
        this.name = name;
        this.cont = cont;
        this.category = category;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCont() {
        return cont;
    }
    public void setCont(String cont) {
        this.cont = cont;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
}
