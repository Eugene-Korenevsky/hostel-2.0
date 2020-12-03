package com.example.hostel1.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "role")
public class Role implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    public long getId() {
        return id;
    }

    @NotNull
    @Column(name = "NAME")
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return (role.getId() == this.getId() &&
                role.getRole().equals(this.getRole()));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + (int) id;
        result = 37 * result + role.hashCode();
        return result;
    }
}
