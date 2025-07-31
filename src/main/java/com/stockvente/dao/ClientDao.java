package com.stockvente.dao;

import com.stockvente.models.Client;
import com.stockvente.utils.DatabaseConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDao implements CrudDao<Client> {
    @Override
  
    public void save(Client client) {
    validerChamps(client);

    String query = "INSERT INTO clients (nom_client, prenom_client, adresse_client, telephone_client) VALUES (?, ?, ?, ?)";
    try (Connection conn = DatabaseConnect.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        conn.setAutoCommit(false); // Désactiver l'auto-commit

        stmt.setString(1, client.getNom_client());
        stmt.setString(2, client.getPrenom_client());
        stmt.setString(3, client.getAdresse_client());
        stmt.setString(4, client.getTelephone_client());

        int rows = stmt.executeUpdate();
        if (rows > 0) {
            System.out.println("Client inséré avec succès !");
        } else {
            System.out.println("Aucun client inséré !");
        }

        conn.commit(); // Valider la transaction

    } catch (SQLException e) {
        try (Connection conn = DatabaseConnect.getConnection()) {
            conn.rollback(); // Annuler en cas d'erreur
        } catch (SQLException rollbackEx) {
            rollbackEx.printStackTrace();
        }
        throw new RuntimeException("Erreur lors de l'ajout du client : " + e.getMessage(), e);
    }
}


    @Override
    public void update(Client client) {
        validerChamps(client);

        String query = "UPDATE clients SET nom_client = ?, prenom_client = ?, adresse_client = ?, telephone_client = ? WHERE id_client = ?";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, client.getNom_client());
            stmt.setString(2, client.getPrenom_client());
            stmt.setString(3, client.getAdresse_client());
            stmt.setString(4, client.getTelephone_client());
            stmt.setInt(5, client.getId_client());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Client avec l'ID " + client.getId_client() + " non trouvé.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du client : " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM clients WHERE id_client = (SELECT MAX(id_client) FROM clients)";
        try (Connection conn = DatabaseConnect.getConnection();
             Statement stmt = conn.createStatement()) {
            int rowsAffected = stmt.executeUpdate(query);
            if (rowsAffected == 0) {
                throw new RuntimeException("Aucun client trouvé à supprimer.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du client : " + e.getMessage(), e);
        }
    }

    @Override
    public List<Client> afficherTous() {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT * FROM clients";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Client client = new Client(
                    rs.getInt("id_client"),
                    rs.getString("nom_client"),
                    rs.getString("prenom_client"),
                    rs.getString("adresse_client"),
                    rs.getString("telephone_client")
                );
                clients.add(client);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des clients : " + e.getMessage(), e);
        }
        return clients;
    }

    private void validerChamps(Client client) {
        if (client.getNom_client() == null || client.getNom_client().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du client ne peut pas être vide ou null.");
        }
        if (client.getPrenom_client() == null || client.getPrenom_client().trim().isEmpty()) {
            throw new IllegalArgumentException("Le prénom du client ne peut pas être vide ou null.");
        }
        if (client.getAdresse_client() == null || client.getAdresse_client().trim().isEmpty()) {
            throw new IllegalArgumentException("L'adresse du client ne peut pas être vide ou null.");
        }
        if (client.getTelephone_client() == null || client.getTelephone_client().trim().isEmpty()) {
            throw new IllegalArgumentException("Le numéro de téléphone du client ne peut pas être vide ou null.");
        }
        // Validation simple du format du numéro de téléphone (exemple : au moins 10 chiffres)
        if (!client.getTelephone_client().matches("\\d{8,}")) {
            throw new IllegalArgumentException("Le numéro de téléphone doit contenir au moins 8 chiffres.");
        }
    }

 
}