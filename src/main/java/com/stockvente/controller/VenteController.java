package com.stockvente.controller;

import com.stockvente.dao.VenteDao;
import com.stockvente.models.Vente;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VenteController {

    private final VenteDao venteDao;
    private final SimpleDateFormat dateFormat;

    public VenteController() {
        this.venteDao = new VenteDao();
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    public String afficherToutesLesVentes(String role) {
        if (!role.equals("Vendeur") && !role.equals("Admin")) {
            return "Accès refusé : rôle non autorisé.";
        }

        List<Vente> ventes = venteDao.afficherTous();
        if (ventes.isEmpty()) {
            return "Aucune vente trouvée dans la base de données.";
        }

        StringBuilder result = new StringBuilder("Liste des ventes :\n");
        for (Vente vente : ventes) {
            result.append("ID: ").append(vente.getId_vente())
                  .append(", Date: ").append(dateFormat.format(vente.getDate_vente()))
                  .append(", Utilisateur ID: ").append(vente.getId_utilisateur())
                  .append(", Client ID: ").append(vente.getId_client())
                  .append("\n");
        }
        return result.toString();
    }

    public String ajouterVente(String role, Vente vente) { // Accepts Vente object
        if (!role.equals("Vendeur") && !role.equals("Admin")) {
            return "Accès refusé : seul un Vendeur ou un Admin peut ajouter une vente.";
        }

        if (vente == null) {
            return "Erreur : la vente ne peut pas être nulle.";
        }
        if (vente.getDate_vente() == null) {
            return "Erreur : la date de vente ne peut pas être nulle.";
        }
        if (vente.getId_utilisateur() <= 0 || vente.getId_client() <= 0) {
            return "Erreur : les IDs de l'utilisateur et du client doivent être des entiers positifs.";
        }
        if (!validerDate(vente.getDate_vente())) { // Added date validation
            return "Erreur : la date de vente ne peut pas être dans le futur.";
        }

        try {
            venteDao.save(vente);
            return "Vente ajoutée avec succès.";
        } catch (RuntimeException e) {
            return "Erreur lors de l'ajout de la vente : " + e.getMessage();
        }
    }

    public String mettreAJourVente(String role, Vente vente) { // New method for updating
        if (!role.equals("Admin") && !role.equals("Vendeur")) {
            return "Accès refusé : seul un Admin ou un Vendeur peut modifier une vente.";
        }
        if (vente == null || vente.getId_vente() <= 0) {
            return "Erreur : l'ID de la vente est invalide.";
        }
        if (vente.getDate_vente() == null) {
            return "Erreur : la date de vente ne peut pas être nulle.";
        }
        if (vente.getId_utilisateur() <= 0 || vente.getId_client() <= 0) {
            return "Erreur : les IDs de l'utilisateur et du client doivent être des entiers positifs.";
        }
        if (!validerDate(vente.getDate_vente())) {
            return "Erreur : la date de vente ne peut pas être dans le futur.";
        }
        try {
            venteDao.update(vente);
            return "Vente avec l'ID " + vente.getId_vente() + " mise à jour avec succès.";
        } catch (RuntimeException e) {
            return "Erreur lors de la mise à jour de la vente : " + e.getMessage();
        }
    }

    public String supprimerVente(String role, int id_vente) { // Accepts id_vente
        if (!role.equals("Admin")) {
            return "Accès refusé : seul un Admin peut supprimer une vente.";
        }
        if (id_vente <= 0) {
            return "Erreur : l'ID de la vente doit être un entier positif.";
        }

        try {
            venteDao.delete(id_vente);
            return "Vente avec l'ID " + id_vente + " supprimée avec succès.";
        } catch (RuntimeException e) {
            return "Erreur lors de la suppression de la vente : " + e.getMessage();
        }
    }

    public List<Vente> getToutesLesVentes(String role) {
        if (!role.equals("Vendeur") && !role.equals("Admin")) {
            throw new SecurityException("Accès refusé : rôle non autorisé.");
        }
        return venteDao.afficherTous();
    }

    private boolean validerDate(Date date) {
        if (date == null) {
            return false;
        }
        Date dateActuelle = new Date();
        return !date.after(dateActuelle);
    }

    String ajouterVente(String vendeur, Date date_vente, int id_utilisateur, int id_client) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String mettreAJourVente(String admin, int id_vente, Date date_vente, int id_utilisateur, int id_client) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}