package com.workintech.springboot.springbootfundamentalsrest.entity;

//Şu an için @Componenet olarak annotate etmek gerekmiyor
public class Animal {

    private int id;

    private String name;

    //boş constructor oluşturmanın amacı bu sınıf component olarak annotate edilirse neye göre nesne üreteceğini bilemeyeceği içindir.
    public Animal() {
    }

    public Animal(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
