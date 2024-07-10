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
//@Table(name="armors")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Armor implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int defense;

    private int price;

    private int number;

    private String description;

    private String imageURL;

    @JsonIgnore
    @OneToMany(mappedBy="armor")
    private List<Player> players;
    //private Set<Player> players=new HashSet<>();

    @ManyToMany(mappedBy="armorsForInventories")
    @JsonIgnore
    private List<Inventory> inventories=new ArrayList<>();

    @ManyToMany(mappedBy="armorsForArmorStores")
    @JsonIgnore
    private List<ArmorStore> armorStores=new ArrayList<>();
    public void addInventory(Inventory inventory){
        inventories.add(inventory);
    }


}
