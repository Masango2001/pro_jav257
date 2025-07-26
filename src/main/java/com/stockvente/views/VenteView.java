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

public class VenteView extends JFrame {
    private final VenteController venteController;
    private final JTextArea outputArea;
    private final JTextField dateField, utilisateurField, clientField;
    private final String role = "Vendeur"; // ou "Admin" selon le contexte

    public VenteView() {
        venteController = new VenteController();

        setTitle("Gestion des Ventes");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Zone de formulaire
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

        // Boutons
        JPanel buttonPanel = new JPanel();
        JButton ajouterBtn = new JButton("Ajouter");
        JButton afficherBtn = new JButton("Afficher");
        buttonPanel.add(ajouterBtn);
        buttonPanel.add(afficherBtn);

        // Zone d’affichage
        outputArea = new JTextArea(12, 60);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Liste des ventes"));

        // Layout principal
        setLayout(new BorderLayout(10, 10));
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Événements
        ajouterBtn.addActionListener(this::ajouterVente);
        afficherBtn.addActionListener(this::afficherVentes);
    }

    VenteView(String vendeur) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    VenteView(AdminController adminController) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void ajouterVente(ActionEvent e) {
        try {
            Date dateVente = new SimpleDateFormat("dd/MM/yyyy").parse(dateField.getText());
            int idUtilisateur = Integer.parseInt(utilisateurField.getText().trim());
            int idClient = Integer.parseInt(clientField.getText().trim());

            Vente vente = new Vente(0, dateVente, idUtilisateur, idClient);
            String result = venteController.ajouterVente(role, vente);
            outputArea.setText(result);
            afficherVentes(null);
        } catch (ParseException pe) {
            outputArea.setText("Erreur : la date doit être au format dd/MM/yyyy.");
        } catch (NumberFormatException nfe) {
            outputArea.setText("Erreur : les ID doivent être des entiers.");
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VenteView().setVisible(true));
    }
}
