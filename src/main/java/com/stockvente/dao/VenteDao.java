package com.stockvente.dao;

import com.stockvente.models.Vente;
import com.stockvente.utils.DatabaseConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class VenteDao implements CrudDao<Vente> {

    @Override
    public void save(Vente vente) {
        validerChamps(vente);

        String query = "INSERT INTO ventes (date_vente, id_utilisateur, id_client) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, new java.sql.Date(vente.getDate_vente().getTime()));
            stmt.setInt(2, vente.getId_utilisateur());
            stmt.setInt(3, vente.getId_client());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout de la vente : " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Vente vente) {
        validerChamps(vente);

        String query = "UPDATE ventes SET date_vente = ? WHERE id_vente = ?";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, new java.sql.Date(vente.getDate_vente().getTime()));
            stmt.setInt(2, vente.getId_vente());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Vente avec l'ID " + vente.getId_vente() + " non trouvée.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de la vente : " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM ventes WHERE id_vente = (SELECT MAX(id_vente) FROM ventes)";
        try (Connection conn = DatabaseConnect.getConnection();
             Statement stmt = conn.createStatement()) {
            int rowsAffected = stmt.executeUpdate(query);
            if (rowsAffected == 0) {
                throw new RuntimeException("Aucune vente trouvée à supprimer.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de la vente : " + e.getMessage(), e);
        }
    }

    @Override
    public List<Vente> afficherTous() {
        List<Vente> ventes = new ArrayList<>();
        String query = "SELECT * FROM ventes";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Vente vente = new Vente(
                    rs.getInt("id_vente"),
                    rs.getDate("date_vente"),
                    rs.getInt("id_utilisateur"),
                    rs.getInt("id_client")
                );
                ventes.add(vente);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des ventes : " + e.getMessage(), e);
        }
        return ventes;
    }

    private void validerChamps(Vente vente) {
        if (vente.getDate_vente() == null) {
            throw new IllegalArgumentException("La date de la vente ne peut pas être null.");
        }
        if (vente.getId_utilisateur() <= 0) {
            throw new IllegalArgumentException("L'ID de l'utilisateur doit être un entier positif.");
        }
        if (vente.getId_client() <= 0) {
            throw new IllegalArgumentException("L'ID du client doit être un entier positif.");
        }
    }
}