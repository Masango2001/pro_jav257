package com.stockvente.dao;

import com.stockvente.models.Fournisseur;
import com.stockvente.utils.DatabaseConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FournisseurDao implements CrudDao<Fournisseur> {

    @Override
    public void save(Fournisseur fournisseur) {
        validerChamps(fournisseur);

        String query = "INSERT INTO fournisseurs (nom_complet_fournisseur, adresse_fournisseur, email_fournisseur, telephone_fournisseur) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, fournisseur.getNom_complet_fournisseur());
            stmt.setString(2, fournisseur.getAdresse_fournisseur());
            stmt.setString(3, fournisseur.getEmail_fournisseur());
            stmt.setString(4, fournisseur.getTelephone_fournisseur());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout du fournisseur : " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Fournisseur fournisseur) {
        validerChamps(fournisseur);

        String query = "UPDATE fournisseurs SET nom_complet_fournisseur = ?, adresse_fournisseur = ?, email_fournisseur = ?, telephone_fournisseur = ? WHERE id_fournisseur = ?";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, fournisseur.getNom_complet_fournisseur());
            stmt.setString(2, fournisseur.getAdresse_fournisseur());
            stmt.setString(3, fournisseur.getEmail_fournisseur());
            stmt.setString(4, fournisseur.getTelephone_fournisseur());
            stmt.setInt(5, fournisseur.getId_fournisseur());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Fournisseur avec l'ID " + fournisseur.getId_fournisseur() + " non trouvé.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du fournisseur : " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM fournisseurs WHERE id_fournisseur = ?";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Aucun fournisseur trouvé avec l'ID " + id + " à supprimer.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du fournisseur : " + e.getMessage(), e);
        }
    }

    @Override
    public List<Fournisseur> afficherTous() {
        List<Fournisseur> fournisseurs = new ArrayList<>();
        String query = "SELECT * FROM fournisseurs";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Fournisseur fournisseur = new Fournisseur(
                    rs.getInt("id_fournisseur"),
                    rs.getString("nom_complet_fournisseur"),
                    rs.getString("adresse_fournisseur"),
                    rs.getString("email_fournisseur"),
                    rs.getString("telephone_fournisseur")
                );
                fournisseurs.add(fournisseur);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des fournisseurs : " + e.getMessage(), e);
        }
        return fournisseurs;
    }

    private void validerChamps(Fournisseur fournisseur) {
        if (fournisseur.getNom_complet_fournisseur() == null || fournisseur.getNom_complet_fournisseur().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom complet du fournisseur ne peut pas être vide ou null.");
        }
        if (fournisseur.getAdresse_fournisseur() == null || fournisseur.getAdresse_fournisseur().trim().isEmpty()) {
            throw new IllegalArgumentException("L'adresse du fournisseur ne peut pas être vide ou null.");
        }
        if (fournisseur.getEmail_fournisseur() == null || fournisseur.getEmail_fournisseur().trim().isEmpty()) {
            throw new IllegalArgumentException("L'email du fournisseur ne peut pas être vide ou null.");
        }
        if (!fournisseur.getEmail_fournisseur().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("L'email du fournisseur doit être valide.");
        }
        if (fournisseur.getTelephone_fournisseur() == null || fournisseur.getTelephone_fournisseur().trim().isEmpty()) {
            throw new IllegalArgumentException("Le téléphone du fournisseur ne peut pas être vide ou null.");
        }
        if (!fournisseur.getTelephone_fournisseur().matches("\\d{10,15}")) {
            throw new IllegalArgumentException("Le numéro de téléphone doit contenir entre 10 et 15 chiffres.");
        }
        // Ajout de limites de longueur pour éviter les dépassements dans la base de données
        if (fournisseur.getNom_complet_fournisseur().length() > 100 ||
            fournisseur.getAdresse_fournisseur().length() > 200 ||
            fournisseur.getEmail_fournisseur().length() > 100 ||
            fournisseur.getTelephone_fournisseur().length() > 20) {
            throw new IllegalArgumentException("Un ou plusieurs champs dépassent la longueur maximale autorisée.");
        }
    }
}