package com.example.demo.model;

public class Users {
    private int id;           // Changé de UserID à id
    private int phone;        // Correspond à telephone
    private String firstName; // Correspond à prenom
    private String lastName;  // Correspond à nom
    private String email;     // Correspond à email
    private String country;   // Changé de int à String pour correspondre à pays
    private String gender;    // Ajouté pour correspondre à genre
    private boolean isBanned; // Champ optionnel pour indiquer si l'utilisateur est banni

    public Users(int id, int phone, String firstName, String lastName, String email, String country, String gender) {
        this.id = id;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.country = country;
        this.gender = gender;
        this.isBanned = false; // Par défaut, l'utilisateur n'est pas banni
    }

    // Getters et setters
    public int getId() { return id; }
    public int getPhone() { return phone; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getCountry() { return country; }
    public String getGender() { return gender; }
    public boolean isBanned() { return isBanned; }

    public void setId(int id) { this.id = id; }
    public void setPhone(int phone) { this.phone = phone; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setCountry(String country) { this.country = country; }
    public void setGender(String gender) { this.gender = gender; }
    public void setBanned(boolean banned) { this.isBanned = banned; }
}