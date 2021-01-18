package com.example.TransportationManagement.Entities;

import androidx.annotation.NonNull;

public class Company {
    String name;
    String email;
    long phone;

    public Company(String email, long phone) {
        this.email = email;
        this.phone = phone;
        this.name = email.split("@")[0];
    }

    public Company() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getName() {
        return name == null ? email.split("@")[0] : name;
    }

    public String getEmail() {
        return email;
    }

    public long getPhone() {
        return phone;
    }

    @NonNull
    @Override
    public String toString() {
        return getName();
    }
}
