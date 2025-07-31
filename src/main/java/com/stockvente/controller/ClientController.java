package com.stockvente.controller;

import com.stockvente.dao.ClientDao;
import com.stockvente.models.Client;

import java.util.List;
import java.util.regex.Pattern;

public class ClientController {

    private final ClientDao clientDao;

    public ClientController() {
        this.clientDao = new ClientDao();
    }

    public String afficherTousLesClients(String role) {
        if (!role.equals("Vendeur") && !role.equals("Admin")) {
            return "Accè refusé : rôle non autorisé.";
        }

        List<Client> clients = clientDao.afficherTous();
        if (clients.isEmpty()) {
            return "Aucun client trouvé .";
        }

        StringBuilder result = new StringBuilder("Liste des clients :\n");
        for (Client client : clients) {
            result.append("ID: ").append(client.getId_client())
                  .append(", Nom: ").append(client.getNom_client())
                  .append(", Prénom: ").append(client.getPrenom_client())
                  .append(", Adresse: ").append(client.getAdresse_client())
                  .append(", Téléphone: ").append(client.getTelephone_client() != null ? client.getTelephone_client() : "Non renseigné")
                  .append("\n");
        }
        return result.toString();
    }

    public String ajouterClient(String role, Client client) { // Accepts Client object
        if (!role.equals("Vendeur") && !role.equals("Admin")) {
            return "Accès refusé : seul un Vendeur ou un Admin peut ajouter un client.";
        }

        if (client == null) {
            return "Erreur : le client ne peut pas être null.";
        }
        if (client.getNom_client() == null || client.getNom_client().trim().isEmpty()) {
            return "Erreur : le nom du client ne peut pas être vide ou null.";
        }
        if (client.getPrenom_client() == null || client.getPrenom_client().trim().isEmpty()) {
            return "Erreur : le prénom du client ne peut pas être vide ou null.";
        }
        if (client.getAdresse_client() == null || client.getAdresse_client().trim().isEmpty()) {
            return "Erreur : l'adresse du client ne peut pas être vide ou null.";
        }
        if (client.getTelephone_client() != null && !client.getTelephone_client().trim().isEmpty() && !validerTelephone(client.getTelephone_client())) {
            return "Erreur : le numéro de téléphone doit contenir uniquement des chiffres (au moins 8 chiffres).";
        }

        try {
            clientDao.save(client);
            return "Client ajouté avec succès.";
        } catch (RuntimeException e) {
            return "Erreur lors de l'ajout du client : " + e.getMessage();
        }
    }

    public String mettreAJourClient(String role, Client client) { // New method for updating client
        if (!role.equals("Vendeur") && !role.equals("Admin")) {
            return "Accès refusé : seul un Vendeur ou un Admin peut mettre à jour un client.";
        }
        if (client == null || client.getId_client() <= 0) {
            return "Erreur : l'ID du client est invalide.";
        }
        if (client.getNom_client() == null || client.getNom_client().trim().isEmpty()) {
            return "Erreur : le nom du client ne peut pas être vide ou null.";
        }
        if (client.getPrenom_client() == null || client.getPrenom_client().trim().isEmpty()) {
            return "Erreur : le prénom du client ne peut pas être vide ou null.";
        }
        if (client.getAdresse_client() == null || client.getAdresse_client().trim().isEmpty()) {
            return "Erreur : l'adresse du client ne peut pas être vide ou null.";
        }
        if (client.getTelephone_client() != null && !client.getTelephone_client().trim().isEmpty() && !validerTelephone(client.getTelephone_client())) {
            return "Erreur : le numéro de téléphone doit contenir uniquement des chiffres (au moins 8 chiffres).";
        }

        try {
            clientDao.update(client);
            return "Client avec l'ID " + client.getId_client() + " mis à jour avec succès.";
        } catch (RuntimeException e) {
            return "Erreur lors de la mise à jour du client : " + e.getMessage();
        }
    }

    public String supprimerClient(String role, int id_client) { // Accepts id_client
        if (!role.equals("Admin")) { // Only Admin can delete clients
            return "Accès refusé : seul un Admin peut supprimer un client.";
        }
        if (id_client <= 0) {
            return "Erreur : l'ID du client doit être un entier positif.";
        }
        try {
            clientDao.delete(id_client);
            return "Client avec l'ID " + id_client + " supprimé avec succès.";
        } catch (RuntimeException e) {
            return "Erreur lors de la suppression du client : " + e.getMessage();
        }
    }

    public List<Client> getTousLesClients(String role) {
        if (!role.equals("Admin")) {
            throw new SecurityException("Accès refusé : seul un Admin peut accéder à cette méthode.");
        }
        return clientDao.afficherTous();
    }

    private boolean validerTelephone(String telephone) {
        if (telephone == null || telephone.trim().isEmpty()) {
            return true; // Telephone is optional, so empty is valid
        }
        return Pattern.matches("\\d{8,}", telephone); // At least 8 digits
    }

    public String ajouterClient(String role, String nom, String prenom, String adresse, String telephone) {
        Client c = new Client();
        c.setNom_client(nom);
        c.setPrenom_client(prenom);
        c.setAdresse_client(adresse);
        c.setTelephone_client(telephone);
        return ajouterClient(role, c); // on réutilise la vraie méthode
    }

    public String mettreAJourClient(String role, int id, String nom, String prenom, String adresse, String telephone) {
        Client c = new Client();
        c.setId_client(id);
        c.setNom_client(nom);
        c.setPrenom_client(prenom);
        c.setAdresse_client(adresse);
        c.setTelephone_client(telephone);
        return mettreAJourClient(role, c); // on réutilise la vraie méthode
    }

}