package com.scott.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Enemy implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private int hp;

    private int maxHp;

    private int mp;

    private int maxMp;

    private int strength;

    private int totalStrength;

    private int defense;

    private int totalDefense;

    private Long money;

    private int exp=0;

    private String imageURL;

    @ManyToMany(fetch= FetchType.LAZY)
    @JoinTable(
            name="enemies_items",
            joinColumns=@JoinColumn(name="enemy_id"),
            inverseJoinColumns=@JoinColumn(name="item_id")
    )
    //private Set<Item> itemsForEnemies;
    private List<Item> itemsForEnemies=new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy="enemies")
    private List<Player> players=new ArrayList<>();
    //private Set<Player> players=new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy="enemies")
    private List<Colosseum> colosseums=new ArrayList<>();
}
