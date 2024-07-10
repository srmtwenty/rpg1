package com.scott.controllers;

import com.scott.models.Colosseum;
import com.scott.models.Enemy;
import com.scott.repositories.ColosseumRepository;
import com.scott.repositories.EnemyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colosseums")
@CrossOrigin(origins="http://localhost:3000")
public class ColosseumController {
    @Autowired
    ColosseumRepository colosseumRepository;

    @Autowired
    EnemyRepository enemyRepository;
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public Colosseum createColosseum(@RequestBody Colosseum colosseum){
        return colosseumRepository.save(colosseum);
    }

    @GetMapping()
    public List<Colosseum> findAllColosseums(){
        return colosseumRepository.findAll();
    }
    @GetMapping("/{id}")
    public Colosseum findColosseumById(@PathVariable Long id){
        return colosseumRepository.findById(id).get();
    }
    @PutMapping("/{id}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public Colosseum updateColosseum(@PathVariable Long id, @RequestBody Colosseum colosseum){
        Colosseum updateC=this.findColosseumById(id);
        updateC.setName(colosseum.getName());
        return colosseumRepository.save(updateC);
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteColosseum(@PathVariable Long id){
        colosseumRepository.deleteById(id);
    }

    @PutMapping("/{id}/addEnemy/{enemyId}")
    public Colosseum addEnemy(@PathVariable Long id, @PathVariable Long enemyId){
        Colosseum colosseum=colosseumRepository.findById(id).get();
        Enemy enemy=enemyRepository.findById(enemyId).get();
        colosseum.addEnemy(enemy);
        return colosseumRepository.save(colosseum);
    }

    @PutMapping("/{id}/removeEnemy/{enemyId}")
    public Colosseum removeEnemy(@PathVariable Long id, @PathVariable Long enemyId){
        Colosseum colosseum=colosseumRepository.findById(id).get();
        Enemy enemy=enemyRepository.findById(enemyId).get();
        colosseum.removeEnemy(enemy);
        return colosseumRepository.save(colosseum);
    }
}
