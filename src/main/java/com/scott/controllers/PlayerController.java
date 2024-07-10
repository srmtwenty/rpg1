package com.scott.controllers;


import com.scott.models.*;
import com.scott.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/players")
@CrossOrigin(origins="http://localhost:3000")
public class PlayerController {
    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    ArmorRepository armorRepository;

    @Autowired
    WeaponRepository weaponRepository;

    @Autowired
    NationRepository nationRepository;

    @Autowired
    EnemyRepository enemyRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserRepository userRepository;
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Player createPlayer(@RequestBody Player player){
        return playerRepository.save(player);
    }
    @GetMapping
    public List<Player> findAllPlayers(){
        return playerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Player findPlayerById(@PathVariable Long id){
        return playerRepository.findById(id).orElseThrow(()->new RuntimeException("User has not been found!"));
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Player updatePlayer(@PathVariable Long id, @RequestBody Player player){
        Player updateP=this.findPlayerById(id);
        updateP.setName(player.getName());
        updateP.setAge(player.getAge());
        updateP.setGender(player.getGender());
        updateP.setHp(player.getMaxHp());
        updateP.setMp(player.getMaxMp());
        updateP.setMaxHp(player.getMaxHp());
        updateP.setMaxMp(player.getMaxMp());
        updateP.setStrength(player.getStrength());
        updateP.setDefense(player.getDefense());
        updateP.setImageURL(player.getImageURL());
        return playerRepository.save(updateP);
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public void deletePlayer(@PathVariable Long id){
        playerRepository.deleteById(id);
    }

    @PutMapping("/{id}/equipA/{armorId}")
    //@PreAuthorize("hasRole('ADMIN')")
    public Player equipPlayerArmor(@PathVariable Long id, @PathVariable Long armorId){
        Player updateP=playerRepository.findById(id).get();
        Armor armor=armorRepository.findById(armorId).get();
        updateP.equipArmor(armor);
        updateP.setTotalDefense(updateP.getDefense()+armor.getDefense());
        return playerRepository.save(updateP);
    }

    @PutMapping("/{id}/removeA/{armorId}")
    //@PreAuthorize("hasRole('ADMIN')")
    public Player removePlayerArmor(@PathVariable Long id, @PathVariable Long armorId){
        Player updateP=playerRepository.findById(id).get();
        Armor armor=armorRepository.findById(armorId).get();
        updateP.setTotalDefense(updateP.getTotalDefense()-armor.getDefense());
        updateP.removeArmor(armor);
        return playerRepository.save(updateP);
    }


    @PutMapping("/{id}/equipW/{weaponId}")
    //@PreAuthorize("hasRole('ADMIN')")
    public Player equipPlayerWeapon(@PathVariable Long id, @PathVariable Long weaponId){
        Player updateP=playerRepository.findById(id).get();
        Weapon weapon=weaponRepository.findById(weaponId).get();
        updateP.equipWeapon(weapon);
        updateP.setTotalStrength(updateP.getStrength()+weapon.getStrength());
        return playerRepository.save(updateP);
    }

    @PutMapping("/{id}/removeW/{weaponId}")
    //@PreAuthorize("hasRole('ADMIN')")
    public Player removePlayerWeapon(@PathVariable Long id, @PathVariable Long weaponId){
        Player updateP=playerRepository.findById(id).get();
        Weapon weapon=weaponRepository.findById(weaponId).get();
        updateP.setTotalStrength(updateP.getTotalStrength()-weapon.getStrength());
        updateP.removeWeapon(weapon);

        return playerRepository.save(updateP);
    }

    @PutMapping("/{id}/assignNation/{nationId}")
    //@PreAuthorize("hasRole('ADMIN')")
    public Player assignNation(@PathVariable Long id, @PathVariable Long nationId){
        Player updateP=playerRepository.findById(id).get();
        Nation nation=nationRepository.findById(nationId).get();
        updateP.setNation(nation);
        System.out.println("updated!");
        return playerRepository.save(updateP);
    }

    @PutMapping("/{id}/removeNation/{nationId}")
    //@PreAuthorize("hasRole('ADMIN')")
    public Player removeNation(@PathVariable Long id, @PathVariable Long nationId){
        Player updateP=playerRepository.findById(id).get();
        Nation nation=nationRepository.findById(nationId).get();
        updateP.removeNation(nation);
        System.out.println("updated!");
        return playerRepository.save(updateP);
    }

    @PutMapping("/{id}/betting")
    public Player betting(@PathVariable Long id){
        Player updateP=playerRepository.findById(id).get();
        int number=(int)(Math.random()*(500 - -500)+ -500);
        System.out.println(number);
        updateP.getUser().getInventory().setMoney(updateP.getUser().getInventory().getMoney()-200 + number);

        if(updateP.getUser().getInventory().getMoney()<0){
            updateP.getUser().getInventory().setMoney(Long.valueOf(0));
        }
        return playerRepository.save(updateP);
    }

    @PutMapping("/{id}/battle/{enemyId}")
    public Player battleOneTurn(@PathVariable Long id, @PathVariable Long enemyId) {

        Player updateP = playerRepository.findById(id).get();
        Enemy updateE = enemyRepository.findById(enemyId).get();
        updateP.getUser().getInventory().setMoney(updateP.getUser().getInventory().getMoney() + updateE.getMoney());
        updateP.setExp(updateP.getExp() + updateE.getExp());
        if (updateP.getExp() > updateP.getMaxExp()) {
            updateP.setExp(updateP.getExp() - updateP.getMaxExp());
            updateP.setMaxExp((updateP.getMaxExp() + 100));

        }
        System.out.println("You earned " + updateE.getMoney());
        return playerRepository.save(updateP);
    }
    /*
    @PutMapping("/{id}/battle/{enemyId}")
    public Player battleOneTurn(@PathVariable Long id, @PathVariable Long enemyId) {
        Player updateP = playerRepository.findById(id).get();
        Enemy updateE = enemyRepository.findById(enemyId).get();
        //Inventory inventory=updateP.getUser().getInventory();
        long damageE=updateP.getTotalStrength()-updateE.getDefense();
        System.out.println("Player attacks!");
        if(damageE<0){
            updateE.setHp(updateE.getHp()-0);
            System.out.println("Enemy has taken 0 damage");
        }else{

            updateE.setHp(updateE.getHp() - (updateP.getTotalStrength()-updateE.getDefense()));
            System.out.println("Enemy has taken "+ damageE +" damages");
        }
        enemyRepository.save(updateE);
        if (updateE.getHp() <= 0) {
            updateE.setHp(0);
            System.out.println("Enemy HP: " + updateE.getHp());
            System.out.println("You win!");
            updateP.getUser().getInventory().setMoney(updateP.getUser().getInventory().getMoney() + updateE.getMoney());
            updateP.setExp(updateP.getExp() + updateE.getExp());
            if(updateP.getExp()>updateP.getMaxExp()){
                updateP.setExp(updateP.getExp()-updateP.getMaxExp());
                updateP.setMaxExp((updateP.getMaxExp()+100));

            }
            System.out.println("You earned " + updateE.getMoney());
            System.out.println("Game Set");

        }
        System.out.println("Enemy HP:" + updateE.getHp());

        if (updateE.getHp() > 0) {
            System.out.println("Enemy attacks!");
            if(updateE.getStrength()-updateP.getTotalDefense()<0){

                updateP.setHp(updateP.getHp() - 0);
                System.out.println("Player has taken 0 damage");
            }else{
                long damageP=updateE.getStrength()-updateP.getTotalDefense();
                updateP.setHp(updateP.getHp() - (updateE.getStrength()-updateP.getTotalDefense()));
                System.out.println("Player has taken "+ damageP +" damages");
            }

            playerRepository.save(updateP);
            if (updateP.getHp() <= 0) {
                updateP.setHp(0);
                System.out.println("Player HP:" + updateP.getHp());
                System.out.println("You lose!");
                System.out.println("Game Set");
            }
            System.out.println("Player HP:" + updateP.getHp());
        }
        enemyRepository.save(updateE);
        return playerRepository.save(updateP);
    }

     */
    @PutMapping("/{id}/recovery")
    public Player fullRecovery(@PathVariable Long id){
        Player updateP=this.findPlayerById(id);
        updateP.setHp(updateP.getMaxHp());
        return playerRepository.save(updateP);
    }

    @GetMapping("/{id}/inventory")
    public Inventory getPlayerForInventory(@PathVariable Long id){
        Player player=this.findPlayerById(id);
        return player.getInventory();
    }
    //purchase armor
    @PutMapping("/{id}/purchaseArmor/{armorId}")
    public Player purchaseArmor(@PathVariable Long id, @PathVariable Long armorId){
        Player player=this.findPlayerById(id);
        Armor armor=armorRepository.findById(armorId).get();
        Inventory inventory=player.getInventory();
        inventory.addArmor(armor);
        if(inventory.getArmorsForInventories().contains(armor)){
            armor.setNumber(armor.getNumber()+1);
            armorRepository.save(armor);
        }else{
            inventory.addArmor(armor);
        }
        inventory.setMoney(inventory.getMoney()-armor.getPrice());
        inventoryRepository.save(inventory);
        player.setInventory(inventory);
        return playerRepository.save(player);
    }


    //purchase weapon
    @PutMapping("/{id}/purchaseWeapon/{weaponId}")
    public Player purchaseWeapon(@PathVariable Long id, @PathVariable Long weaponId){
        Player player=this.findPlayerById(id);
        Weapon weapon=weaponRepository.findById(weaponId).get();
        Inventory inventory=player.getInventory();
        inventory.addWeapon(weapon);
        if(inventory.getWeaponsForInventories().contains(weapon)){
            weapon.setNumber(weapon.getNumber()+1);
            weaponRepository.save(weapon);
        }else{
            inventory.addWeapon(weapon);
        }
        inventory.setMoney(inventory.getMoney()-weapon.getPrice());
        inventoryRepository.save(inventory);
        player.setInventory(inventory);
        return playerRepository.save(player);
    }
    //purchase item
    @PutMapping("/{id}/purchaseItem/{itemId}")
    public Player purchaseItem(@PathVariable Long id, @PathVariable Long itemId){
        Player player=this.findPlayerById(id);
        Item item=itemRepository.findById(itemId).get();
        Inventory inventory=player.getInventory();
        inventory.addItem(item);
        if(inventory.getItemsForInventories().contains(item)){
            item.setNumber(item.getNumber()+1);
            itemRepository.save(item);
        }else{
            inventory.addItem(item);
        }
        inventory.setMoney(inventory.getMoney()-item.getPrice());
        inventoryRepository.save(inventory);
        player.setInventory(inventory);
        return playerRepository.save(player);
    }

    @GetMapping("/{id}/inventory_weapons")
    public List<Weapon> weaponsInInventory(@PathVariable Long id, @PathVariable Long weaponId){
        Player player=playerRepository.findById(id).get();
        Inventory inventory=player.getInventory();
        return inventory.getWeaponsForInventories();
    }

    @PutMapping("/{id}/assignUser/{userId}")

    public Player assignUser(@PathVariable Long id, @PathVariable Long userId){
        Player updateP=playerRepository.findById(id).get();
        User user=userRepository.findById(userId).get();
        updateP.setUser(user);
        System.out.println("User has been added!");
        return playerRepository.save(updateP);
    }

    @PutMapping("/{id}/removeUser/{userId}")
    public Player removeUser(@PathVariable Long id, @PathVariable Long userId){
        Player updateP=playerRepository.findById(id).get();
        User user=userRepository.findById(userId).get();
        updateP.removeUser(user);
        System.out.println("User has been removed!");
        return playerRepository.save(updateP);
    }
}