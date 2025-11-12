package com.ichiraku.model;

public class Customer {
    // --- Fields ---
    private final String id;
    private final String name;
    private final String contactNumber;
    private final String email;

    // --- Constructor ---
    public Customer(String id, String name, String contactNumber, String email) {
        this.id = id;
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public Customer(String id, String name) {
        this(id, name, null, null);
    }

    // --- Getters ---
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Customer: " + name +
                (contactNumber != null ? " | Contact: " + contactNumber : "") +
                (email != null ? " | Email: " + email : "");
    }
}
