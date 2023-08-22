package com.workintech.springboot.springbootfundamentalsrest.controller;

import com.workintech.springboot.springbootfundamentalsrest.entity.Animal;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/workintech/animal") //tüm requestlerin önüne tekrar tekrar yazmak yerine bu şekilde konulabilir.
public class AnimalController {

    @Value("${instructor.name}")
    private String name;
    @Value("${instructor.surname}")
    private String surname;

    private Map<Integer, Animal> animalMap; //= new HashMap<>(); Şeklinde yazmak hard dependency olur ve bunu istemeyiz.

    //veya Post constructor ile constructor hemen sonra çalışacak bir 2. bir constructor yazılabilir
    @PostConstruct
    public void init(){
        animalMap = new HashMap<>();
    }

    //Hard dependency yerine constructor ile de insatance yaratılabilir.
    /*
    public AnimalController() {
        animalMap = new HashMap<>();
    }
     */

    @GetMapping("/welcome")
    public String welcome() {
    return name + " " + surname + " says hello";
    }

    @GetMapping("/")
    public List<Animal> getAllAnimals(){
        return animalMap.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Animal getAnimal(@PathVariable int id){
        if(id<0){
         //TODO id is not valid
        }
        if(!animalMap.containsKey(id)){
            //TODO map does not contain id
        }
        return animalMap.get(id);
    }

    @PostMapping
    public Animal addAnimal(@RequestBody Animal animal){
        if(animalMap.containsKey(animal.getId())){
            //TODO item already exists
        }

        if(animal.getId()<0 || animal.getName()==null || animal.getName().isEmpty()){
            //TODO animal credentials not valid
        }

        animalMap.put(animal.getId(), animal);
        return animalMap.get(animal.getId());
    }

    @PutMapping("/{id}")
    public Animal updateAnimal(@PathVariable int id, @RequestBody Animal animal){
        if(!animalMap.containsKey(id)){
        // TODO animal is not exist
        }
        if(animal.getId()<0 || animal.getName()==null || animal.getName().isEmpty()){
            //TODO animal credentials not valid
        }
        animalMap.put(id, new Animal(id, animal.getName()));
        return animalMap.get(id);
    }

    @DeleteMapping("/{id}")
    public Animal deleteAnimal(@PathVariable int id){
        if(!animalMap.containsKey(id)){
            //TODO Animal does not exist
        }
        Animal foundAnimal = animalMap.get(id);
        animalMap.remove(id);
        return foundAnimal;
    }


    //Bu methodda sınıfın sınıf yok olduğunda son çalışan methodudur.
    @PreDestroy
    public void destroy(){
        System.out.println("Animal Controller has been destroyed");
    }

}
