package com.scott.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

@Entity
//@AllArgsConstructor
@NoArgsConstructor
public class Filedb implements Serializable {
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid", strategy="uuid2")
    private String id;

    private String name;

    private String type;

    //Lob is datatype for storing large objet data
    @Lob
    private byte[] data;

    //private String description;

    //@JsonIgnore
    //@ManyToMany(mappedBy="images")
    //private Set<Person> people=new HashSet<>();

    //public Filedb(){
    //}
    public Filedb(String name, String type, byte[] data){
        this.name=name;
        this.type=type;
        this.data=data;
        //this.description=description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    //public String getDescription() {
    //    return description;
    //}

    //public void setDescription(String description) {
    //    this.description = description;
    //}

    //public Set<Person> getPeople() {
    //    return people;
    //}

    //public void setPeople(Set<Person> people) {
    //    this.people = people;
    //}
}
