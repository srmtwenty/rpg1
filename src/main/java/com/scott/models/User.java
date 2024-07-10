package com.scott.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Table(name="users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames="username"),
                @UniqueConstraint(columnNames="email")
        })
@Entity
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max=20)
    private String username;

    @NotBlank
    @Size(max=50)
    @Email
    private String email;

    @NotBlank
    @Size(max=120)
    private String password;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="nation_id", referencedColumnName = "id")
    private Nation nation;

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="role_id")
    )
    private Set<Role> roles=new HashSet<>();

    @JsonIgnore
    @OneToOne(mappedBy="user")
    private Player player;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="inventory_id", referencedColumnName = "id")
    private Inventory inventory;


    public User(String username, String email, String password){
        this.username=username;
        this.email=email;
        this.password=password;

    }

    public void removeNation(Nation nation){
        this.nation=null;
    }

    public void removeInventory(Inventory inventory){
        this.inventory=null;
    }
}
