package com.product_manager.ws.model;
//ProductId yi ozel yaptigimiz icin ayri bir class olustur
public class ProductID {

    private final String id;

    public ProductID(final String id) {this.id = id;}

    public String getId(){return this.id;}
}
