package com.shop.model;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class CustomUserDetails extends BaseModel{

    @Column(name = "username", length = 25, nullable = false)
    private String username;
    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "email", length = 255, nullable = true)
    private String email;

    @Column(name = "phone", length = 255, nullable = true)
    private String phone;

    @Column(name = "address", length = 255, nullable = true)
    private String address;

    @Column(name = "role", length = 50, nullable = false)
    private String role;

    @Column(name = "token", length = 255, nullable = true)
    private String token;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}