package com.workintech.springboot.springbootfundamentalsrest.controller;

import com.workintech.springboot.springbootfundamentalsrest.entity.Animal;
import com.workintech.springboot.springbootfundamentalsrest.mapping.AnimalResponse;
import com.workintech.springboot.springbootfundamentalsrest.validation.AnimalValidation;
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

    //Hard dependency yerine constructor ile de instance yaratılabilir.
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
    public AnimalResponse getAnimal(@PathVariable int id){
        if(!AnimalValidation.isIdValid(id)){
         //TODO id is not valid
            return new AnimalResponse(null, "Id is not valid", 400);
        }
        if(!AnimalValidation.isMapContainsKey(animalMap, id)){
            //TODO map does not contain id
            return new AnimalResponse(null, "Animal does not exist", 400);
        }
        // return animalMap.get(id) yerine animalresponse dönerek message ve status bilgileri de dönüşe enjekte edildi.
        return new AnimalResponse(animalMap.get(id), "Success",200 );
    }

    @PostMapping
    public AnimalResponse addAnimal(@RequestBody Animal animal){
        if(AnimalValidation.isMapContainsKey(animalMap, animal.getId())){
            //TODO item already exists
            return new AnimalResponse(null, "Animal already exists", 400);
        }

        if(!AnimalValidation.isAnimalCredentialsValid(animal)){
            //TODO animal credentials not valid
            return new AnimalResponse(null, "Animal credentials are not valid", 400);
        }

        animalMap.put(animal.getId(), animal);
        return new AnimalResponse(animalMap.get(animal.getId()),"Success. Animal has been added", 201);
    }

    @PutMapping("/{id}")
    public AnimalResponse updateAnimal(@PathVariable int id, @RequestBody Animal animal){
        if(!AnimalValidation.isMapContainsKey(animalMap, animal.getId())){
        // TODO animal is not exist
            return new AnimalResponse(null, "Animal does not exist", 400);
        }
        if(!AnimalValidation.isAnimalCredentialsValid(animal)){
            //TODO animal credentials not valid
            return new AnimalResponse(null, "Animal credentials are not valid", 400);
        }
        animalMap.put(id, new Animal(id, animal.getName()));
        return new AnimalResponse(animalMap.get(id), "Success. Animal has been updated", 200);
    }

    @DeleteMapping("/{id}")
    public AnimalResponse deleteAnimal(@PathVariable int id){
        if(!AnimalValidation.isMapContainsKey(animalMap,id)){
            //TODO Animal does not exist
            return new AnimalResponse(null, "Animal does not exist", 400);
        }
        Animal foundAnimal = animalMap.get(id);
        animalMap.remove(id);
        return new AnimalResponse(foundAnimal, "Success. Animal has been deleted.", 200);
    }


    //Bu methodda sınıfın sınıf yok olduğunda son çalışan methodudur.
    @PreDestroy
    public void destroy(){
        System.out.println("Animal Controller has been destroyed");
    }

}
