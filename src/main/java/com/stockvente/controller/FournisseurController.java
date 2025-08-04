package com.stockvente.controller;

import com.stockvente.dao.FournisseurDao;
import com.stockvente.models.Fournisseur;

import java.util.List;
import java.util.regex.Pattern;

public class FournisseurController {

    private final FournisseurDao fournisseurDao;

    public FournisseurController() {
        this.fournisseurDao = new FournisseurDao();
        
    }
    
     public List<Fournisseur> getFournisseurs() {
        return fournisseurDao.afficherTous();
    }

    public String afficherTousLesFournisseurs(String role) {
        if (!role.equals("Magasinier") && !role.equals("Admin")) {
            return "Accès refusé : seul un Magasinier ou un Admin peut consulter les fournisseurs.";
        }

        List<Fournisseur> fournisseurs = fournisseurDao.afficherTous();
        if (fournisseurs.isEmpty()) {
            return "Aucun fournisseur trouvé dans la base de données.";
        }

        StringBuilder result = new StringBuilder("Liste des fournisseurs :\n");
        for (Fournisseur fournisseur : fournisseurs) {
            result.append("ID: ").append(fournisseur.getId_fournisseur())
                  .append(", Nom: ").append(fournisseur.getNom_complet_fournisseur())
                  .append(", Email: ").append(fournisseur.getEmail_fournisseur())
                  .append(", Adresse: ").append(fournisseur.getAdresse_fournisseur())
                  .append(", Téléphone: ").append(fournisseur.getTelephone_fournisseur())
                  .append("\n");
        }
        return result.toString();
    }

    public String ajouterFournisseur(String role, Fournisseur fournisseur) { // Accepts Fournisseur object
        if (!role.equals("Magasinier") && !role.equals("Admin")) {
            return "Accès refusé : seul un Magasinier ou un Admin peut ajouter un fournisseur.";
        }

        if (fournisseur == null || fournisseur.getNom_complet_fournisseur() == null || fournisseur.getNom_complet_fournisseur().trim().isEmpty()) {
            return "Erreur : le nom complet du fournisseur ne peut pas être vide.";
        }
        if (fournisseur.getEmail_fournisseur() != null && !fournisseur.getEmail_fournisseur().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            return "Erreur : l'email du fournisseur n'est pas valide.";
        }
        if (!validerTelephone(fournisseur.getTelephone_fournisseur())) {
            return "Erreur : le numéro de téléphone doit contenir uniquement des chiffres (au moins 8 chiffres).";
        }

        try {
            fournisseurDao.save(fournisseur);
            return "Fournisseur ajouté avec succès.";
        } catch (RuntimeException e) {
            return "Erreur lors de l'ajout du fournisseur : " + e.getMessage();
        }
    }

    public String mettreAJourFournisseur(String role, Fournisseur fournisseur) { // Accepts Fournisseur object
        if (!role.equals("Magasinier") && !role.equals("Admin")) {
            return "Accès refusé : seul un Magasinier ou un Admin peut mettre à jour un fournisseur.";
        }

        if (fournisseur == null || fournisseur.getId_fournisseur() <= 0) {
            return "Erreur : l'ID du fournisseur est invalide.";
        }
        if (fournisseur.getNom_complet_fournisseur() == null || fournisseur.getNom_complet_fournisseur().trim().isEmpty()) {
            return "Erreur : le nom complet du fournisseur ne peut pas être vide.";
        }
        if (fournisseur.getEmail_fournisseur() != null && !fournisseur.getEmail_fournisseur().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            return "Erreur : l'email du fournisseur n'est pas valide.";
        }
        if (!validerTelephone(fournisseur.getTelephone_fournisseur())) {
            return "Erreur : le numéro de téléphone doit contenir uniquement des chiffres (au moins 8 chiffres).";
        }

        try {
            fournisseurDao.update(fournisseur);
            return "Fournisseur avec l'ID " + fournisseur.getId_fournisseur() + " mis à jour avec succès.";
        } catch (RuntimeException e) {
            return "Erreur lors de la mise à jour du fournisseur : " + e.getMessage();
        }
    }

    public String supprimerFournisseur(String role, int id_fournisseur) { // Accepts id_fournisseur
        if (!role.equals("Magasinier") && !role.equals("Admin")) {
            return "Accès refusé : seul un Magasinier ou un Admin peut supprimer un fournisseur.";
        }
        if (id_fournisseur <= 0) {
            return "Erreur : l'ID du fournisseur doit être un entier positif.";
        }

        try {
            fournisseurDao.delete(id_fournisseur);
            return "Fournisseur avec l'ID " + id_fournisseur + " supprimé avec succès.";
        } catch (RuntimeException e) {
            return "Erreur lors de la suppression du fournisseur : " + e.getMessage();
        }
    }

    private boolean validerTelephone(String telephone) {
        if (telephone == null || telephone.trim().isEmpty()) {
            return false;
        }
        return Pattern.matches("\\d{8,}", telephone);
    }

    String ajouterFournisseur(String magasinier, String nomCompletFournisseur, String adresseFournisseur, String emailFournisseur, String telephoneFournisseur) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String mettreAJourFournisseur(String magasinier, int id_fournisseur) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String mettreAJourFournisseur(String admin, int id_fournisseur, String nom_complet_fournisseur, String adresse_fournisseur, String email_fournisseur, String telephone_fournisseur) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}