package com.staj2023backend.ws.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SoldProduct {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Product product;

    private Long numberOfProduct;

    // Other fields and getters/setters

    @ManyToOne
    private Selling selling;
}

