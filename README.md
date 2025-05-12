# 📱 ShowTime – Booking App for Live Performances

🎭 **ShowTime** est une application mobile Android permettant aux utilisateurs de découvrir, filtrer et réserver des billets pour des spectacles (concerts, théâtre, festivals...).

---

## 🛠️ Technologies utilisées

| Côté Frontend (Mobile) | Backend (API REST)    | Base de Données      |
|------------------------|-----------------------|----------------------|
| Java, Android Studio   | Spring Boot (Java)    | MySQL                |
| XML (UI)               | Spring Data JPA       | MySQL Workbench      |
| SharedPreferences      | REST Controllers      | Script SQL (data.sql)|

---

## 🚀 Fonctionnalités principales

- 🔍 Parcourir les spectacles par ville et date
- 📅 Voir les détails de chaque événement
- 🎟️ Réserver des tickets
- ✅ Authentification simple (compte / login)
- 📬 Réception d'un message de confirmation
- 👤 Accès au profil et historique des réservations

---


---

## 🌐 API REST (Exemples)

| Endpoint                      | Description                         | Méthode |
|------------------------------|-------------------------------------|---------|
| `/api/users/login`           | Authentifier un utilisateur         | `POST`  |
| `/api/spectacles`            | Récupérer tous les spectacles       | `GET`   |
| `/api/reservations`          | Ajouter une réservation             | `POST`  |

---

## 🔗 Intégration Android ↔ Backend

L'application Android utilise la bibliothèque **Volley** pour interagir avec les endpoints Spring Boot. Les données JSON sont ensuite affichées dynamiquement dans les `RecyclerView`.

---

## 🧩 Outils utilisés

- Android Studio Arctic Fox
- Spring Tool Suite (STS)
- MySQL Workbench 8.0
- Postman (pour tester les routes REST)
- Git + GitHub (versioning)

---
