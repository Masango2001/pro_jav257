package com.stockvente.views;

import com.stockvente.controller.AdminController;
import com.stockvente.controller.ConcernerController;
import com.stockvente.models.Concerner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ConcernerView extends JFrame {
    private final ConcernerController concernerController;
    private JTextArea outputArea;
    private JTextField venteField, produitField, quantiteField, prixField;
    private final String role;

    // Constructeur principal avec rôle
    public ConcernerView(String role) {
        this.role = (role != null && !role.isEmpty()) ? role : "Vendeur";
        this.concernerController = new ConcernerController();
        initUI();
    }

    // Constructeur par défaut
    public ConcernerView() {
        this("Vendeur");
    }

    // ✅ Constructeur avec AdminController
    public ConcernerView(AdminController adminController) {
        this("Admin");
    }

    private void initUI() {
        setTitle("Gestion des Lignes de Vente");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Entrées
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Informations Ligne de Vente"));

        venteField = new JTextField();
        produitField = new JTextField();
        quantiteField = new JTextField();
        prixField = new JTextField();

        formPanel.add(new JLabel("ID Vente :"));
        formPanel.add(venteField);
        formPanel.add(new JLabel("ID Produit :"));
        formPanel.add(produitField);
        formPanel.add(new JLabel("Quantité vendue :"));
        formPanel.add(quantiteField);
        formPanel.add(new JLabel("Prix unitaire vendu :"));
        formPanel.add(prixField);

        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton ajouterBtn = new JButton("Ajouter");
        JButton afficherBtn = new JButton("Afficher");
        buttonPanel.add(ajouterBtn);
        buttonPanel.add(afficherBtn);
        ajouterBtn.addActionListener(this::ajouterLigneDeVente);
        afficherBtn.addActionListener(this::afficherToutesLesLignes);


        // Zone de sortie
        outputArea = new JTextArea(12, 60);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lignes de vente"));

        // Layout principal
        setLayout(new BorderLayout(10, 10));
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Actions
    }

    private void ajouterLigneDeVente(ActionEvent e) {
        try {
            int idVente = Integer.parseInt(venteField.getText());
            int idProduit = Integer.parseInt(produitField.getText());
            int quantite = Integer.parseInt(quantiteField.getText());
            double prix = Double.parseDouble(prixField.getText());

            Concerner ligne = new Concerner(idVente, idProduit, quantite, prix);
            String result = concernerController.ajouterLigneDeVente(role, ligne);
            outputArea.setText(result);
            afficherToutesLesLignes(null);
        } catch (NumberFormatException ex) {
            outputArea.setText("Erreur : veuillez saisir des valeurs valides pour ID, quantité et prix.");
        }
    }

    private void afficherToutesLesLignes(ActionEvent e) {
        try {
            List<Concerner> lignes = concernerController.getToutesLesLignesDeVente(role);
            if (lignes.isEmpty()) {
                outputArea.setText("Aucune ligne de vente trouvée.");
                return;
            }

            StringBuilder sb = new StringBuilder("=== Lignes de vente ===\n");
            for (Concerner c : lignes) {
                sb.append("Vente ID: ").append(c.getId_vente())
                  .append(", Produit ID: ").append(c.getId_produit())
                  .append(", Quantité: ").append(c.getQuantite_vendue())
                  .append(", Prix Unitaire: ").append(c.getPrix_unitaire_vendue())
                  .append(", Total: ").append(c.getMontantTotalVente())
                  .append("\n");
            }
            outputArea.setText(sb.toString());
        } catch (Exception ex) {
            outputArea.setText("Erreur lors de l'affichage : " + ex.getMessage());
        }
    }

   
}
