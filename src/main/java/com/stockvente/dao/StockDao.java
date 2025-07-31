package com.stockvente.dao;

import com.stockvente.models.Produit;
import com.stockvente.models.Stock;
import com.stockvente.utils.DatabaseConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class StockDao implements CrudDao<Stock> {
    @Override
    public void save(Stock stock) {
    validerChamps(stock);  // Valider quantité et date (comme pour update)

        String query = "INSERT INTO stocks (id_produit, quantite_stock, date_misejour) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, stock.getId_produit());
            stmt.setInt(2, stock.getQuantite_stock());
            stmt.setDate(3, new java.sql.Date(stock.getDate_misejour().getTime()));

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException("Échec de l'insertion du stock.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout du stock : " + e.getMessage(), e);
        }
    }


    @Override
    public void update(Stock stock) {
        validerChamps(stock);

        String query = "UPDATE stocks SET quantite_stock = ?, date_misejour = ? WHERE id_stock = ?";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, stock.getQuantite_stock());
            stmt.setDate(2, new java.sql.Date(stock.getDate_misejour().getTime()));
            stmt.setInt(3, stock.getId_stock());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Stock avec l'ID " + stock.getId_stock() + " non trouvé.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du stock : " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
       
    }

    @Override
    public List<Stock> afficherTous() {
        List<Stock> stocks = new ArrayList<>();
        String query = "SELECT * FROM stocks";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Stock stock = new Stock(
                    rs.getInt("id_stock"),
                    rs.getInt("id_produit"),
                    rs.getInt("quantite_stock"),
                    rs.getDate("date_misejour")
                );
                stocks.add(stock);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des stocks : " + e.getMessage(), e);
        }
        return stocks;
    }

    private void validerChamps(Stock stock) {
        if (stock.getQuantite_stock() < 0) {
            throw new IllegalArgumentException("La quantité en stock ne peut pas être négative.");
        }
        if (stock.getDate_misejour() == null) {
            throw new IllegalArgumentException("La date de mise à jour ne peut pas être null.");
        }
    }
    public void diminuerStock(int idProduit, int quantiteVendue) {
        String sql = "UPDATE stocks SET quantite_stock = quantite_stock - ?, date_misejour = CURRENT_DATE WHERE id_produit = ?";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quantiteVendue);
            stmt.setInt(2, idProduit);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException("Le produit avec l'ID " + idProduit + " n'existe pas dans le stock.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la diminution du stock : " + e.getMessage(), e);
        }
    }
    // Récupère tous les produits (à utiliser dans la vue consultation)
    public List<Produit> getAllProduitsAvecStock() {
        List<Produit> produits = new ArrayList<>();
            String query = "SELECT DISTINCT p.id_produit, p.nom_produit, c.nom_categorie, s.quantite_stock " +
                           "FROM produits p " +
                           "JOIN stocks s ON p.id_produit = s.id_produit " +
                           "JOIN categories c ON p.id_categorie = c.id_categorie";

            try (Connection conn = DatabaseConnect.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Produit produit = new Produit(); // ✅ instanciation nécessaire

                    produit.setId_produit(rs.getInt("id_produit"));
                    produit.setNom_produit(rs.getString("nom_produit"));
                    produit.setNom_categorie(rs.getString("nom_categorie")); // depuis jointure avec `categorie`
                    produit.setQuantite_stock(rs.getInt("quantite_stock"));  // depuis jointure avec `stocks`

                    produits.add(produit);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return produits;
        }

    public int getQuantiteStockParProduit(int idProduit) {
        String query = "SELECT quantite_stock FROM stocks WHERE id_produit = ?";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idProduit);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("quantite_stock");
                } else {
                    throw new RuntimeException("Produit non trouvé dans le stock.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de la quantité en stock : " + e.getMessage(), e);
        }
    }


}