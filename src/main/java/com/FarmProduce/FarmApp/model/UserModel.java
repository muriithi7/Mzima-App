package com.FarmProduce.FarmApp.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Entity
public class UserModel implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String username;
    private String password;

    @Column(name = "profile_image_path") // Adjust the column name to match your database schema
    private String profileImagePath; // Profile image path
    @ManyToMany(fetch = FetchType.EAGER)// load all the roles each time a user is loaded
    private Collection<rolesModel> roles = new ArrayList<>();

    @CreatedDate
    @Column(nullable = false,updatable = false)
    private Date date_created;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }
    @PrePersist
    protected void onCreate() {
        date_created = new Date(); // Set the current date when the entity is persisted
    }
    public void setUsername(String username) {

        this.username = username;
    }


    public void setPassword(String password) {

        this.password = password;
    }

    public Collection<rolesModel> getRoles() {
        return roles;
    }

    public void setRoles(Collection<rolesModel> roles) {
        this.roles = roles;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // return the user's roles as a collection of GrantedAuthority objects
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // assuming accounts never expire
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // assuming accounts are never locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // assuming credentials never expire
    }

    @Override
    public boolean isEnabled() {
        return true; // assuming all accounts are enabled by default
    }

}