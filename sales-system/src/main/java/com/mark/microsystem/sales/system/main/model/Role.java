package com.mark.microsystem.sales.system.main.model;

public enum Role {

    ADMIN("Administrator"),
    SELLER("Seller");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Role{" +
                "description='" + description + '\'' +
                '}';
    }
}
