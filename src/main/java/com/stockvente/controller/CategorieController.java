package com.stockvente.controller;

import com.stockvente.dao.CategorieDao;
import com.stockvente.models.Categorie;

import java.util.List;

public class CategorieController {

    private final CategorieDao categorieDao;

    public CategorieController() {
        this.categorieDao = new CategorieDao();
    }

    public String afficherToutesLesCategories(String role) {
        if (!role.equals("Vendeur") && !role.equals("Magasinier") && !role.equals("Admin")) {
            return "Accès refusé : rôle non autorisé.";
        }

        List<Categorie> categories = categorieDao.afficherTous();
        if (categories.isEmpty()) {
            return "Aucune catégorie trouvée dans la base de données.";
        }

        StringBuilder result = new StringBuilder("Liste des catégories :\n");
        for (Categorie categorie : categories) {
            result.append("ID: ").append(categorie.getId_categorie())
                  .append(", Nom: ").append(categorie.getNom_categorie())
                  .append("\n");
        }
        return result.toString();
    }

    public String ajouterCategorie(String role, Categorie categorie) { // Accepts Categorie object
        if (!role.equals("Magasinier") && !role.equals("Admin")) {
            return "Accès refusé : seul un Magasinier ou un Admin peut ajouter une catégorie.";
        }

        if (categorie == null || categorie.getNom_categorie() == null || categorie.getNom_categorie().trim().isEmpty()) {
            return "Erreur : le nom de la catégorie ne peut pas être vide ou null.";
        }

        if (categorieExisteDeja(categorie.getNom_categorie())) {
            return "Erreur : une catégorie avec le nom '" + categorie.getNom_categorie() + "' existe déjà.";
        }

        try {
            categorieDao.save(categorie);
            return "Catégorie ajoutée avec succès.";
        } catch (RuntimeException e) {
            return "Erreur lors de l'ajout de la catégorie : " + e.getMessage();
        }
    }

    public String mettreAJourCategorie(String role, Categorie categorie) { // Accepts Categorie object
        if (!role.equals("Magasinier") && !role.equals("Admin")) {
            return "Accès refusé : seul un Magasinier ou un Admin peut mettre à jour une catégorie.";
        }

        if (categorie == null || categorie.getId_categorie() <= 0) {
            return "Erreur : l'ID de la catégorie est invalide.";
        }
        if (categorie.getNom_categorie() == null || categorie.getNom_categorie().trim().isEmpty()) {
            return "Erreur : le nom de la catégorie ne peut pas être vide ou null.";
        }

        // Check if the new name clashes with an existing category (excluding itself)
        List<Categorie> existingCategories = categorieDao.afficherTous();
        for (Categorie existing : existingCategories) {
            if (existing.getId_categorie() != categorie.getId_categorie() &&
                existing.getNom_categorie().equalsIgnoreCase(categorie.getNom_categorie())) {
                return "Erreur : une autre catégorie avec le nom '" + categorie.getNom_categorie() + "' existe déjà.";
            }
        }

        try {
            categorieDao.update(categorie);
            return "Catégorie avec l'ID " + categorie.getId_categorie() + " mise à jour avec succès.";
        } catch (RuntimeException e) {
            return "Erreur lors de la mise à jour de la catégorie : " + e.getMessage();
        }
    }

    public String supprimerCategorie(String role, int id_categorie) { // Accepts id_categorie
        if (!role.equals("Admin")) {
            return "Accès refusé : seul un Admin peut supprimer une catégorie.";
        }
        if (id_categorie <= 0) {
            return "Erreur : l'ID de la catégorie doit être un entier positif.";
        }

        try {
            categorieDao.delete(id_categorie);
            return "Catégorie avec l'ID " + id_categorie + " supprimée avec succès.";
        } catch (RuntimeException e) {
            return "Erreur lors de la suppression de la catégorie : " + e.getMessage();
        }
    }

    private boolean categorieExisteDeja(String nomCategorie) {
        if (nomCategorie == null || nomCategorie.trim().isEmpty()) {
            return false;
        }
        List<Categorie> categories = categorieDao.afficherTous();
        for (Categorie categorie : categories) {
            if (categorie.getNom_categorie().equalsIgnoreCase(nomCategorie)) {
                return true;
            }
        }
        return false;
    }

    String mettreAJourCategorie(String magasinier, int id_categorie) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String ajouterCategorie(String magasinier, String nomCategorie) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String mettreAJourCategorie(String admin, int id_categorie, String nom_categorie) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}