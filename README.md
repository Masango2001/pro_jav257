# 📦 Application de Gestion des Ventes et du Stock

## 📜 Introduction
Ce projet est une **application de gestion des ventes et du stock** destinée aux petites et moyennes entreprises qui souhaitent centraliser et automatiser leurs opérations commerciales.  
L’application permet de suivre **les clients, les ventes, les produits, les fournisseurs et le stock** via une interface intuitive développée en **Java Swing**, reliée à une base de données **MySQL**.

L’objectif principal est de faciliter le travail des vendeurs et de l’administrateur en offrant :
- Un suivi clair des transactions.
- Une gestion optimisée des stocks.
- Un accès sécurisé aux données via un système d’authentification par rôles.

---

## 🎯 Objectifs
- **Centraliser les données** liées aux ventes, clients, produits, fournisseurs et stocks.
- **Améliorer la productivité** grâce à un tableau de bord simple à utiliser.
- **Suivre en temps réel** les entrées et sorties de produits.
- **Sécuriser l’accès** avec gestion des rôles (`Admin`, `Vendeur`).
- **Faciliter la prise de décision** grâce à des informations fiables.

---


## Lieu sur Youtube
- https://youtu.be/EDsEL_-Ul5E?si=mBnCI2jU6iiW9-OH

- https://youtu.be/PNDYcJozKIo?si=qysO6dYcQdxQXDdb

## 🚀 Fonctionnalités

### 🔐 Authentification
- Connexion avec rôle utilisateur.
- Interface adaptée selon le rôle :
  - **Admin** : gestion complète.
  - **Vendeur** : accès limité à la vente et à la consultation du stock.

### 👥 Gestion des Clients
- Ajout, modification, suppression.
- Consultation rapide avec recherche.

### 🛒 Gestion des Ventes
- Création de ventes.
- Sélection des produits et calcul du total.
- Historique des ventes.

### 📦 Gestion du Stock
- Affichage du stock disponible.
- Mise à jour automatique après chaque vente ou approvisionnement.

### 🏭 Gestion des Fournisseurs
- Suivi des informations fournisseurs.
- Historique des approvisionnements.

---

## 🗄 Modèle de données (MCD)

Le projet repose sur une base de données relationnelle modélisée comme suit :  

![MCD](MCD.jpg)

### 📌 Entités principales
- **utilisateurs** : gère les comptes et rôles.
- **clients** : informations des acheteurs.
- **fournisseurs** : partenaires commerciaux.
- **produits** : articles vendus.
- **categories** : classification des produits.
- **stocks** : quantité et mise à jour des produits.
- **ventes** : enregistrements des transactions.
- **approvisionnements** : entrées de stock depuis les fournisseurs.



## 🏗 Architecture logicielle

Le projet suit le modèle **MVC (Model - View - Controller)** :
- **Model** : Représentation des données (entités, DAO).
- **View** : Interface graphique en **Swing**.
- **Controller** : Gestion de la logique métier.


