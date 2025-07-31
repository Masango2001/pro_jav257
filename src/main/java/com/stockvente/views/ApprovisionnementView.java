package com.stockvente.views;

import com.stockvente.controller.AdminController;
import com.stockvente.controller.ApprovisionnementController;
import com.stockvente.models.Approvisionnement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;

public class ApprovisionnementView extends JFrame {
    private final ApprovisionnementController controller;
    private final JTable table;
    private final DefaultTableModel tableModel;

    public ApprovisionnementView() {
        controller = new ApprovisionnementController();
        setTitle("Gestion des Approvisionnements");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        tableModel = new DefaultTableModel(new String[]{
                "ID", "Produit", "Fournisseur", "QuantitéApprovisionnement", "Prix", "Total", "Date"
        }, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel panelButtons = new JPanel(new FlowLayout());
        JButton btnAjouter = new JButton("Ajouter");
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");
        JButton btnActualiser = new JButton("Actualiser");

        panelButtons.add(btnAjouter);
        panelButtons.add(btnModifier);
        panelButtons.add(btnSupprimer);
        panelButtons.add(btnActualiser);

        add(scrollPane, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);

        // Boutons
        btnAjouter.addActionListener(e -> ajouterApprovisionnement());
        btnModifier.addActionListener(e -> modifierApprovisionnement());
        btnSupprimer.addActionListener(e -> supprimerApprovisionnement());
        btnActualiser.addActionListener(e -> chargerApprovisionnements());

        chargerApprovisionnements();
    }

// Ajout demandé sans modifier le reste
    public ApprovisionnementView(AdminController adminController) {
        this();
    }


    private void ajouterApprovisionnement() {
        try {
            int idProduit = Integer.parseInt(JOptionPane.showInputDialog(this, "ID Produit :"));
            int idFournisseur = Integer.parseInt(JOptionPane.showInputDialog(this, "ID Fournisseur :"));
            int quantiteApprovisionnement = Integer.parseInt(JOptionPane.showInputDialog(this, "Quantité Approvisionnement :"));
            double prix = Double.parseDouble(JOptionPane.showInputDialog(this, "Prix unitaire :"));

            Approvisionnement a = new Approvisionnement(0, idProduit, idFournisseur, quantiteApprovisionnement, prix, new Date());
            String msg = controller.ajouterApprovisionnement("Admin", a);
            JOptionPane.showMessageDialog(this, msg);
            chargerApprovisionnements();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Entrée invalide ou annulée.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierApprovisionnement() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un approvisionnement.");
            return;
        }

        try {
            int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            int idProduit = Integer.parseInt(JOptionPane.showInputDialog(this, "ID Produit :", tableModel.getValueAt(row, 1)));
            int idFournisseur = Integer.parseInt(JOptionPane.showInputDialog(this, "ID Fournisseur :", tableModel.getValueAt(row, 2)));
            int quantiteApprovisionnement = Integer.parseInt(JOptionPane.showInputDialog(this, "Quantité :", tableModel.getValueAt(row, 3)));
            double prix = Double.parseDouble(JOptionPane.showInputDialog(this, "Prix unitaire :", tableModel.getValueAt(row, 4)));

            Approvisionnement a = new Approvisionnement(id, idProduit, idFournisseur, quantiteApprovisionnement, prix, new Date());
            String msg = controller.mettreAJourApprovisionnement("Admin", a);
            JOptionPane.showMessageDialog(this, msg);
            chargerApprovisionnements();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerApprovisionnement() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un approvisionnement.");
            return;
        }

        int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(this, "Confirmer la suppression ?", "Supprimer", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String msg = controller.supprimerApprovisionnement("Admin", id);
            JOptionPane.showMessageDialog(this, msg);
            chargerApprovisionnements();
        }
    }

    private void chargerApprovisionnements() {
        tableModel.setRowCount(0);
        String data = controller.afficherTousLesApprovisionnements("Admin");

        if (data.startsWith("Liste")) {
            String[] lignes = data.split("\n");
            for (int i = 1; i < lignes.length; i++) {
                String[] tokens = lignes[i].split(", ");
                Object[] row = new Object[tokens.length];
                for (int j = 0; j < tokens.length; j++) {
                    row[j] = tokens[j].split(":")[1].trim();
                }
                tableModel.addRow(row);
            }
        } else {
            JOptionPane.showMessageDialog(this, data);
        }
    }

   
}
