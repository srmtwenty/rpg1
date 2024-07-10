package com.scott.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Colosseum implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(fetch= FetchType.LAZY)
    @JoinTable(
            name="colosseums_enemies",
            joinColumns=@JoinColumn(name="colosseum_id"),
            inverseJoinColumns=@JoinColumn(name="enemy_id")
    )
    private List<Enemy> enemies=new ArrayList<>();

    public void addEnemy(Enemy enemy){
        enemies.add(enemy);
    }
    public void removeEnemy(Enemy enemy){
        enemies.remove(enemy);
    }
}
