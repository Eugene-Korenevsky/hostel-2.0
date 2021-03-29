package com.example.hostel1.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@EqualsAndHashCode
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


    @NotNull
    @Column(name = "SURNAME")
    @Size(min = 2, message = "surname min size = 2")
    private String surname;


    @NotNull
    @Column(name = "EMAIL")
    @Pattern(regexp = "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$", message = "wrong email")
    private String email;


    @NotNull
    @Column(name = "PASSWORD")
    @Size(min = 3, message = "password is too short")
    private transient String password;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")
    private Role role;

}
