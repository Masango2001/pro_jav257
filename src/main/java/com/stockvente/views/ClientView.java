package com.stockvente.views;

import com.stockvente.controller.AdminController;
import com.stockvente.controller.ClientController;
import com.stockvente.models.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ClientView extends JFrame {

    private ClientController clientController;
    private JTextArea outputArea;
    private JTextField idField, nomField, prenomField, adresseField, telephoneField;
    private String role;

//    public ClientView() {
//        this.clientController = new ClientController();
//        this.role = "Default";
//        initialiserUI();
//    }

    public ClientView(String vendeur) {
        this.clientController = new ClientController();
        this.role = vendeur; // ex : "Vendeur"
        initialiserUI();
    }

    public ClientView(AdminController adminController) {
        this.clientController = new ClientController();
        this.role = "Admin";  // ou récupérer via adminController
        initialiserUI();
    }

    private void initialiserUI() {
        setTitle("Gestion des Clients (" + role + ")");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Informations Client"));

        idField = new JTextField();
        nomField = new JTextField();
        prenomField = new JTextField();
        adresseField = new JTextField();
        telephoneField = new JTextField();

        inputPanel.add(new JLabel("ID (pour modifier/supprimer) :"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Nom :"));
        inputPanel.add(nomField);
        inputPanel.add(new JLabel("Prénom :"));
        inputPanel.add(prenomField);
        inputPanel.add(new JLabel("Adresse :"));
        inputPanel.add(adresseField);
        inputPanel.add(new JLabel("Téléphone :"));
        inputPanel.add(telephoneField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnAjouter = new JButton("Ajouter");
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");
        JButton btnAfficher = new JButton("Afficher");

        buttonPanel.add(btnAjouter);
        buttonPanel.add(btnModifier);
        buttonPanel.add(btnSupprimer);
        buttonPanel.add(btnAfficher);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBorder(BorderFactory.createTitledBorder("Résultats"));
        JScrollPane scrollPane = new JScrollPane(outputArea);

        setLayout(new BorderLayout(10, 10));
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        btnAjouter.addActionListener(e -> ajouterClient());
        btnModifier.addActionListener(e -> modifierClient());
        btnSupprimer.addActionListener(e -> supprimerClient());
        btnAfficher.addActionListener(e -> afficherClients());
    }




  private void ajouterClient() {
        Client client = new Client();
        client.setNom_client(nomField.getText());
        client.setPrenom_client(prenomField.getText());
        client.setAdresse_client(adresseField.getText());
        client.setTelephone_client(telephoneField.getText());

        System.out.println("Client à ajouter : " + client.getNom_client() + ", " 
                + client.getPrenom_client() + ", " + client.getAdresse_client() 
                + ", " + client.getTelephone_client());

        String resultat = clientController.ajouterClient(role, client);

        // -> Eviter que le message soit écrasé par afficherClients()
        if (resultat.startsWith("Client ajouté")) { // ou resultat.toLowerCase().contains("succès")
            JOptionPane.showMessageDialog(this, resultat, "Succès", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            // rafraîchir la liste seulement après avoir montré le message
            outputArea.setText(clientController.afficherTousLesClients(role));
        } else {
            JOptionPane.showMessageDialog(this, resultat, "Erreur", JOptionPane.ERROR_MESSAGE);
            outputArea.setText(resultat);
        }
    }

    private void clearFields() {
        idField.setText("");
        nomField.setText("");
        prenomField.setText("");
        adresseField.setText("");
        telephoneField.setText("");
    }


    private void modifierClient() {
        try {
            int id = Integer.parseInt(idField.getText());
            Client client = new Client();
            client.setId_client(id);
            client.setNom_client(nomField.getText());
            client.setPrenom_client(prenomField.getText());
            client.setAdresse_client(adresseField.getText());
            client.setTelephone_client(telephoneField.getText());

            String resultat = clientController.mettreAJourClient(role, client);
            outputArea.setText(resultat);
            afficherClients();
        } catch (NumberFormatException e) {
            outputArea.setText("Erreur : ID invalide pour la modification.");
        }
    }

    private void supprimerClient() {
        try {
            int id = Integer.parseInt(idField.getText());
            String resultat = clientController.supprimerClient(role, id);
            outputArea.setText(resultat);
            afficherClients();
        } catch (NumberFormatException e) {
            outputArea.setText("Erreur : ID invalide pour la suppression.");
        }
    }

    private void afficherClients() {
        String resultat = clientController.afficherTousLesClients(role);
        outputArea.setText(resultat);
    }

     public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClientView("Vendeur").setVisible(true));
    }


}
