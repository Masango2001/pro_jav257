package com.stockvente.controller;

import com.stockvente.dao.ConcernerDao;
import com.stockvente.models.Concerner;

import java.util.List;

public class ConcernerController {

    private final ConcernerDao concernerDao;

    public ConcernerController() {
        this.concernerDao = new ConcernerDao();
    }

    public List<Concerner> getToutesLesLignesDeVente(String role) {
        if (!role.equals("Vendeur") && !role.equals("Admin")) {
            throw new SecurityException("Accès refusé : rôle non autorisé.");
        }
        return concernerDao.afficherTous();
    }

    public String ajouterLigneDeVente(String role, Concerner concerner) {
        if (!role.equals("Vendeur") && !role.equals("Admin")) {
            return "Accès refusé : seul un Vendeur ou un Admin peut ajouter une ligne de vente.";
        }

        try {
            concernerDao.save(concerner);
            return "Ligne de vente ajoutée avec succès.";
        } catch (RuntimeException e) {
            return "Erreur lors de l'ajout de la ligne de vente : " + e.getMessage();
        }
    }

    String ajouterLigneDeVente(String admin, int id_vente, int id_produit, String quantite_vendue, double prix_unitaire_vendue) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String afficherConcerner(String vendeur) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String mettreAJourLigneDeVente(String admin, int id_vente, int id_produit, int quantite_vendue, double prix_unitaire_vendue) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String supprimerLigneDeVente(String admin, int id_vente, int id_produit) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String ajouterLigneDeVente(String admin, int id_vente, int id_produit, int quantite_vendue, double prix_unitaire_vendue) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}