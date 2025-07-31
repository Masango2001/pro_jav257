package com.stockvente.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatisticsDao {
    private final Connection connection;

    public StatisticsDao(Connection connection) {
        this.connection = connection;
    }
    
    public int getTotalUtilisateurs() throws SQLException {
        String sql = "SELECT COUNT(*) FROM utilisateurs";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public int getTotalProduits() throws SQLException {
        String sql = "SELECT COUNT(*) AS total_produits FROM produits";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt("total_produits") : 0;
        }
    }
    // Approvisionnements récents (aujourd’hui)
    public int getApprovisionnementsRecents() {
        String sql = "SELECT COUNT(*) FROM approvisionnements WHERE DATE(date_approvisionnement) = CURDATE()";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Produits en alerte (ex: quantité <= 5)
    public int getProduitsEnAlerte() {
        String sql = "SELECT COUNT(*) FROM stock WHERE quantite_stock <= 5";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

// Approvisionnements ce mois
    public int getTotalApprovisionnementsCeMois() {
        String sql = "SELECT COUNT(*) FROM approvisionnements WHERE MONTH(date_approvisionnement) = MONTH(CURRENT_DATE()) AND YEAR(date_approvisionnement) = YEAR(CURRENT_DATE())";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getQuantiteTotaleEnStock() throws SQLException {
        String sql = "SELECT SUM(quantite_stock) AS total_stock FROM stocks";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt("total_stock") : 0;
        }
    }

    public int getNombreTotalVentes() throws SQLException {
        String sql = "SELECT COUNT(*) AS total_ventes FROM ventes";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt("total_ventes") : 0;
        }
    }

    public String getNomVendeurLePlusActif() throws SQLException {
        String sql = """
            SELECT u.username
            FROM ventes v
            JOIN utilisateurs u ON v.id_utilisateur = u.id_utilisateur
            GROUP BY v.id_utilisateur, u.username
            ORDER BY COUNT(*) DESC
            LIMIT 1
        """;
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getString("username") : "Aucun";
        }
    }

    public String getMagasinierLePlusActif() throws SQLException {
        String sql = """
            SELECT u.username
            FROM approvisionnements a
            JOIN utilisateurs u ON a.id_utilisateur = u.id_utilisateur
            GROUP BY a.id_utilisateur, u.username
            ORDER BY SUM(a.quantite_approvisionnement) DESC
            LIMIT 1
        """;
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getString("username") : "Aucun";
        }
    }
}