package com.stockvente.controller;

import com.stockvente.dao.ApprovisionnementDao;
import com.stockvente.models.Approvisionnement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ApprovisionnementController {

    private final ApprovisionnementDao approvisionnementDao;
    private final SimpleDateFormat dateFormat;

    public ApprovisionnementController() {
        this.approvisionnementDao = new ApprovisionnementDao();
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }
    
     public List<String> getHistoriqueApprovisionnements() {
        return approvisionnementDao.getHistoriqueApprovisionnements();
    }

    public String afficherTousLesApprovisionnements(String role) {
        if (!role.equals("Magasinier") && !role.equals("Admin")) {
            return "Accès refusé : seul un Magasinier ou un Admin peut consulter les approvisionnements.";
        }

        List<Approvisionnement> approvisionnements = approvisionnementDao.afficherTous();
        if (approvisionnements.isEmpty()) {
            return "Aucun approvisionnement trouvé dans la base de données.";
        }

        StringBuilder result = new StringBuilder("Liste des approvisionnements :\n");
        for (Approvisionnement approvisionnement : approvisionnements) {
            result.append("ID: ").append(approvisionnement.getId_approvisionnement())
                  .append(", Produit ID: ").append(approvisionnement.getId_produit())
                  .append(", Fournisseur ID: ").append(approvisionnement.getId_fournisseur())
                  .append(", Quantité: ").append(approvisionnement.getQuantite_approvisionnement())
                  .append(", Prix unitaire: ").append(approvisionnement.getPrix_unitaire_achat())
                  .append(", Montant total: ").append(approvisionnement.getMontantTotalAchat())
                  .append(", Date: ").append(dateFormat.format(approvisionnement.getDate_approvisionnement()))
                  .append("\n");
        }
        return result.toString();
    }

    public String ajouterApprovisionnement(String role, Approvisionnement approvisionnement) { // Accepts Approvisionnement object
        if (!role.equals("Magasinier") && !role.equals("Admin")) {
            return "Accès refusé : seul un Magasinier ou un Admin peut ajouter un approvisionnement.";
        }

        if (approvisionnement == null) {
            return "Erreur : l'approvisionnement ne peut pas être null.";
        }
        if (approvisionnement.getId_produit() <= 0 || approvisionnement.getId_fournisseur() <= 0) {
            return "Erreur : les IDs du produit et du fournisseur doivent être des entiers positifs.";
        }
        if (approvisionnement.getQuantite_approvisionnement() <= 0) {
            return "Erreur : la quantité doit être un entier positif.";
        }
        if (approvisionnement.getPrix_unitaire_achat() <= 0) {
            return "Erreur : le prix unitaire doit être positif.";
        }
        if (!validerDate(approvisionnement.getDate_approvisionnement())) {
            return "Erreur : la date d'approvisionnement ne peut pas être dans le futur (après aujourd'hui).";
        }

        try {
            approvisionnementDao.save(approvisionnement);
            return "Approvisionnement ajouté avec succès.";
        } catch (RuntimeException e) {
            return "Erreur lors de l'ajout de l'approvisionnement : " + e.getMessage();
        }
    }

    public String mettreAJourApprovisionnement(String role, Approvisionnement approvisionnement) { // Accepts Approvisionnement object
        if (!role.equals("Magasinier") && !role.equals("Admin")) {
            return "Accès refusé : seul un Magasinier ou un Admin peut mettre à jour un approvisionnement.";
        }

        if (approvisionnement == null || approvisionnement.getId_approvisionnement() <= 0) {
            return "Erreur : l'ID de l'approvisionnement est invalide.";
        }
        if (approvisionnement.getId_produit() <= 0 || approvisionnement.getId_fournisseur() <= 0) {
            return "Erreur : les IDs du produit et du fournisseur doivent être des entiers positifs.";
        }
        if (approvisionnement.getQuantite_approvisionnement() <= 0) {
            return "Erreur : la quantité doit être un entier positif.";
        }
        if (approvisionnement.getPrix_unitaire_achat() <= 0) {
            return "Erreur : le prix unitaire doit être positif.";
        }
        if (!validerDate(approvisionnement.getDate_approvisionnement())) {
            return "Erreur : la date d'approvisionnement ne peut pas être dans le futur (après aujourd'hui).";
        }

        try {
            approvisionnementDao.update(approvisionnement);
            return "Approvisionnement avec l'ID " + approvisionnement.getId_approvisionnement() + " mis à jour avec succès.";
        } catch (RuntimeException e) {
            return "Erreur lors de la mise à jour de l'approvisionnement : " + e.getMessage();
        }
    }

    public String supprimerApprovisionnement(String role, int id_approvisionnement) { // Accepts id_approvisionnement
        if (!role.equals("Magasinier") && !role.equals("Admin")) {
            return "Accès refusé : seul un Magasinier ou un Admin peut supprimer un approvisionnement.";
        }
        if (id_approvisionnement <= 0) {
            return "Erreur : l'ID de l'approvisionnement doit être un entier positif.";
        }

        try {
            approvisionnementDao.delete(id_approvisionnement);
            return "Approvisionnement avec l'ID " + id_approvisionnement + " supprimé avec succès.";
        } catch (RuntimeException e) {
            return "Erreur lors de la suppression de l'approvisionnement : " + e.getMessage();
        }
    }

    private boolean validerDate(Date date) {
        if (date == null) {
            return false;
        }
        Date dateActuelle = new Date();
        return !date.after(dateActuelle);
    }

    String supprimerApprovisionnement(String magasinier) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String mettreAJourApprovisionnement(String admin, int id_approvisionnement, int id_produit, int id_fournisseur, int quantite_approvisionnement, float prix_unitaire_achat, Date date_approvisionnement) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String ajouterApprovisionnement(String admin, int id_produit, int id_fournisseur, int quantite_approvisionnement, float prix_unitaire_achat, Date date_approvisionnement) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
} 