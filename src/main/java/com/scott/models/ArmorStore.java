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
public class ArmorStore implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(fetch= FetchType.LAZY)
    @JoinTable(
            name="armorStores_armors",
            joinColumns=@JoinColumn(name="armorStore_id"),
            inverseJoinColumns=@JoinColumn(name="armor_id")
    )
    private List<Armor> armorsForArmorStores=new ArrayList<>();

    public void addArmor(Armor armor){
        armorsForArmorStores.add(armor);
    }
    public void removeArmor(Armor armor){
        armorsForArmorStores.remove(armor);
    }
}
