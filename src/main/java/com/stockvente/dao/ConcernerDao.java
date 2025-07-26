package com.stockvente.dao;

import com.stockvente.models.Concerner;
import com.stockvente.utils.DatabaseConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ConcernerDao implements CrudDao<Concerner> {

    @Override
    public void save(Concerner concerner) {
        validerChamps(concerner);

        String query = "INSERT INTO concerner (id_vente, id_produit, quantite_vendue, prix_unitaire_vendue) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, concerner.getId_vente());
            stmt.setInt(2, concerner.getId_produit());
            stmt.setInt(3, concerner.getQuantite_vendue());
            stmt.setDouble(4, concerner.getPrix_unitaire_vendue());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout de la relation Concerner : " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Concerner concerner) {
        validerChamps(concerner);

        String query = "UPDATE concerner SET quantite_vendue = ?, prix_unitaire_vendue = ? WHERE id_vente = ? AND id_produit = ?";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, concerner.getQuantite_vendue());
            stmt.setDouble(2, concerner.getPrix_unitaire_vendue());
            stmt.setInt(3, concerner.getId_vente());
            stmt.setInt(4, concerner.getId_produit());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Relation Concerner avec ID_Vente " + concerner.getId_vente() + " et ID_Produit " + concerner.getId_produit() + " non trouvée.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de la relation Concerner : " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM concerner WHERE (id_vente, id_produit) IN (SELECT id_vente, id_produit FROM concerner WHERE id_vente = (SELECT MAX(id_vente) FROM concerner))";
        try (Connection conn = DatabaseConnect.getConnection();
             Statement stmt = conn.createStatement()) {
            int rowsAffected = stmt.executeUpdate(query);
            if (rowsAffected == 0) {
                throw new RuntimeException("Aucune relation Concerner trouvée à supprimer.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de la relation Concerner : " + e.getMessage(), e);
        }
    }

    @Override
    public List<Concerner> afficherTous() {
        List<Concerner> concerneItems = new ArrayList<>();
        String query = "SELECT * FROM concerner";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Concerner concerner = new Concerner(
                    rs.getInt("id_vente"),
                    rs.getInt("id_produit"),
                    rs.getInt("quantite_vendue"),
                    rs.getDouble("prix_unitaire_vendue")
                );
                concerneItems.add(concerner);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des éléments Concerner : " + e.getMessage(), e);
        }
        return concerneItems;
    }

    private void validerChamps(Concerner concerner) {
        if (concerner.getQuantite_vendue() <= 0) {
            throw new IllegalArgumentException("La quantité vendue doit être un entier positif.");
        }
        if (concerner.getPrix_unitaire_vendue() < 0) {
            throw new IllegalArgumentException("Le prix unitaire vendu ne peut pas être négatif.");
        }
    }
}