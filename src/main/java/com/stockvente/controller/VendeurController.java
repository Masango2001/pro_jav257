package com.stockvente.controller;

import com.stockvente.models.Client;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VendeurController {

    private final VenteController venteController;
    private final ClientController clientController;
    private final StockController stockController;
    private final ConcernerController concernerController;
    private final SimpleDateFormat dateFormat;

    public VendeurController() {
        this.venteController = new VenteController();
        this.clientController = new ClientController();
        this.stockController = new StockController();
        this.concernerController = new ConcernerController();
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    public VendeurController(ClientController clientController, VenteController venteController, StockController stockController, ConcernerController concernerController) {
        this.clientController = clientController;
        this.venteController = venteController;
        this.stockController = stockController;
        this.concernerController = concernerController;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    public String afficherApercuVendeur(String role) {
        if (!role.equals("Vendeur") && !role.equals("Admin")) {
            return "Accès refusé : seul un Vendeur ou un Admin peut accéder à l'aperçu du Vendeur.";
        }

        StringBuilder result = new StringBuilder("=== Aperçu pour le Vendeur ===\n\n");

        result.append("### Ventes ###\n");
        result.append(venteController.afficherToutesLesVentes("Vendeur")).append("\n\n");

        result.append("### Clients ###\n");
        result.append(clientController.afficherTousLesClients("Vendeur")).append("\n\n");

        result.append("### Stocks ###\n");
        result.append(stockController.afficherTousLesStocks("Vendeur")).append("\n");

        return result.toString();
    }

    public String ajouterVente(String role, Date date_vente, int id_utilisateur, int id_client) {
        if (!role.equals("Vendeur") && !role.equals("Admin")) {
            return "Accès refusé : seul un Vendeur ou un Admin peut ajouter une vente via VendeurController.";
        }
        if (date_vente == null) {
            return "Erreur : la date de vente ne peut pas être nulle.";
        }
        Date currentDate = new Date();
        if (date_vente.after(currentDate)) {
            return "Erreur : la date de vente ne peut pas être dans le futur.";
        }
        return venteController.ajouterVente(role, date_vente, id_utilisateur, id_client);
    }

    public String ajouterClient(String role, String nom_client, String prenom_client, String adresse_client, String telephone_client) {
        if (!role.equals("Vendeur") && !role.equals("Admin")) {
            return "Accès refusé : seul un Vendeur ou un Admin peut ajouter un client via VendeurController.";
        }
        try {
            Client client = new Client();
            client.setNom_client(nom_client);
            client.setPrenom_client(prenom_client);
            client.setAdresse_client(adresse_client);
            client.setTelephone_client(telephone_client);

            return clientController.ajouterClient(role, client);
        } catch (RuntimeException e) {
            return "Erreur lors de l'ajout du client : " + e.getMessage();
        }
    }

    public String afficherConcerner(String role) {
        if (!role.equals("Vendeur") && !role.equals("Admin")) {
            return "Accès refusé : seul un Vendeur ou un Admin peut afficher les lignes de vente via VendeurController.";
        }
        return concernerController.afficherConcerner(role);
    }

    public String ajouterConcerner(String role, int id_vente, int id_produit, String quantite_vendue, double prix_unitaire_vendue) {
        if (!role.equals("Vendeur") && !role.equals("Admin")) {
            return "Accès refusé : seul un Vendeur ou un Admin peut ajouter une ligne de vente via VendeurController.";
        }
        try {
            return concernerController.ajouterLigneDeVente(role, id_vente, id_produit, quantite_vendue, prix_unitaire_vendue);
        } catch (RuntimeException e) {
            return "Erreur lors de l'ajout de la ligne de vente : " + e.getMessage();
        }
    }

    public ConcernerController getConcernerController() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public VenteController getVenteController() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
