package com.stockvente.controller;
import com.stockvente.dao.StockDao;
import com.stockvente.dao.ConcernerDao;
import com.stockvente.models.Concerner;
import com.stockvente.utils.DatabaseConnect;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;

public class ConcernerController {

    private final ConcernerDao concernerDao;
    private final StockDao stockDao;

    public ConcernerController() {
        this.concernerDao = new ConcernerDao();
        this.stockDao = new StockDao();
    }
    
    public double getTotalVentesDuJour() {
        return concernerDao.getTotalVentesJour();
    }

    public String getProduitLePlusVenduDuJour() {
        return concernerDao.getProduitLePlusVenduJour();
    }

    public List<Concerner> getToutesLesLignesDeVente(String role) {
        if (!role.equals("Vendeur") && !role.equals("Admin")) {
            throw new SecurityException("Accès refusé : rôle non autorisé.");
        }
        return concernerDao.afficherTous();
        
    }
    public String ajouterLigneDeVente(String role, Concerner concerner) {
      if (!role.equals("Vendeur") && !role.equals("Admin")) {
          return "Accès refusé : seul un Vendeur ou un Admin peut ajouter une ligne de vente.";
      }

      Connection conn = null;
      try {
          conn = DatabaseConnect.getConnection();
          conn.setAutoCommit(false); // Désactiver auto-commit pour la transaction

          // 1. Vérifie le stock disponible
          int quantiteDisponible = stockDao.getQuantiteStockParProduit(concerner.getId_produit(), conn);
          if (concerner.getQuantite_vendue() > quantiteDisponible) {
              return "Erreur : Stock insuffisant. Disponible : " + quantiteDisponible +
                     ", demandé : " + concerner.getQuantite_vendue();
          }

          // 2. Enregistre la ligne de vente en réutilisant la connexion
          concernerDao.save(concerner, conn);

          // 3. Diminue le stock en réutilisant la même connexion
          stockDao.diminuerStock(concerner.getId_produit(), concerner.getQuantite_vendue(), conn);

          conn.commit(); // Valider la transaction
          return "✅ Ligne de vente ajoutée et stock mis à jour avec succès.";
        } catch (SQLException e) {
          if (conn != null) {
              try {
                  conn.rollback(); // Annuler en cas d'erreur
                } catch (SQLException rollbackEx) {
                  return "❌ Erreur lors du rollback : " + rollbackEx.getMessage();
                }
            }
          return "❌ Erreur SQL : " + e.getMessage();
        } catch (RuntimeException e) {
          return "❌ Erreur lors de l'ajout de la ligne de vente : " + e.getMessage();
        } finally {
          if (conn != null) {
              try {
                  conn.setAutoCommit(true); // Réactiver auto-commit
                  conn.close();
                } catch (SQLException closeEx) {
                  return "❌ Erreur lors de la fermeture de la connexion : " + closeEx.getMessage();
               }
            }
        }
    }



    String ajouterLigneDeVente(String admin, int id_vente, int id_produit, String quantite_vendue, double prix_unitaire_vendue) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String afficherConcerner(String vendeur) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String mettreAJourLigneDeVente(String admin, int id_vente, int id_produit, int quantite_vendue, double prix_unitaire_vendue) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String supprimerLigneDeVente(String admin, int id_vente, int id_produit) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String ajouterLigneDeVente(String admin, int id_vente, int id_produit, int quantite_vendue, double prix_unitaire_vendue) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}