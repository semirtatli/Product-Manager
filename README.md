# STAJ2023_backend
2023 stajyerler java projesi

---

## Description
---

Market Takip Sisteminin backend kismi. Kullanici kaydi yapilip kayitli kullanici ile giris yapilabilir.
<br>Admin kategori ekleyip silebilir, urun ekleyebilir, guncelleyebilir, silebilir. Yapilan satislari goruntuleyebilir.

## Technologies
---
* Java
* Spring Boot
* Maven
* PostgreSQL
* JDBC
* Junit
* Mockito

## Installation
---
1. [Clone Repository Link](https://github.com/ilknuruysal/staj2023backend.git)

2. Maven ile dependency'leri indir

3. Postgre Serverini kurup application.properties'ten konfigurasyonunu yap

## Setup

1. `src/main/resources/application.properties` icindeki database'i kur

2. Projeyi calistir

Proje `http://localhost:8080` uzerinde calısır.

## API Endpoints
---
### REQUESTS
**Request**
* #### POST  /api/1.0/users

  *Yeni bir kullanici olusturur*

```
  {
    "username": "username",
    "displayName": "displayName",
    "password": "P4ssword"
}

```

**Response**

```
{
    "message": "user created"
}
```

**Request**
* #### GET /api/1.0/users

  *Tum kullanicilari listeler*

**Response**

```
   [
    {
        "id": 1,
        "username": "admin",
        "displayName": "admin",
        "password": "$2a$10$A6t4weBxcGMBe7Wl1aNttOi/z3GpuxhVNK9trgJF6Zd8xod2TVYmG"
    },
    
    {
        "id": 2,
        "username": "username",
        "displayName": "username",
        "password": "$2a$10$4ffg1s3CorG8Pyku01jhdO33/OXcXfogCOvA0zbhw/fergaMAqp82"
    },
    
    {
        "id": 52,
        "username": "username8",
        "displayName": "displayName",
        "password": "$2a$10$oEgG1J.xTlj1dX2vA9Bk6eyispIu4l1Ve2sekTvSFPNkYtREeGq0G"
    }
]
```

**Request**
* #### GET getUserById - /api/1.0/users/{id}

  *Id ile spesifik bir kullaniciyi listeler*

`/api/1.0/users/1`

**Response**

```
{
    "id": 1,
    "username": "admin",
    "displayName": "admin",
    "password": "$2a$10$A6t4weBxcGMBe7Wl1aNttOi/z3GpuxhVNK9trgJF6Zd8xod2TVYmG"
}
```

**Request**
* #### PATCH updateUserById - /api/1.0/users/{id}

  *Bir kullaniciyi gunceller*

`/api/1.0/users/52`

```
  {
    "username": "username9",
    "displayName": "displayName",
    "password": "P4ssword"
}
```

**Response**
```
{
    "id": 52,
    "username": "username9",
    "displayName": "displayName",
    "password": "$2a$10$EZhGmGL7aU.3J8/92rEJW.sPXPZwUmG3ubUw3NsHowGcshiOYHyqO"
}
```
**Request**
* #### DELETE deleteUserById - /api/1.0/users/{id}

  *Bir kullaniciyi siler*

`/api/1.0/users/52`

**Response**

` `

**Request**
* #### POST createCategory - /api/category

  *Bir kategori olusturur*

```
{
    "categoryName": "Tablet"

}
```

**Response**

```
{
    "id": "6ff7566f-a004-40c1-93aa-17690d7b5810"
}
```

**Request**
* #### GET getAllCategories - /api/category

  *Tum kategorileri listeler*

**Response**

```
[
    {
        "id": 1,
        "categoryName": "Laptop"
    },
    {
        "id": 3,
        "categoryName": "Telefon"
    },
    {
        "id": 52,
        "categoryName": "Tablet"
    }
]
```

**Request**
* #### DELETE deleteCategoryById - /api/category/{id}

  *Bir kategoriyi siler*

`/api/category/52`

**Response**

` `

**Request**
* #### POST createProduct - /api/products

  *Bir urun olusturur*

```
{
    "productName": "LaptopUrunu",
    "productPrice": 42.500,
    "productCategoryID": 1,
    "productColor": "Gri",
    "productStock": 25
}
```

**Response**

```
{
    "id": "12339385-a6a0-49a4-931b-19c9a3bb27e1"
}
```

**Request**
* #### GET getAllProducts - /api/products

  *Tum urunleri listeler*

**Response**

```
[
    {
        "id": 1,
        "productName": "IyiLaptop",
        "productCategoryID": 1,
        "productPrice": 21065.00,
        "productColor": "Kırmızı",
        "productStock": 118
    },
    {
        "id": 2,
        "productName": "efsaneharikalaptop",
        "productCategoryID": 3,
        "productPrice": 860.90,
        "productColor": "Bemmbeyaz",
        "productStock": 1
    },
    {
        "id": 52,
        "productName": "LaptopUrunu",
        "productCategoryID": 1,
        "productPrice": 42.50,
        "productColor": "Gri",
        "productStock": 25
    }
]
```

**Request**
* #### GET getProductByCategory - /api/products/byCategory/{ProductCategoryID}

  *Urunleri kategoriye gore filtreleyerek listeler*

`/api/products/byCategory/1`

**Response**

```
[
    {
        "id": 1,
        "productName": "IyiLaptop",
        "productCategoryID": 1,
        "productPrice": 21065.00,
        "productColor": "Kırmızı",
        "productStock": 118
    },
    {
        "id": 52,
        "productName": "LaptopUrunu",
        "productCategoryID": 1,
        "productPrice": 42.50,
        "productColor": "Gri",
        "productStock": 25
    }
]
```

**Request**
* #### GET getProductById - /api/products/{id}"

  *Bir urunu listeler*

`/api/products/52`

**Response**

```
{
    "id": 52,
    "productName": "LaptopUrunu",
    "productCategoryID": 1,
    "productPrice": 42.50,
    "productColor": "Gri",
    "productStock": 25
}
```

**Request**
* #### PUT updateProductById - /api/products/{id}

  *Bir urunu gunceller*

`/api/products/52`

```
{
    "productName": "LaptopUrunu",
    "productPrice": 45.000,
    "productCategoryID": 1,
    "productColor": "Beyaz",
    "productStock": 40
}
```

**Response**

```
{
    "id": 52,
    "productName": "LaptopUrunu",
    "productCategoryID": 1,
    "productPrice": 45.000,
    "productColor": "Beyaz",
    "productStock": 40
}
```

**Request**
* #### DELETE deleteProductByID - /api/products/{id}

  *Bir urunu siler*

`/api/products/52`

**Response**

` `

**Request**
* #### POST createOrder - /api/orders

  *Bir siparis olusturur*

```
  {
  "soldProducts": [
    {
      "product": {
        "id": 1
      },
      "numberOfProduct": 18
    },
    {
      "product": {
        "id": 52
      },
      "numberOfProduct": 20
    }
  ]
}
```

**Response**

```
{
        "id": 1,
            "soldProducts": [
        {
            "id": null,
                "product": {
            "id": 1,
                    "productName": null,
                    "productCategoryID": null,
                    "productPrice": null,
                    "productColor": null,
                    "productStock": null
        },
            "numberOfProduct": 18,
                "selling": null
        },
        {
            "id": null,
                "product": {
            "id": 52,
                    "productName": null,
                    "productCategoryID": null,
                    "productPrice": null,
                    "productColor": null,
                    "productStock": null
        },
            "numberOfProduct": 20,
                "selling": null
        }
    ]
    }
```

**Request**
* #### GET getAllSelling - /api/orders

  *Tum siparisleri listeler*

**Response**

```
[
    {
        "id": 1,
        "soldProducts": []
    }
]
```

