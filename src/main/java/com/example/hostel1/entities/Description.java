package com.example.hostel1.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @Size(min = 1, message = "description must have at least 1 letter")
    private String description;


    @ManyToMany(mappedBy = "descriptions")
    private Set<Room> rooms = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Description description = (Description) o;
        return (description.getId() == this.getId() &&
                description.getDescription().equals(this.getDescription()));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + (int) id;
        result = 37 * result + description.hashCode();
        return result;
    }
}
