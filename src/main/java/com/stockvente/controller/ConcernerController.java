package com.stockvente.controller;
import com.stockvente.dao.StockDao;
import com.stockvente.dao.ConcernerDao;
import com.stockvente.models.Concerner;

import java.util.List;

public class ConcernerController {

    private final ConcernerDao concernerDao;
    private final StockDao stockDao;

    public ConcernerController() {
        this.concernerDao = new ConcernerDao();
        this.stockDao = new StockDao();
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
            // Vérifier si la quantité en stock est suffisante
            int quantiteDisponible = stockDao.getQuantiteStockParProduit(concerner.getId_produit());

            if (concerner.getQuantite_vendue() > quantiteDisponible) {
                return "Erreur : Stock insuffisant. Disponible : " + quantiteDisponible +
                       ", demandé : " + concerner.getQuantite_vendue();
            }

            // Stock suffisant, on procède
            concernerDao.save(concerner);
            stockDao.diminuerStock(concerner.getId_produit(), concerner.getQuantite_vendue());

            return "Ligne de vente ajoutée et stock mis à jour avec succès.";
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