package com.staj2023backend.ws.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import jakarta.persistence.*;
import java.util.List;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//Lombok kutuphanesi annotationu
@Data
//Database'de SELLING tablosu oluşturmak için kullanılan annotation
@Entity
public class Selling {

//  SELLING tablosunun primary key ini id olarak belirlemek için kullanılan annotation
    @Id
//  id yi sistemin üretip güncellemesi için kullanılan annotation
//  diger variableler columnlar
    @GeneratedValue
    private Long id;

    //private Long productID;
    @OneToMany(mappedBy = "selling")
    private List<SoldProduct> soldProducts;
   // private Long costumerID;

}
