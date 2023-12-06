package com.staj2023backend.ws.shared;

import lombok.AllArgsConstructor;
import lombok.Data;

//Lombok annotations for getter, setter, a constructor with all variables...
@Data
@AllArgsConstructor
public class GenericResponse {

//Bazi requestler sonrasi client tarafinda message gostermek icin olusturuldu
    private String message;


}
