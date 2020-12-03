package com.example.hostel1.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Table(name = "users")
public class User implements Serializable {
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
    @Size(min = 2, message = "name min size = 2")
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @NotNull
    @Column(name = "SURNAME")
    @Size(min = 2, message = "surname min size = 2")
    private String surname;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @NotNull
    @Column(name = "EMAIL")
    @Pattern(regexp = "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$", message = "wrong email")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull
    @Column(name = "PASSWORD")
    @Size(min = 3, message = "password is too short")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToOne
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")
    private Role role;

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return (user.getId() == this.getId() && user.getEmail().equals(this.getEmail())
                && user.getName().equals(this.getName()) && user.getPassword().equals(this.getPassword())
                && user.getRole().equals(this.getRole()) && user.getSurname().equals(this.getSurname()));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + (int) id;
        result = 37 * result + email.hashCode();
        result = 37 * result + name.hashCode();
        result = 37 * result + password.hashCode();
        result = 37 * result + role.hashCode();
        result = 37 * result + surname.hashCode();
        return result;
    }
}
