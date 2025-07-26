package com.stockvente.dao;

import com.stockvente.models.Utilisateur;
import com.stockvente.utils.DatabaseConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDao implements CrudDao<Utilisateur> {

    @Override
    public void save(Utilisateur utilisateur) {
        // Vérifier l'unicité du username et de l'email
        String checkQuery = "SELECT COUNT(*) FROM utilisateurs WHERE username = ? OR email = ?";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            checkStmt.setString(1, utilisateur.getUsername());
            checkStmt.setString(2, utilisateur.getEmail());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                throw new SQLException("Le nom d'utilisateur ou l'email existe déjà.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification de l'unicité : " + e.getMessage(), e);
        }

        // Insérer l'utilisateur (mot de passe en clair)
        String insertQuery = "INSERT INTO utilisateurs (username, password, email, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, utilisateur.getUsername());
            stmt.setString(2, utilisateur.getPassword()); // Stockage en clair
            stmt.setString(3, utilisateur.getEmail());
            stmt.setString(4, utilisateur.getRole());
            stmt.executeUpdate();

            // Vérifier la génération de l'ID
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new SQLException("Échec de la récupération de l'ID auto-incrémenté.");
            }
            // L'ID est généré mais pas assigné à l'objet (pas de setId_utilisateur)
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Utilisateur utilisateur) {
        // Vérifier l'unicité du username et de l'email (sauf pour l'utilisateur actuel)
        String checkQuery = "SELECT COUNT(*) FROM utilisateurs WHERE (username = ? OR email = ?) AND id_utilisateur != ?";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            checkStmt.setString(1, utilisateur.getUsername());
            checkStmt.setString(2, utilisateur.getEmail());
            checkStmt.setInt(3, utilisateur.getId_utilisateur());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                throw new SQLException("Le nom d'utilisateur ou l'email existe déjà pour un autre utilisateur.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification de l'unicité : " + e.getMessage(), e);
        }

        // Mettre à jour l'utilisateur (mot de passe en clair)
        String updateQuery = "UPDATE utilisateurs SET username = ?, password = ?, email = ?, role = ? WHERE id_utilisateur = ?";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setString(1, utilisateur.getUsername());
            stmt.setString(2, utilisateur.getPassword()); // Stockage en clair
            stmt.setString(3, utilisateur.getEmail());
            stmt.setString(4, utilisateur.getRole());
            stmt.setInt(5, utilisateur.getId_utilisateur());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Utilisateur avec l'ID " + utilisateur.getId_utilisateur() + " non trouvé.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de l'utilisateur : " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        String deleteQuery = "DELETE FROM utilisateurs WHERE id_utilisateur = ?";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Utilisateur avec l'ID " + id + " non trouvé.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de l'utilisateur : " + e.getMessage(), e);
        }
    }

    @Override
public List<Utilisateur> afficherTous() {
    List<Utilisateur> utilisateurs = new ArrayList<>();
    String query = "SELECT id_utilisateur, username, password, email, role FROM utilisateurs";
    try (Connection conn = DatabaseConnect.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            Utilisateur utilisateur = Utilisateur.fromDatabase(
                rs.getInt("id_utilisateur"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getString("role")
            );
            utilisateurs.add(utilisateur);
        }
    } catch (SQLException e) {
        throw new RuntimeException("Erreur lors de la récupération des utilisateurs : " + e.getMessage(), e);
    }
    return utilisateurs;
}

}