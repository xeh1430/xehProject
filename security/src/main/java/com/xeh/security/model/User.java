package com.xeh.security.model;

import java.util.List;

/**
 * @author: xeh
 * @date: 2019/1/25 23:10
 * @description:
 */
public class User {

    private int id;
    private String username;
    private String password;

    private List<Role> roles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
