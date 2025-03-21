package com.example.demo.model;

import javafx.beans.property.*;

import java.time.LocalDateTime;

public class Products {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final IntegerProperty ownerId = new SimpleIntegerProperty();
    private final StringProperty type = new SimpleStringProperty();
    private final StringProperty thumbnailImage = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final DoubleProperty price = new SimpleDoubleProperty();
    private final IntegerProperty stockQuantity = new SimpleIntegerProperty();
    private final ObjectProperty<LocalDateTime> createdAt = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDateTime> updatedAt = new SimpleObjectProperty<>();
    private final StringProperty status = new SimpleStringProperty(); // Disponibilité (available/unavailable)
    private final StringProperty requestStatus = new SimpleStringProperty(); // Statut de la demande (pending/approved/rejected)

    public Products(int id, String name, int ownerId, String type, String thumbnailImage, String description,
                    double price, int stockQuantity, LocalDateTime createdAt, LocalDateTime updatedAt, String status, String requestStatus) {
        this.id.set(id);
        this.name.set(name);
        this.ownerId.set(ownerId);
        this.type.set(type);
        this.thumbnailImage.set(thumbnailImage);
        this.description.set(description);
        this.price.set(price);
        this.stockQuantity.set(stockQuantity);
        this.createdAt.set(createdAt);
        this.updatedAt.set(updatedAt);
        this.status.set(status);
        this.requestStatus.set(requestStatus);
    }

    // Getters pour les propriétés
    public IntegerProperty idProperty() { return id; }
    public StringProperty nameProperty() { return name; }
    public IntegerProperty ownerIdProperty() { return ownerId; }
    public StringProperty typeProperty() { return type; }
    public StringProperty thumbnailImageProperty() { return thumbnailImage; }
    public StringProperty descriptionProperty() { return description; }
    public DoubleProperty priceProperty() { return price; }
    public IntegerProperty stockQuantityProperty() { return stockQuantity; }
    public ObjectProperty<LocalDateTime> createdAtProperty() { return createdAt; }
    public ObjectProperty<LocalDateTime> updatedAtProperty() { return updatedAt; }
    public StringProperty statusProperty() { return status; }
    public StringProperty requestStatusProperty() { return requestStatus; }

    // Getters pour les valeurs
    public int getId() { return id.get(); }
    public String getName() { return name.get(); }
    public int getOwnerId() { return ownerId.get(); }
    public String getType() { return type.get(); }
    public String getThumbnailImage() { return thumbnailImage.get(); }
    public String getDescription() { return description.get(); }
    public double getPrice() { return price.get(); }
    public int getStockQuantity() { return stockQuantity.get(); }
    public LocalDateTime getCreatedAt() { return createdAt.get(); }
    public LocalDateTime getUpdatedAt() { return updatedAt.get(); }
    public String getStatus() { return status.get(); }
    public String getRequestStatus() { return requestStatus.get(); }

    // Setters
    public void setId(int id) { this.id.set(id); }
    public void setName(String name) { this.name.set(name); }
    public void setOwnerId(int ownerId) { this.ownerId.set(ownerId); }
    public void setType(String type) { this.type.set(type); }
    public void setThumbnailImage(String thumbnailImage) { this.thumbnailImage.set(thumbnailImage); }
    public void setDescription(String description) { this.description.set(description); }
    public void setPrice(double price) { this.price.set(price); }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity.set(stockQuantity); }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt.set(createdAt); }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt.set(updatedAt); }
    public void setStatus(String status) { this.status.set(status); }
    public void setRequestStatus(String requestStatus) { this.requestStatus.set(requestStatus); }
}