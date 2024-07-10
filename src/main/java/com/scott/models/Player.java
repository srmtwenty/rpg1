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
//@Table(name="players")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Player implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    //@Enumerated
    private Gender gender;

    private int hp;

    private int maxHp;

    private int mp;

    private int maxMp;

    private int strength;

    private int totalStrength;

    private int defense;

    private int totalDefense;

    //private int money;

    private int level=1;

    private int exp=0;

    private int maxExp=100;

    private String imageURL;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="armor_id", referencedColumnName = "id")
    private Armor armor;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="weapon_id", referencedColumnName = "id")
    private Weapon weapon;


    @ManyToMany(fetch= FetchType.LAZY)
    @JoinTable(
            name="players_items",
            joinColumns=@JoinColumn(name="player_id"),
            inverseJoinColumns=@JoinColumn(name="item_id")
    )
    private List<Item> items=new ArrayList<>();
    //private Set<Item> items=new HashSet<>();

    @ManyToMany(fetch= FetchType.LAZY)
    @JoinTable(
            name="players_enemies",
            joinColumns=@JoinColumn(name="player_id"),
            inverseJoinColumns=@JoinColumn(name="enemy_id")
    )
    private List<Enemy> enemies=new ArrayList<>();
    //private Set<Enemy> enemies=new HashSet<>();

    //@JsonIgnore
    //@OneToMany(mappedBy="player", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    //private List<Inventory> inventories=new ArrayList<>();

    //@ManyToOne(cascade= CascadeType.ALL)
    //@JoinColumn(name="inventory_id", referencedColumnName = "id")
    //private Inventory inventory;


    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="nation_id", referencedColumnName = "id")
    private Nation nation;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    @JsonIgnore
    @OneToOne(mappedBy="player")
    private Inventory inventory;

    public void equipArmor(Armor armor){
        this.armor=armor;
    }
    public void removeArmor(Armor armor){
        this.armor=null;
    }

    public void equipWeapon(Weapon weapon){
        this.weapon=weapon;
    }
    public void removeWeapon(Weapon weapon){
        this.weapon=null;
    }

    public void removeNation(Nation nation){
        this.nation=null;
    }

    public void removeUser(User user){
        this.user=null;
    }
}
