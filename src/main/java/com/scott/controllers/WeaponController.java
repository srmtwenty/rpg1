package com.scott.controllers;

import com.scott.models.Weapon;
import com.scott.repositories.WeaponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weapons")
@CrossOrigin(origins="http://localhost:3000")
public class WeaponController {

    @Autowired
    WeaponRepository weaponRepository;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public Weapon createWeapon(@RequestBody Weapon weapon){
        return weaponRepository.save(weapon);
    }
    @GetMapping
    public List<Weapon> findAllWeapons(){
        return weaponRepository.findAll();
    }
    @GetMapping("/{weaponId}")
    public Weapon findWeaponById(@PathVariable Long weaponId){
        return weaponRepository.findById(weaponId).orElseThrow(()->new RuntimeException("Weapon has not been found!"));
    }

    @PutMapping("/{weaponId}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public Weapon updateWeapon(@PathVariable Long weaponId, @RequestBody Weapon weapon){
        Weapon updateW=this.findWeaponById(weaponId);
        updateW.setName(weapon.getName());
        updateW.setDescription(weapon.getDescription());
        updateW.setStrength(weapon.getStrength());
        updateW.setPrice(weapon.getPrice());
        updateW.setNumber(weapon.getNumber());
        updateW.setImageURL(weapon.getImageURL());
        return weaponRepository.save(updateW);
    }

    @DeleteMapping("/{weaponId}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteWeapon(@PathVariable Long weaponId){
        weaponRepository.deleteById(weaponId);
    }
}
