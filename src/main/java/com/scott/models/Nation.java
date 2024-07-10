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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Nation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy="nation")
    @JsonIgnore
    private List<User> users=new ArrayList<>();
    //private Set<User> users=new HashSet<>();

    @OneToMany(mappedBy="nation")
    @JsonIgnore
    private List<Player> players=new ArrayList<>();
    //private Set<Player> players=new HashSet<>();
}
