package com.example.hostel1.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
@Data
@Entity
@Table(name = "description")
public class Description implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @NotNull
    @Column(name = "NAME")
    private String description;


    @ManyToMany(mappedBy = "descriptions")
    private Set<Room> rooms = new HashSet<>();


    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Description description = (Description) o;
        return  (description.getId() == this.getId() &&
                description.getDescription().equals(this.getDescription()));
    }
    @Override
    public int hashCode(){
        int result = 17;
        result = 37 * result + (int)id;
        result = 37 * result + description.hashCode();
        return result;
    }
}
