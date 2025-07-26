package com.stockvente.dao;

import com.stockvente.models.Categorie;
import com.stockvente.utils.DatabaseConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CategorieDao implements CrudDao<Categorie> {

    @Override
    public void save(Categorie categorie) {
        // Validation du nom de la catégorie
        if (categorie.getNom_categorie() == null || categorie.getNom_categorie().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de la catégorie ne peut pas être vide ou null.");
        }

        String query = "INSERT INTO categories (nom_categorie) VALUES (?)";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, categorie.getNom_categorie());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout de la catégorie : " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Categorie categorie) {
        // Validation du nom de la catégorie
        if (categorie.getNom_categorie() == null || categorie.getNom_categorie().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de la catégorie ne peut pas être vide ou null.");
        }

        String query = "UPDATE categories SET nom_categorie = ? WHERE id_categorie = ?";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, categorie.getNom_categorie());
            stmt.setInt(2, categorie.getId_categorie());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Catégorie avec l'ID " + categorie.getId_categorie() + " non trouvée.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de la catégorie : " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM categories WHERE id_categorie = (SELECT MAX(id_categorie) FROM categories)";
        try (Connection conn = DatabaseConnect.getConnection();
             Statement stmt = conn.createStatement()) {
            int rowsAffected = stmt.executeUpdate(query);
            if (rowsAffected == 0) {
                throw new RuntimeException("Aucune catégorie trouvée à supprimer.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de la catégorie : " + e.getMessage(), e);
        }
    }

    @Override
    public List<Categorie> afficherTous() {
        List<Categorie> categories = new ArrayList<>();
        String query = "SELECT * FROM categories";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Categorie categorie = new Categorie(
                    rs.getInt("id_categorie"),
                    rs.getString("nom_categorie")
                );
                categories.add(categorie);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des catégories : " + e.getMessage(), e);
        }
        return categories;
    }
}