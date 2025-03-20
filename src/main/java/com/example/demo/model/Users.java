package com.example.demo.model;

public class Users {
    private int UserID;
    private int phone;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int country;
    private boolean isBanned; // Champ optionnel pour indiquer si l'utilisateur est banni

    public Users(int UserID,int phone, String firstName, String lastName, String email, String password, int country) {
        this.UserID = UserID;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.country = country;
        this.isBanned = false; // Par défaut, l'utilisateur n'est pas banni
    }

    // Getters et setters
    public int getUserId() { return UserID; }
    public int getPhone() { return phone; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public int getCountry() { return country; }
    public boolean isBanned() { return isBanned; }
    public void setBanned(boolean banned) { this.isBanned = banned; }
    public void setUserId(int userId) { this.UserID = userId; } // Ajouté pour les modifications
    public void setPhone(int phone) { this.phone = phone; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setCountry(int country) { this.country = country; }
}