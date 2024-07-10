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
//@Table(name="weapons")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Weapon implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int strength;

    private int price;

    private int number;
    
    private String description;

    private String imageURL;

    @JsonIgnore
    @OneToMany(mappedBy = "weapon")
    private List<Player> players=new ArrayList<>();
    //private Set<Player> players=new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy="weaponsForInventories")
    private List<Inventory> inventories;
}
