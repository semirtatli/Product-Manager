package com.product_manager.ws.model;


import com.product_manager.ws.unique_username.UniqueUsername;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

//Getter, Setter, toString ve başka kütüphaneler için kullanılan Lombok annotationu
@Data
@AllArgsConstructor
//Database'de USERS tablosu oluşturmak için kullanılan annotation
@Entity
public class Users {

//USERS tablosunun primary key ini id olarak belirlemek için kullanılan annotation
    @Id
//id yi sistemin üretip güncellemesi için kullanılan annotation
//diger variableler table'in diger columnlari
    @GeneratedValue
    private long id;
//username kısmının boş olamayacağını görsel olarak sunmak için Validation Messages kısmından dil seçerek
//kullanıcıya uyarı gösterme, Jakarta validation dependency ile birlikte gelen uyarılar
    @NotBlank (message = "{staj2023backend.constraints.username.NotBlank.message}")
//username kısmının en az 4 en çok 255 karakter olmasını gösteren uyarı
    @Size(min=4, max=255, message = "{staj2023backend.constraints.username.size.message}")
//Sistemden diğer username'ler ile yazılan username'i karşılaştıran ve daha önce kullanılmışsa uyarı veren annotation
    @UniqueUsername
    private String username;

    @NotBlank (message = "{staj2023backend.constraints.displayName.NotBlank.message}")
    @Size(min=4, max=255, message = "{staj2023backend.constraints.displayName.size.message}")
    private String displayName;

    @NotBlank (message = "{staj2023backend.constraints.password.NotBlank.message}")
    @Size(min=8, max=255, message = "{staj2023backend.constraints.password.size.message}")
//Şifrenin en az 1 büyük harf en az 1 küçük harf ve en az bir sayı içermesini söyleyen uyarı
    @Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{staj2023backend.constraints.password.pattern.message}")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    public Users() {

    }

    public Users(int id, String username, String displayName, String password) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.password = password;
        this.role = Role.USER;
    }
}
