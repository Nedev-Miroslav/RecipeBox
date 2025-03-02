# 🍽️ RecipeBox
Personal Project

---

<div align="center">
  <a href="https://github.com/Nedev-Miroslav/RecipeBox">
    <img src="https://github.com/Nedev-Miroslav/RecipeBox/blob/main/src/main/resources/static/images/logo.png" alt="Logo" style="width: 100px; height: auto;">
  </a>
</div>

---

## 📖 Description:
A simple and user-friendly web application for managing, sharing, and discovering recipes.
### 🛠️ Built with: 
![Java](https://img.shields.io/badge/Java-f89820?style=flat&logo=java&logoColor=white) 
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6db33f?style=flat&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479a1?style=flat&logo=mysql&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005f0f?style=flat&logo=thymeleaf&logoColor=white)
![Bootstrap](https://img.shields.io/badge/Bootstrap-563d7c?style=flat&logo=bootstrap&logoColor=white)
![HTML](https://img.shields.io/badge/HTML-e34c26?style=flat&logo=html5&logoColor=white)
![CSS](https://img.shields.io/badge/CSS-264de4?style=flat&logo=css3&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=flat&logo=hibernate&logoColor=white) 
![JavaScript](https://img.shields.io/badge/JavaScript-f0db4f?style=flat&logo=javascript&logoColor=black) 
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6db33f?style=flat&logo=spring&logoColor=white) 
![Spring Security](https://img.shields.io/badge/Spring%20Security-6db33f?style=flat&logo=spring&logoColor=white)

---

## 📦 Installation & Setup
1. ⬇️ **Download the repository**  
   [![Download RecipeBox](https://img.shields.io/badge/⬇️%20Download-RecipeBox-blue?style=for-the-badge&logo=github)](https://github.com/Nedev-Miroslav/RecipeBox/archive/refs/heads/main.zip)
2. 🛠 **Database Configuration (MySQL)**
    - The database will be created automatically if it does not exist.
    - Ensure **MySQL is installed and running** on your machine.
    - In `application.yml`, set your **Username** and **Password** for MySQL.
3. ▶️ **Run the project**
4. 🌍 **Access the application**
    - Open your browser and visit: **[http://localhost:8081](http://localhost:8081)**
---

## 🔍 How It Works
1. **📝 Sign Up / Log In**
    - Create a new account or log in with existing credentials.
    - Secure authentication with **Spring Security**.
2. **📖 Browse Recipes**
    - Discover a variety of user-shared recipes.
    - Use the **🔎 smart search** feature to find recipes by name.
3. **🍽️ Add Your Own**
    - Easily add new recipes with ingredients, steps, and images.
    - ✏️ Edit or ❌ delete your own recipes anytime.
4. **⭐ Save Favorites**
    - ❤️ Bookmark your favorite recipes for quick access later.
    - 📂 Manage your saved recipes in the **Favorites section**.

---

## 🔑 User Authentication
- 👤 **Regular Users**:
    - Can **add, edit, and delete** their own recipes.
    - Can **browse, search, and save** recipes as favorites.
- 👑 **Admin Users**:
    - Can **add, edit, and delete** all recipes.
- 🔐 **Default Admin Account**:
    - **Username**: `Admin`
    - **Password**: `admin` (hashed in DB)
- 🔗 **Login Page**: `/users/login`
- 📝 **Register Page**: `/users/register`

---

## ✨ Features
✔️ **🍳 Recipe Management** – Add, edit, and delete your own recipes with images and detailed descriptions.  
✔️ **🔎 Smart Search** – Find recipes easily by **partial or full name**.  
✔️ **⭐ Favorites** – Save and manage your favorite recipes.  
✔️ **📌 My Recipes Section** – View all the recipes you’ve personally added.  
✔️ **💬 Comments** – Leave and read comments on recipes.  
✔️ **🔐 User Authentication** – Secure login with different roles (User/Admin).  
✔️ **🛠 Admin Privileges** – Manage all recipes and users.
---

## 📸 Screenshots

### 🔑 Authentication
- **Login Page**  
  ![Login Page](https://github.com/Nedev-Miroslav/RecipeBox/blob/main/src/main/resources/static/images/screenshots/loginPage.png)
- **Register Page**  
  ![Register Page](https://github.com/Nedev-Miroslav/RecipeBox/blob/main/src/main/resources/static/images/screenshots/registerPage.png)

### 🏠 Home Page
- **Home Page En**  
  ![Home Page En](https://github.com/Nedev-Miroslav/RecipeBox/blob/main/src/main/resources/static/images/screenshots/homePage.png)
- **Home Page Bg**  
  ![Home Page Bg](https://github.com/Nedev-Miroslav/RecipeBox/blob/main/src/main/resources/static/images/screenshots/homePageBg.png)

### 📖 Recipe Management
- **Add recipe page**  
  ![Add recipe page](https://github.com/Nedev-Miroslav/RecipeBox/blob/main/src/main/resources/static/images/screenshots/addRecipePage.png)
- **All recipes page**  
  ![All recipes page](https://github.com/Nedev-Miroslav/RecipeBox/blob/main/src/main/resources/static/images/screenshots/allRecipesPage.png)
- **Recipe detail page**  
  ![Recipe detail page](https://github.com/Nedev-Miroslav/RecipeBox/blob/main/src/main/resources/static/images/screenshots/recipeDetailPage.png)
- **My Recipes page**  
  ![My Recipes page](https://github.com/Nedev-Miroslav/RecipeBox/blob/main/src/main/resources/static/images/screenshots/myRecipesPage.png)

---

## 📜 License
This project is licensed under the MIT License.