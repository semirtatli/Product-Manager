package com.staj2023backend.ws.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.staj2023backend.ws.shared.Views;
import lombok.Data;

import java.util.Date;
import java.util.Map;
//Lombok kütüphanesi
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
//Dönülecek HTTP response'larını, mesajları ve url yi tutan fieldlar
//    @JsonView(Views.Base.class)
    private int status;
//    @JsonView(Views.Base.class)
    private String message;
//    @JsonView(Views.Base.class)
    private String path;
//    @JsonView(Views.Base.class)
    private long timestamp = new Date().getTime();
//apierror map ediliyor ve message olarak gosterilmek icin iletiliyor
    private Map<String, String> validationErrors;

    public ApiError(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
    }


}
