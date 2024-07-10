package com.scott.controllers;

import com.scott.models.*;
import com.scott.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventories")
@CrossOrigin(origins="http://localhost:3000")
public class InventoryController {
    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    ArmorRepository armorRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    WeaponRepository weaponRepository;
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public Inventory createInventory(@RequestBody Inventory inventory){
        return inventoryRepository.save(inventory);
    }
    @GetMapping
    public List<Inventory> findAllInventories(){
        return inventoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public Inventory findInventoryById(@PathVariable Long id){
        return inventoryRepository.findById(id).orElseThrow(()->new RuntimeException("Inventory has not been found!"));
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public Inventory updateInventory(@PathVariable Long id, @RequestBody Inventory inventory){
        Inventory updateI=this.findInventoryById(id);
        updateI.setName(inventory.getName());
        updateI.setDescription(inventory.getDescription());
        updateI.setMoney(inventory.getMoney());
        //updateI.setPlayer(inventory.getPlayer());
        return inventoryRepository.save(updateI);
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteInventory(@PathVariable Long id){
        inventoryRepository.deleteById(id);
    }

    //@PutMapping("/{id}/setPlayer/{playerId}")
    //@PreAuthorize("hasRole('ADMIN')")
    //public Inventory setPlayer(@PathVariable Long id, @PathVariable Long playerId){
    //    Inventory updateI=this.findInventoryById(id);
    //    Player player=playerRepository.findById(playerId).get();
    //    updateI.setPlayer(player);
    //    return inventoryRepository.save(updateI);
    //}
    //@PutMapping("/{id}/removePlayer/{playerId}")
    //@PreAuthorize("hasRole('ADMIN')")
    //public Inventory removePlayer(@PathVariable Long id, @PathVariable Long playerId){
    //    Inventory updateI=this.findInventoryById(id);
    //    Player player=playerRepository.findById(playerId).get();
    //    updateI.removePlayer(player);
    //    return inventoryRepository.save(updateI);
    //}

    @PutMapping("/{id}/assignPlayer/{playerId}")
    public Inventory assignPlayer(@PathVariable Long id, @PathVariable Long playerId){
        Inventory updateI=this.findInventoryById(id);
        Player player=playerRepository.findById(playerId).get();
        updateI.setPlayer(player);
        return inventoryRepository.save(updateI);
    }
    @PutMapping("/{id}/removePlayer/{playerId}")
    public Inventory removePlayer(@PathVariable Long id, @PathVariable Long playerId){
        Inventory updateI=this.findInventoryById(id);
        Player player=playerRepository.findById(playerId).get();
        updateI.removePlayer(player);
        return inventoryRepository.save(updateI);
    }

    @GetMapping("/{id}/armors")
    public List<Armor> getArmorsForInventory(@PathVariable Long id){
        Inventory updateI=this.findInventoryById(id);
        return updateI.getArmorsForInventories();
    }
    @GetMapping("/{id}/weapons")
    public List<Weapon> getWeaponsForInventory(@PathVariable Long id){
        Inventory updateI=this.findInventoryById(id);
        return updateI.getWeaponsForInventories();
    }
    @GetMapping("/{id}/items")
    public List<Item> getItemsForInventory(@PathVariable Long id){
        Inventory updateI=this.findInventoryById(id);
        return updateI.getItemsForInventories();
    }

    //purchase and add armor
    @PutMapping("/{id}/purchaseArmor/{armorId}")
    //@PreAuthorize("hasRole('ADMIN')")
    public Inventory purchaseArmor(@PathVariable Long id, @PathVariable Long armorId){
        Inventory updateI=this.findInventoryById(id);
        Armor armor=armorRepository.findById(armorId).get();
        if(updateI.getArmorsForInventories().contains(armor)) {
            System.out.println("Yes");
            armor.setNumber(armor.getNumber()+1);
            armorRepository.save(armor);
        }else{
            System.out.println("No");
            updateI.addArmor(armor);
        }
        //}else{

        //}
        updateI.setMoney(updateI.getMoney()-armor.getPrice());
        return inventoryRepository.save(updateI);
    }

    //purchase and add weapon
    @PutMapping("/{id}/purchaseWeapon/{weaponId}")
    //@PreAuthorize("hasRole('ADMIN')")
    public Inventory purchaseWeapon(@PathVariable Long id, @PathVariable Long weaponId){
        Inventory updateI=this.findInventoryById(id);
        Weapon weapon=weaponRepository.findById(weaponId).get();
        if(updateI.getWeaponsForInventories().contains(weapon)){
            System.out.println("Yes");
            weapon.setNumber(weapon.getNumber()+1);
            weaponRepository.save(weapon);
        }else{
            System.out.println("No");
            updateI.addWeapon(weapon);
        }
        updateI.setMoney(updateI.getMoney()-weapon.getPrice());
        return inventoryRepository.save(updateI);
    }

    //purchase and add item
    @PutMapping("/{id}/purchaseItem/{itemId}")
    //@PreAuthorize("hasRole('ADMIN')")
    public Inventory purchaseItem(@PathVariable Long id, @PathVariable Long itemId){
        Inventory updateI=this.findInventoryById(id);
        Item item=itemRepository.findById(itemId).get();
        if(updateI.getItemsForInventories().contains(item)){
            System.out.println("Yes");
            item.setNumber(item.getNumber()+1);
            itemRepository.save(item);
        }else{
            System.out.println("No");
            updateI.addItem(item);
        }
        updateI.setMoney(updateI.getMoney()-item.getPrice());
        return inventoryRepository.save(updateI);
    }


    @GetMapping("/{id}/getUser")
    public User getUser(@PathVariable Long id){
        Inventory inventory=this.findInventoryById(id);
        return inventory.getUser();
    }

    @GetMapping("/{id}/getPlayer")
    public Player getPlayer(@PathVariable Long id){
        Inventory inventory=this.findInventoryById(id);
        return inventory.getUser().getPlayer();
    }
}
