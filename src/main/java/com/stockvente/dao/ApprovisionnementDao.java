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

          try (Connection conn = DatabaseConnect.getConnection()) {

              String checkQuery = "SELECT quantite_approvisionnement FROM approvisionnements WHERE id_produit = ? AND id_fournisseur = ?";
              PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
              checkStmt.setInt(1, approvisionnement.getId_produit());
              checkStmt.setInt(2, approvisionnement.getId_fournisseur());

              ResultSet rs = checkStmt.executeQuery();

              if (rs.next()) {
                  // Approvisionnement existant : on met à jour la quantité ET la date (à la date actuelle ou fournie)
                  int ancienneQuantite = rs.getInt("quantite_approvisionnement");
                  int nouvelleQuantite = ancienneQuantite + approvisionnement.getQuantite_approvisionnement();

                  String updateQuery = "UPDATE approvisionnements SET quantite_approvisionnement = ?, date_approvisionnement = ?, prix_unitaire_achat = ? WHERE id_produit = ? AND id_fournisseur = ?";
                  PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                  updateStmt.setInt(1, nouvelleQuantite);

                  // Met à jour la date avec la date de l'objet approvisionnement (ou new java.sql.Date(System.currentTimeMillis()) si tu veux la date actuelle)
                  updateStmt.setDate(2, new java.sql.Date(approvisionnement.getDate_approvisionnement().getTime()));

                  updateStmt.setDouble(3, approvisionnement.getPrix_unitaire_achat());
                  updateStmt.setInt(4, approvisionnement.getId_produit());
                  updateStmt.setInt(5, approvisionnement.getId_fournisseur());

                  updateStmt.executeUpdate();

                  // Ici aussi tu peux mettre à jour le stock (quantite_stock) en ajoutant nouvelleQuantite
                  mettreAJourStock(conn, approvisionnement.getId_produit(), nouvelleQuantite);

              } else {
                  // Nouvel approvisionnement → insert + stock
                  String insertQuery = "INSERT INTO approvisionnements (id_produit, id_fournisseur, quantite_approvisionnement, prix_unitaire_achat, date_approvisionnement) VALUES (?, ?, ?, ?, ?)";
                  PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                  insertStmt.setInt(1, approvisionnement.getId_produit());
                  insertStmt.setInt(2, approvisionnement.getId_fournisseur());
                  insertStmt.setInt(3, approvisionnement.getQuantite_approvisionnement());
                  insertStmt.setDouble(4, approvisionnement.getPrix_unitaire_achat());
                  insertStmt.setDate(5, new java.sql.Date(approvisionnement.getDate_approvisionnement().getTime()));

                  insertStmt.executeUpdate();

                  // Ajouter dans le stock (quantite_stock = quantite_approvisionnement)
                  mettreAJourStock(conn, approvisionnement.getId_produit(), approvisionnement.getQuantite_approvisionnement());
                }

            } catch (SQLException e) {
              throw new RuntimeException("Erreur lors de l'ajout/mise à jour de l'approvisionnement : " + e.getMessage(), e);
          }
    }

      // Méthode pour mettre à jour la quantité dans la table stock
        private void mettreAJourStock(Connection conn, int idProduit, int quantite) throws SQLException {
            // Vérifier si le produit existe dans stock
            String checkStockQuery = "SELECT quantite_stock FROM stocks WHERE id_produit = ?";
            PreparedStatement checkStockStmt = conn.prepareStatement(checkStockQuery);
            checkStockStmt.setInt(1, idProduit);
            ResultSet rsStock = checkStockStmt.executeQuery();

            if (rsStock.next()) {
                // Produit existe → mise à jour quantite_stock
                int quantiteStockExistante = rsStock.getInt("quantite_stock");
                int nouvelleQuantiteStock = quantite; // On remplace par la nouvelle quantité (tu peux aussi additionner si tu préfères)

                String updateStockQuery = "UPDATE stocks SET quantite_stock = ? WHERE id_produit = ?";
                PreparedStatement updateStockStmt = conn.prepareStatement(updateStockQuery);
                updateStockStmt.setInt(1, nouvelleQuantiteStock);
                updateStockStmt.setInt(2, idProduit);

                updateStockStmt.executeUpdate();
              } else {
                // Produit absent → insertion dans stock
                String insertStockQuery = "INSERT INTO stocks (id_produit, quantite_stock) VALUES (?, ?)";
                PreparedStatement insertStockStmt = conn.prepareStatement(insertStockQuery);
                insertStockStmt.setInt(1, idProduit);
                insertStockStmt.setInt(2, quantite);

                insertStockStmt.executeUpdate();
            }
        }
    
        
    public List<String> getHistoriqueApprovisionnements() {
        List<String> historique = new ArrayList<>();
        String query = """
            SELECT a.date_approvisionnement, p.nom_produit, f.nom_complet_fournisseur, a.quantite_approvisionnement, a.prix_unitaire_achat
            FROM approvisionnements a
            JOIN produits p ON a.id_produit = p.id_produit
            JOIN fournisseurs f ON a.id_fournisseur = f.id_fournisseur
            ORDER BY a.date_approvisionnement DESC
        """;

        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String ligne = String.format(
                    "Le %s : %s approvisionné de %d unités à %.2f FBU par %s",
                    rs.getDate("date_approvisionnement").toString(),
                    rs.getString("nom_produit"),
                    rs.getInt("quantite_approvisionnement"),
                    rs.getDouble("prix_unitaire_achat"),
                    rs.getString("nom_complet_fournisseur")
                );
                historique.add(ligne);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historique;
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