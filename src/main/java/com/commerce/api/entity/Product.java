package com.commerce.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ColumnDefault("NULL::character varying")
    @Column(name = "slug", nullable = false)
    private String slug;

    @ColumnDefault("NULL::character varying")
    @Column(name = "long_description", length = Integer.MAX_VALUE)
    private String longDescription;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @ColumnDefault("NULL::timestamp without time zone")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "sku", nullable = false)
    private String sku;

    @ColumnDefault("NULL::character varying")
    @Column(name = "main_image")
    private String mainImage;

    @Column(name = "price", precision = 5, scale = 2)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "merchant_id", nullable = false)
    /*
    @JsonBackReference // This will prevent infinite recursion during serialization
    @JsonIgnore // This will make the property not discoverable by default
     */
    private Merchant merchant;

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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }
}