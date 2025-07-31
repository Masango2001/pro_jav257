package com.stockvente.views;

import com.stockvente.controller.AdminController;
import com.stockvente.controller.VenteController;
import com.stockvente.models.Vente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.text.*;

public class VenteView extends JFrame {
    private final VenteController venteController;
    private JTextArea outputArea;
    private JTextField dateField, utilisateurField, clientField;
    private final String role;

    // Constructeur avec rôle
    public VenteView(String role) {
        this.venteController = new VenteController();
        this.role = (role != null && !role.isEmpty()) ? role : "Vendeur";
        initUI();
    }

    // Constructeur par défaut
    public VenteView() {
        this("Vendeur");
    }

    // ✅ Constructeur utilisé depuis AdminView
    public VenteView(AdminController adminController) {
        this.venteController = new VenteController();
        this.role = "Admin";
        initUI();
    }

    private void initUI() {
        setTitle("Gestion des Ventes - Rôle : " + role);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel welcomeLabel = new JLabel("Bienvenue dans la vue des ventes (" + role + ")");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(welcomeLabel, BorderLayout.NORTH);

        // === Formulaire de saisie
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Ajouter une Vente"));

        dateField = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        utilisateurField = new JTextField();
        clientField = new JTextField();

        formPanel.add(new JLabel("Date de vente (dd/MM/yyyy) :"));
        formPanel.add(dateField);
        formPanel.add(new JLabel("ID Utilisateur :"));
        formPanel.add(utilisateurField);
        formPanel.add(new JLabel("ID Client :"));
        formPanel.add(clientField);

        centerPanel.add(formPanel, BorderLayout.CENTER);

        // === Boutons d'action
        JPanel buttonPanel = new JPanel();

        JButton ajouterBtn = new JButton("Ajouter");
        JButton afficherBtn = new JButton("Afficher");
        buttonPanel.add(ajouterBtn);
        buttonPanel.add(afficherBtn);

        if ("Admin".equalsIgnoreCase(role)) {
            JButton modifierBtn = new JButton("Modifier");
            JButton supprimerBtn = new JButton("Supprimer");
            buttonPanel.add(modifierBtn);
            buttonPanel.add(supprimerBtn);

            modifierBtn.addActionListener(this::modifierVente);
        
        }

        centerPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

        // === Zone d'affichage des ventes
        outputArea = new JTextArea(12, 40);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Liste des ventes"));
        add(scrollPane, BorderLayout.EAST);

        // === Actions des boutons
        ajouterBtn.addActionListener(this::ajouterVente);
        afficherBtn.addActionListener(this::afficherVentes);

        setVisible(true);
    }


    private void ajouterVente(ActionEvent e) {
        try {
            // Vérifier que les champs ne sont pas vides
            String dateText = dateField.getText().trim();
            String utilisateurText = utilisateurField.getText().trim();
            String clientText = clientField.getText().trim();

            if (dateText.isEmpty()) {
                outputArea.setText("Erreur : le champ Date de vente est vide.");
                return;
            }
            if (utilisateurText.isEmpty()) {
                outputArea.setText("Erreur : le champ ID Utilisateur est vide.");
                return;
            }
            if (clientText.isEmpty()) {
                outputArea.setText("Erreur : le champ ID Client est vide.");
                return;
            }

            // Valider que les ID sont des entiers
            if (!utilisateurText.matches("\\d+")) {
                outputArea.setText("Erreur : l'ID Utilisateur doit être un nombre entier.");
                return;
            }
            if (!clientText.matches("\\d+")) {
                outputArea.setText("Erreur : l'ID Client doit être un nombre entier.");
                return;
            }

            // Convertir les valeurs
            Date dateVente = new SimpleDateFormat("dd/MM/yyyy").parse(dateText);
            int idUtilisateur = Integer.parseInt(utilisateurText);
            int idClient = Integer.parseInt(clientText);

            // Créer et ajouter la vente
            Vente vente = new Vente(0, dateVente, idUtilisateur, idClient);
            String result = venteController.ajouterVente(role, vente);
            outputArea.setText(result);
            afficherVentes(null);
        } catch (ParseException pe) {
            outputArea.setText("Erreur : la date doit être au format dd/MM/yyyy.");
        } catch (NumberFormatException nfe) {
            outputArea.setText("Erreur : les ID doivent être des entiers valides.");
        } catch (Exception ex) {
            outputArea.setText("Erreur : " + ex.getMessage());
        }
    }

    private void afficherVentes(ActionEvent e) {
        try {
            List<Vente> ventes = venteController.getToutesLesVentes(role);
            if (ventes.isEmpty()) {
                outputArea.setText("Aucune vente trouvée.");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            StringBuilder sb = new StringBuilder("=== Liste des ventes ===\n");
            for (Vente vente : ventes) {
                sb.append("ID: ").append(vente.getId_vente())
                        .append(", Date: ").append(sdf.format(vente.getDate_vente()))
                        .append(", Utilisateur ID: ").append(vente.getId_utilisateur())
                        .append(", Client ID: ").append(vente.getId_client())
                        .append("\n");
            }
            outputArea.setText(sb.toString());
        } catch (Exception ex) {
            outputArea.setText("Erreur lors de l'affichage : " + ex.getMessage());
        }
    }

    private void modifierVente(ActionEvent e) {
        outputArea.setText("Fonctionnalité de modification non implémentée.");
    }



   
}