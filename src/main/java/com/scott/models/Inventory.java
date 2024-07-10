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

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@Table(name="inventories")
@Entity
public class Inventory implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    //private boolean hasUser=false;
    //@OneToOne(fetch= FetchType.LAZY)
    //@JoinColumn(name="player_id")


    //@ManyToOne(cascade=CascadeType.ALL)
    //@JoinColumn(name="inventory_id", referencedColumnName = "id")
    //private Player player;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="player_id", referencedColumnName = "id")
    private Player player;

    //@JsonIgnore
    //@OneToMany(mappedBy="inventory", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    //private List<Player> players=new ArrayList<>();


    //@ManyToMany(mappedBy="inventories")
    //@JsonIgnore
    //private List<Armor> armors;

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="inventories_armors",
            joinColumns = @JoinColumn(name="inventory_id"),
            inverseJoinColumns=@JoinColumn(name="armor_id")
    )
    private List<Armor> armorsForInventories;


    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="inventories_items",
            joinColumns = @JoinColumn(name="inventory_id"),
            inverseJoinColumns=@JoinColumn(name="item_id")
    )
    private List<Item> itemsForInventories;

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="inventories_weapons",
            joinColumns = @JoinColumn(name="inventory_id"),
            inverseJoinColumns=@JoinColumn(name="weapon_id")
    )
    private List<Weapon> weaponsForInventories;

/*
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="inventories_nations",
            joinColumns = @JoinColumn(name="inventory_id"),
            inverseJoinColumns=@JoinColumn(name="nation_id")
    )
    private List<Nation> nations;
*/
    @JsonIgnore
    @OneToOne(mappedBy="inventory")
    private User user;

    private Long money;
    public void addArmor(Armor armor){
        armorsForInventories.add(armor);
    }
    public void removeArmor(Armor armor){
        armorsForInventories.remove(armor);
    }

    public void addItem(Item item){
        itemsForInventories.add(item);
    }
    public void removeItem(Item item){
        itemsForInventories.remove(item);
    }

    public void addWeapon(Weapon weapon){
        weaponsForInventories.add(weapon);
    }
    public void removeWeapon(Weapon weapon){
        weaponsForInventories.remove(weapon);
    }

    public void removePlayer(Player player){
        this.player=null;
    }


}
