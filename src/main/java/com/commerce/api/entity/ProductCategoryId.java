package com.commerce.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class ProductCategoryId implements java.io.Serializable {
    private static final long serialVersionUID = 7667591928797365972L;
    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProductCategoryId entity = (ProductCategoryId) o;
        return Objects.equals(this.productId, entity.productId) &&
                Objects.equals(this.categoryId, entity.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, categoryId);
    }

}