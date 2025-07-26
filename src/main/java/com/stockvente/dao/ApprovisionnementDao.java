package com.stockvente.dao;

import com.stockvente.models.Approvisionnement;
import com.stockvente.utils.DatabaseConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ApprovisionnementDao implements CrudDao<Approvisionnement> {

    @Override
    public void save(Approvisionnement approvisionnement) {
        validerChamps(approvisionnement);
        validerClesEtrangeres(approvisionnement);

        String query = "INSERT INTO approvisionnements (id_produit, id_fournisseur, quantite_approvisionnement, prix_unitaire_achat, date_approvisionnement) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, approvisionnement.getId_produit());
            stmt.setInt(2, approvisionnement.getId_fournisseur());
            stmt.setInt(3, approvisionnement.getQuantite_approvisionnement());
            stmt.setDouble(4, approvisionnement.getPrix_unitaire_achat());
            stmt.setDate(5, new java.sql.Date(approvisionnement.getDate_approvisionnement().getTime()));
            stmt.executeUpdate();

            // Récupérer l'ID généré (note : id_approvisionnement est final, donc non modifiable ici)
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                // Pas de mise à jour de l'objet, car id_approvisionnement est final
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout de l'approvisionnement : " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Approvisionnement approvisionnement) {
        validerChamps(approvisionnement);

        String query = "UPDATE approvisionnements SET quantite_approvisionnement = ?, prix_unitaire_achat = ?, date_approvisionnement = ? WHERE id_approvisionnement = ?";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, approvisionnement.getQuantite_approvisionnement());
            stmt.setDouble(2, approvisionnement.getPrix_unitaire_achat());
            stmt.setDate(3, new java.sql.Date(approvisionnement.getDate_approvisionnement().getTime()));
            stmt.setInt(4, approvisionnement.getId_approvisionnement());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Approvisionnement avec l'ID " + approvisionnement.getId_approvisionnement() + " non trouvé.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de l'approvisionnement : " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM approvisionnements WHERE id_approvisionnement = (SELECT MAX(id_approvisionnement) FROM approvisionnements)";
        try (Connection conn = DatabaseConnect.getConnection();
             Statement stmt = conn.createStatement()) {
            int rowsAffected = stmt.executeUpdate(query);
            if (rowsAffected == 0) {
                throw new RuntimeException("Aucun approvisionnement trouvé à supprimer.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de l'approvisionnement : " + e.getMessage(), e);
        }
    }

    @Override
    public List<Approvisionnement> afficherTous() {
        List<Approvisionnement> approvisionnements = new ArrayList<>();
        String query = "SELECT * FROM approvisionnements";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Approvisionnement approvisionnement = new Approvisionnement(
                    rs.getInt("id_approvisionnement"),
                    rs.getInt("id_produit"),
                    rs.getInt("id_fournisseur"),
                    rs.getInt("quantite_approvisionnement"),
                    rs.getDouble("prix_unitaire_achat"),
                    rs.getDate("date_approvisionnement")
                );
                approvisionnements.add(approvisionnement);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des approvisionnements : " + e.getMessage(), e);
        }
        return approvisionnements;
    }

    private void validerChamps(Approvisionnement approvisionnement) {
        if (approvisionnement.getQuantite_approvisionnement() < 0) {
            throw new IllegalArgumentException("La quantité d'approvisionnement ne peut pas être négative.");
        }
        if (approvisionnement.getPrix_unitaire_achat() < 0) {
            throw new IllegalArgumentException("Le prix unitaire d'achat ne peut pas être négatif.");
        }
        if (approvisionnement.getDate_approvisionnement() == null) {
            throw new IllegalArgumentException("La date d'approvisionnement ne peut pas être null.");
        }
    }

    private void validerClesEtrangeres(Approvisionnement approvisionnement) {
        String produitQuery = "SELECT COUNT(*) FROM produits WHERE id_produit = ?";
        String fournisseurQuery = "SELECT COUNT(*) FROM fournisseurs WHERE id_fournisseur = ?";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement produitStmt = conn.prepareStatement(produitQuery);
             PreparedStatement fournisseurStmt = conn.prepareStatement(fournisseurQuery)) {
            
            produitStmt.setInt(1, approvisionnement.getId_produit());
            ResultSet produitRs = produitStmt.executeQuery();
            if (produitRs.next() && produitRs.getInt(1) == 0) {
                throw new IllegalArgumentException("Le produit avec l'ID " + approvisionnement.getId_produit() + " n'existe pas.");
            }

            fournisseurStmt.setInt(1, approvisionnement.getId_fournisseur());
            ResultSet fournisseurRs = fournisseurStmt.executeQuery();
            if (fournisseurRs.next() && fournisseurRs.getInt(1) == 0) {
                throw new IllegalArgumentException("Le fournisseur avec l'ID " + approvisionnement.getId_fournisseur() + " n'existe pas.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la validation des clés étrangères : " + e.getMessage(), e);
        }
    }

   
}