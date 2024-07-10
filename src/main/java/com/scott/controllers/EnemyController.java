package com.scott.controllers;

import com.scott.models.Enemy;
import com.scott.repositories.EnemyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enemies")
@CrossOrigin(origins="http://localhost:3000")
public class EnemyController {
    @Autowired
    EnemyRepository enemyRepository;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public Enemy createEnemy(@RequestBody Enemy enemy){
        return enemyRepository.save(enemy);
    }
    @GetMapping
    public List<Enemy> findAllEnemies(){
        return enemyRepository.findAll();
    }
    @GetMapping("/{id}")
    public Enemy findEnemyById(@PathVariable Long id){
        return enemyRepository.findById(id).orElseThrow(()->new RuntimeException("Enemy has not been found!"));
    }
    @PutMapping("/{id}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public Enemy updateEnemy(@PathVariable Long id, @RequestBody Enemy enemy){
        Enemy updateE=this.findEnemyById(id);
        updateE.setName(enemy.getName());
        updateE.setDescription(enemy.getDescription());
        updateE.setHp(enemy.getMaxHp());
        updateE.setMp(enemy.getMaxMp());
        updateE.setMaxHp(enemy.getMaxHp());
        updateE.setMaxMp(enemy.getMaxMp());
        updateE.setStrength(enemy.getStrength());
        updateE.setDefense(enemy.getDefense());
        updateE.setExp(enemy.getExp());
        updateE.setMoney(enemy.getMoney());
        updateE.setImageURL(enemy.getImageURL());
        return enemyRepository.save(updateE);
    }
    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteEnemy(@PathVariable Long id){
        enemyRepository.deleteById(id);
    }
    @PutMapping("/{id}/enemyRecover")
    public Enemy enemyRecover(@PathVariable Long id){
        Enemy updateE=this.findEnemyById(id);
        updateE.setHp(updateE.getMaxHp());
        return enemyRepository.save(updateE);
    }
}
