package com.stockvente.dao;

import com.stockvente.models.Produit;
import com.stockvente.utils.DatabaseConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProduitDao implements CrudDao<Produit> {

    @Override
    public void save(Produit produit) {
        
        validerCategorie(produit.getId_categorie());

        String query = "INSERT INTO produits (nom_produit, id_categorie) VALUES (?, ?)";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, produit.getNom_produit());
            stmt.setInt(2, produit.getId_categorie());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout du produit : " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Produit produit) {
        String query = "UPDATE produits SET nom_produit = ? WHERE id_produit = ?";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, produit.getNom_produit());
            stmt.setInt(2, produit.getId_produit());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Produit avec l'ID " + produit.getId_produit() + " non trouvé.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du produit : " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM produits WHERE id_produit = (SELECT MAX(id_produit) FROM produits)";
        try (Connection conn = DatabaseConnect.getConnection();
             Statement stmt = conn.createStatement()) {
            int rowsAffected = stmt.executeUpdate(query);
            if (rowsAffected == 0) {
                throw new RuntimeException("Aucun produit trouvé à supprimer.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du produit : " + e.getMessage(), e);
        }
    }

    @Override
    public List<Produit> afficherTous() {
        List<Produit> produits = new ArrayList<>();
        String query = "SELECT p.id_produit, p.nom_produit, p.id_categorie, " +
               "c.nom_categorie, s.quantite_stock " +
               "FROM produits p " +
               "JOIN categories c ON p.id_categorie = c.id_categorie " +
               "JOIN stocks s ON p.id_produit = s.id_produit";

        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Produit produit = new Produit(
                    rs.getInt("id_produit"),
                    rs.getString("nom_produit"),
                    rs.getInt("id_categorie"),
                    rs.getString("nom_categorie"),
                    rs.getInt("quantite_stock")
                );
                produits.add(produit);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des produits : " + e.getMessage(), e);
        }
        return produits;
    }

    private void validerCategorie(int id_categorie) {
        String query = "SELECT COUNT(*) FROM categories WHERE id_categorie = ?";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_categorie);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                throw new IllegalArgumentException("La catégorie avec l'ID " + id_categorie + " n'existe pas.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la validation de la catégorie : " + e.getMessage(), e);
        }
    }
}