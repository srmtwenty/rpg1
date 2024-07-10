package com.scott.controllers;

import com.scott.models.Item;
import com.scott.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@CrossOrigin("http://localhost:3000")
public class ItemController {
    @Autowired
    ItemRepository itemRepository;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public Item createItem(@RequestBody Item item){
        return itemRepository.save(item);
    }

    @GetMapping
    public List<Item> findAllItems(){
        return itemRepository.findAll();
    }

    @GetMapping("/{id}")
    public Item findItemById(@PathVariable Long id){
        return itemRepository.findById(id).orElseThrow(()->new RuntimeException("Item has not been found!"));
    }
    @PutMapping("/{id}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public Item updateItem(@PathVariable Long id, @RequestBody Item item){
        Item updateI=this.findItemById(id);
        updateI.setName(item.getName());
        updateI.setPrice(item.getPrice());
        updateI.setDescription(item.getDescription());
        updateI.setNumber(item.getNumber());
        updateI.setImageURL(item.getImageURL());
        return itemRepository.save(updateI);
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteItem(@PathVariable Long id){
        itemRepository.deleteById(id);
    }
}
