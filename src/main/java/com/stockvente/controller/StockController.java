package com.stockvente.controller;

import com.stockvente.dao.StockDao;
import com.stockvente.models.Stock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StockController {
    

    private final StockDao stockDao;
    private final SimpleDateFormat dateFormat;

    public StockController() {
        this.stockDao = new StockDao();
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    public String afficherTousLesStocks(String role) {
        if (!role.equals("Vendeur") && !role.equals("Magasinier") && !role.equals("Admin")) {
            return "Accès refusé : rôle non autorisé.";
        }

        List<Stock> stocks = stockDao.afficherTous();
        if (stocks.isEmpty()) {
            return "Aucun stock trouvé dans la base de données.";
        }

        StringBuilder result = new StringBuilder("Liste des stocks :\n");
        for (Stock stock : stocks) {
            result.append("ID: ").append(stock.getId_stock())
                  .append(", Produit ID: ").append(stock.getId_produit())
                  .append(", Quantité: ").append(stock.getQuantite_stock())
                  .append(", Date de mise à jour: ").append(dateFormat.format(stock.getDate_misejour()))
                  .append("\n");
        }
        return result.toString();
    }

    public String ajouterStock(String role, Stock stock) { // Accepts Stock object
        if (!role.equals("Magasinier") && !role.equals("Admin")) {
            return "Accès refusé : seul un Magasinier ou un Admin peut ajouter du stock.";
        }
        if (stock == null || stock.getId_produit() <= 0 || stock.getQuantite_stock() <= 0) {
            return "Erreur : données de stock invalides (ID produit et quantité doivent être positifs).";
        }
        if (!validerDate(stock.getDate_misejour())) {
            return "Erreur : la date de mise à jour ne peut pas être dans le futur.";
        }

        try {
            // Note: `save` method in DAO should handle if product already has a stock entry (update) or new entry (insert)
            stockDao.save(stock);
            return "Stock ajouté/mis à jour avec succès pour le produit ID " + stock.getId_produit() + ".";
        } catch (RuntimeException e) {
            return "Erreur lors de l'ajout/mise à jour du stock : " + e.getMessage();
        }
    }

    public String mettreAJourStock(String role, Stock stock) {
        if (!role.equals("Magasinier") && !role.equals("Admin")) {
            return "Accès refusé : seul un Magasinier ou un Admin peut mettre à jour un stock.";
        }
        if (stock == null || stock.getId_stock() <= 0) {
            return "Erreur : l'ID du stock est invalide.";
        }
        if (!validerDate(stock.getDate_misejour())) {
            return "Erreur : la date de mise à jour ne peut pas être dans le futur.";
        }
        if (stock.getQuantite_stock() < 0) {
            return "Erreur : la quantité de stock ne peut pas être négative.";
        }

        try {
            stockDao.update(stock);
            return "Stock avec l'ID " + stock.getId_stock() + " mis à jour avec succès.";
        } catch (RuntimeException e) {
            return "Erreur lors de la mise à jour du stock : " + e.getMessage();
        }
    }

    public String supprimerStock(String role, int id_stock) { // New method for deleting stock entry
        if (!role.equals("Admin")) {
            return "Accès refusé : seul un Admin peut supprimer une entrée de stock.";
        }
        if (id_stock <= 0) {
            return "Erreur : l'ID du stock doit être un entier positif.";
        }
        try {
            stockDao.delete(id_stock);
            return "Entrée de stock avec l'ID " + id_stock + " supprimée avec succès.";
        } catch (RuntimeException e) {
            return "Erreur lors de la suppression de l'entrée de stock : " + e.getMessage();
        }
    }

    private boolean validerDate(Date date) {
        if (date == null) {
            return false;
        }
        Date dateActuelle = new Date();
        return !date.after(dateActuelle);
    }

    public List<Stock> getTousLesStocks(String role) {
        if (!role.equals("Admin")) {
            throw new SecurityException("Accès refusé : seul un Admin peut accéder à cette méthode.");
        }
        return stockDao.afficherTous();
    }

    String ajouterStock(String admin, int id_produit, int quantite_stock, Date date_misejour) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String mettreAJourStock(String admin, int id_stock, int id_produit, int quantite_stock, Date date_misejour) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}