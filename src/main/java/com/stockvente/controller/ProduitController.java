package com.stockvente.controller;

import com.stockvente.dao.ProduitDao;
import com.stockvente.models.Produit;

import java.util.List;

public class ProduitController {

    private final ProduitDao produitDao;

    public ProduitController() {
        this.produitDao = new ProduitDao();
    }
    
    public List<Produit> getProduits() {
        return produitDao.afficherTous();
    }


    public String afficherTousLesProduits(String role) {
        if (!role.equals("Vendeur") && !role.equals("Magasinier") && !role.equals("Admin")) {
            return "Accès refusé : rôle non autorisé.";
        }

        List<Produit> produits = produitDao.afficherTous();
        if (produits.isEmpty()) {
            return "Aucun produit trouvé dans la base de données.";
        }

        StringBuilder result = new StringBuilder("Liste des produits :\n");
        for (Produit produit : produits) {
            result.append("ID: ").append(produit.getId_produit())
                  .append(", Nom: ").append(produit.getNom_produit())
                  .append(", Catégorie ID: ").append(produit.getId_categorie())
                  .append(",nom Categorie :").append(produit.getNom_categorie())
                  .append(",quantite stock :").append(produit.getQuantite_stock())
                  .append("\n");
        }
        return result.toString();
    }

    public String ajouterProduit(String role, Produit produit) { // Accepts Produit object
        if (!role.equals("Magasinier") && !role.equals("Admin")) {
            return "Accès refusé : seul un Magasinier ou un Admin peut ajouter un produit.";
        }

        if (produit == null || produit.getNom_produit() == null || produit.getNom_produit().trim().isEmpty()) {
            return "Erreur : le nom du produit ne peut pas être vide ou null.";
        }
        if (produit.getId_categorie() <= 0) {
            return "Erreur : l'ID de la catégorie doit être un entier positif.";
        }

        try {
            produitDao.save(produit);
            return "Produit ajouté avec succès.";
        } catch (RuntimeException e) {
            return "Erreur lors de l'ajout du produit : " + e.getMessage();
        }
    }

    public String mettreAJourProduit(String role, Produit produit) { // Accepts Produit object
        if (!role.equals("Magasinier") && !role.equals("Admin")) {
            return "Accès refusé : seul un Magasinier ou un Admin peut mettre à jour un produit.";
        }

        if (produit == null || produit.getId_produit() <= 0) {
            return "Erreur : l'ID du produit est invalide.";
        }
        if (produit.getNom_produit() == null || produit.getNom_produit().trim().isEmpty()) {
            return "Erreur : le nom du produit ne peut pas être vide ou null.";
        }
        if (produit.getId_categorie() <= 0) {
            return "Erreur : l'ID de la catégorie doit être un entier positif.";
        }

        try {
            produitDao.update(produit);
            return "Produit avec l'ID " + produit.getId_produit() + " mis à jour avec succès.";
        } catch (RuntimeException e) {
            return "Erreur lors de la mise à jour du produit : " + e.getMessage();
        }
    }

    public String supprimerProduit(String role, int id_produit) { // Accepts id_produit
        if (!role.equals("Magasinier") && !role.equals("Admin")) {
            return "Accès refusé : seul un Magasinier ou un Admin peut supprimer un produit.";
        }
        if (id_produit <= 0) {
            return "Erreur : l'ID du produit doit être un entier positif.";
        }

        try {
            produitDao.delete(id_produit);
            return "Produit avec l'ID " + id_produit + " supprimé avec succès.";
        } catch (RuntimeException e) {
            return "Erreur lors de la suppression du produit : " + e.getMessage();
        }
    }

    public List<Produit> getTousLesProduits(String role) {
        if (!role.equals("Admin")) {
            throw new SecurityException("Accès refusé : seul un Admin peut accéder à cette méthode.");
        }
        return produitDao.afficherTous();
    }
//
//    String mettreAJourProduit(String admin, int id_produit, String nom_produit, int id_categorie) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//    String ajouterProduit(String admin, String nom_produit, int id_categorie) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }

    
}