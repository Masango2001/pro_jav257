package com.stockvente.dao;

import com.stockvente.models.Concerner;
import com.stockvente.utils.DatabaseConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConcernerDao implements CrudDao<Concerner> {

    @Override
    public void save(Concerner concerner) {
        try {
            // Cette méthode pourrait ouvrir une nouvelle connexion
            // Modifiez-la pour accepter une connexion en paramètre
            save(concerner, DatabaseConnect.getConnection());
        } catch (SQLException ex) {
            Logger.getLogger(ConcernerDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void save(Concerner concerner, Connection conn) throws SQLException {
        String query = "INSERT INTO concerner (id_vente, id_produit, quantite_vendue, prix_unitaire_vendue) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, concerner.getId_vente());
            stmt.setInt(2, concerner.getId_produit());
            stmt.setInt(3, concerner.getQuantite_vendue());
            stmt.setDouble(4, concerner.getPrix_unitaire_vendue());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Échec de l'insertion de la ligne de vente.");
            }
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
    public String getProduitLePlusVenduJour() {
        String query = """
            SELECT p.nom_produit, SUM(c.quantite_vendue) as total_vendu
            FROM concerner c
            JOIN produits p ON c.id_produit = p.id_produit
            JOIN ventes v ON c.id_vente = v.id_vente
            WHERE DATE(v.date_vente) = CURDATE()
            GROUP BY p.nom_produit
            ORDER BY total_vendu DESC
            LIMIT 1
        """;

        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getString("nom_produit") + " (" + rs.getInt("total_vendu") + " unités)";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "Aucun produit vendu aujourd'hui";
    }
    public double getTotalVentesJour() {
        String query = """
            SELECT IFNULL(SUM(c.quantite_vendue * c.prix_unitaire_vendue), 0) as total
            FROM concerner c
            JOIN ventes v ON c.id_vente = v.id_vente
            WHERE DATE(v.date_vente) = CURDATE()
        """;

        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("total");  // plus besoin de wasNull grâce au IFNULL en SQL
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }



}