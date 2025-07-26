package com.stockvente.dao;

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
}