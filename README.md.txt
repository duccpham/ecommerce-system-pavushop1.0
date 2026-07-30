# PavuShop - E-Commerce System
A full-stack e-commerce web application built with Spring Boot and React.

#  Features

# Customer
- Register & Login
- Browse products
- Search products
- View product details
- Add to cart
- Checkout
- Forgot password with OTP
- Order confirmation email

# Admin
- Manage Categories
- Manage Brands
- Manage Products
- Manage Orders
- Manage Customers
- Sales Reports

---

# Tech Stack

## Backend
- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT Authentication
- MySQL
- Redis
- Java Mail

## Frontend
- React
- React Router
- Ant Design
- Vite

## DevOps
- Docker
- Docker Compose
- Nginx

---

# System Architecture

```
                Web Browser
                     │
                     ▼
                 Nginx (Frontend)
                     │
         ┌───────────┴───────────┐
         │                       │
         ▼                       ▼
     React (UI)          Spring Boot REST API
                                 │
        ┌────────────────────────┼──────────────────────┐
        │                        │                      │
        ▼                        ▼                      ▼
      MySQL                   Redis               Image Storage
   (Data Storage)          (OTP Cache)          (Upload Folder)
```
# Screenshots

 Home

![Home](screenshots/home.png)

 Products

![Products](screenshots/products.png)

 Product Detail

![Product Detail](screenshots/productDetail.png)

 Cart

![Cart](screenshots/cart.png)

 Login

![Login](screenshots/login.png)

 Register

![Register](screenshots/register.png)

 Admin

![Admin](screenshots/admin.png)

---
# Run Project 

Docker
bash
cd ecommerce-system-pavushop1.0
docker compose up --build

---


# Author

**Thắng Phạm**

GitHub: https://github.com/duccpham
