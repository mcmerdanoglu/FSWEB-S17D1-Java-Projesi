package com.workintech.springboot.springbootfundamentalsrest.mapping;

import com.workintech.springboot.springbootfundamentalsrest.entity.Animal;

//Animal dönmek yerine bu sınıftan animalresponse dönerek istenilen mesajlar return değerine enjekte edilebilir.
//(Bu örnekte animal, message ve htttp kodu status olarak iletilliyor)
public class AnimalResponse {

    private Animal animal;
    private String message;
    private int status;

    public AnimalResponse(Animal animal, String message, int status) {
        this.animal = animal;
        this.message = message;
        this.status = status;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
