package com.stockvente.controller;

import com.stockvente.dao.UtilisateurDao;
import com.stockvente.models.Utilisateur;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class UserManagementController {

    private static final Logger LOGGER = Logger.getLogger(UserManagementController.class.getName());
    private final UtilisateurDao utilisateurDao;
    private String roleConnecte;  // ❌ ENLÈVE "final" ici
    
    public UserManagementController(UtilisateurDao utilisateurDao) {
        this(utilisateurDao, "Admin");
    }

    public UserManagementController(UtilisateurDao utilisateurDao, String roleConnecte) {
        this.utilisateurDao = utilisateurDao;
        this.roleConnecte = roleConnecte;
    }

    // (optionnel) Méthode pour changer dynamiquement le rôle
    public void setRoleConnecte(String role) {
        this.roleConnecte = role;
    }


    
    



    public String ajouterUtilisateur(String username, String password, String email, String role) {
        // Vérifie si c'est bien un admin connecté
        if (!"Admin".equalsIgnoreCase(roleConnecte)) {
            return "⛔ Accès refusé : seul un Admin peut ajouter un utilisateur.";
        }

        try {
            // Crée un nouvel utilisateur
            Utilisateur utilisateur = new Utilisateur(username, password, email, role);

            // Sauvegarde via DAO
            utilisateurDao.save(utilisateur);

            LOGGER.log(Level.INFO, "✅ Utilisateur {0} ajouté avec succès.", username);
            return "✅ Utilisateur ajouté avec succès.";
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "❌ Erreur lors de l'ajout de l'utilisateur {0} : {1}", new Object[]{username, e.getMessage()});
            if (e.getMessage().contains("Duplicate entry") || e.getMessage().toLowerCase().contains("unique")) {
                return "⚠️ Nom d'utilisateur ou email déjà existant.";
            }
            return "❌ Erreur technique : " + e.getMessage();
        }
    }



    public String mettreAJourUtilisateur(int id, String username, String password, String email, String role) {
        if (role == null || !"Admin".equalsIgnoreCase(role)) {
            return "Accès refusé : seul un Admin peut mettre à jour un utilisateur.";
        }

        try {
            // Enregistrer le mot de passe en clair (non recommandé en production)
            Utilisateur utilisateur = new Utilisateur(id, username, password, email, role);
            utilisateurDao.update(utilisateur);
            LOGGER.log(Level.INFO, "Utilisateur ID {0} mis à jour avec succès.", id);
            return "Utilisateur mis à jour avec succès.";
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la mise à jour de l'utilisateur ID {0} : {1}", new Object[]{id, e.getMessage()});
            if (e.getMessage().contains("Duplicate entry")) {
                return "Erreur : le nom d'utilisateur existe déjà.";
            }
            return "Erreur lors de la mise à jour de l'utilisateur : " + e.getMessage();
        }
    }

    public String supprimerUtilisateur(int id, String role) {
        if (role == null || !"Admin".equalsIgnoreCase(role)) {
            return "Accès refusé : seul un Admin peut supprimer un utilisateur.";
        }

        try {
            utilisateurDao.delete(id);
            LOGGER.log(Level.INFO, "Utilisateur ID {0} supprimé avec succès.", id);
            return "Utilisateur supprimé avec succès.";
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la suppression de l'utilisateur ID {0} : {1}", new Object[]{id, e.getMessage()});
            return "Erreur lors de la suppression de l'utilisateur : " + e.getMessage();
        }
    }

    public List<Utilisateur> afficherTousLesUtilisateurs() {
        try {
            return utilisateurDao.afficherTous();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la récupération des utilisateurs : {0}", e.getMessage());
            return List.of(); // Retourne une liste vide en cas d'erreur
        }
    }
    
    public List<String> afficherTousLesUtilisateurs(String role) {
        if (role == null || !"Admin".equalsIgnoreCase(role)) {
            return List.of("Erreur : seul un Admin peut voir les utilisateurs.");
        }

        try {
            List<Utilisateur> utilisateurs = afficherTousLesUtilisateurs();
            return utilisateurs.stream()
                    .map(u -> String.format("ID: %d, Nom: %s, Email: %s, Rôle: %s",
                            u.getId_utilisateur(), u.getUsername(), u.getEmail(), u.getRole()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur récupération utilisateurs : {0}", e.getMessage());
            return List.of("Erreur lors de la récupération des utilisateurs : " + e.getMessage());
        }
    }
    // Méthodes inutilisées à supprimer ou implémenter selon les besoins
}
