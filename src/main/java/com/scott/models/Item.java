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
//@Table(name="items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int price;

    private String description;

    private int number;

    private String imageURL;

    @JsonIgnore
    @ManyToMany(mappedBy="items")
    private List<Player> players;
    //private Set<Player> players=new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy="itemsForEnemies")
    private List<Enemy> enemies;

    @JsonIgnore
    @ManyToMany(mappedBy="itemsForInventories")
    private List<Inventory> inventories;
}
