package com.scott.controllers;

import com.scott.models.Inventory;
import com.scott.models.Nation;
import com.scott.models.Player;
import com.scott.models.User;
import com.scott.repositories.InventoryRepository;
import com.scott.repositories.NationRepository;
import com.scott.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins="http://localhost:3000")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    NationRepository nationRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    @PostMapping("/create")
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }
    @GetMapping
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable Long id){
        return userRepository.findById(id).orElseThrow(()->new RuntimeException("User has not been found!"));
    }

    @PutMapping("/{id}/update")
    public User updateUser(@PathVariable Long id, @RequestBody User user){
        User updateU=this.findUserById(id);
        updateU.setUsername(user.getUsername());
        return userRepository.save(updateU);
    }

    @DeleteMapping("/{id}/delete")
    public void deleteUser(@PathVariable Long id){
        userRepository.deleteById(id);
    }

    @PutMapping("/{id}/assignNation/{nationId}")
    public User assignNation(@PathVariable Long id, @PathVariable Long nationId){
        User user=userRepository.findById(id).get();
        Nation nation=nationRepository.findById(nationId).get();
        user.setNation(nation);
        System.out.println("updated!");
        return userRepository.save(user);
    }
    @PutMapping("/{id}/removeNation/{nationId}")
    public User removeNation(@PathVariable Long id, @PathVariable Long nationId){
        User user=userRepository.findById(id).get();
        Nation nation=nationRepository.findById(nationId).get();
        user.removeNation(nation);
        System.out.println("updated!");
        return userRepository.save(user);
    }
    @GetMapping("/{id}/getPlayer")
    public Player getPlayer(@PathVariable Long id) {
        User user = userRepository.findById(id).get();
        return user.getPlayer();
    }
    @GetMapping("/{id}/getInventory")
    public Inventory getInventory(@PathVariable Long id) {
        User user = userRepository.findById(id).get();
        return user.getInventory();
    }

    @PutMapping("/{id}/assignInventory/{inventoryId}")
    public User assignInventory(@PathVariable Long id, @PathVariable Long inventoryId){
        User user=userRepository.findById(id).get();
        Inventory inventory=inventoryRepository.findById(inventoryId).get();
        user.setInventory(inventory);
        //inventory.setHasUser(true);
        //inventoryRepository.save(inventory);
        return userRepository.save(user);
    }
    @PutMapping("/{id}/removeInventory/{inventoryId}")
    public User removeInventory(@PathVariable Long id, @PathVariable Long inventoryId){
        User user=userRepository.findById(id).get();
        Inventory inventory=inventoryRepository.findById(inventoryId).get();
        user.removeInventory(inventory);
        //inventory.setHasUser(false);
        //inventoryRepository.save(inventory);
        return userRepository.save(user);
    }
}
