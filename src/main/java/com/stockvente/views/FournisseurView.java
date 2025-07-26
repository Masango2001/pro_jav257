package com.stockvente.views;

import com.stockvente.controller.AdminController;
import com.stockvente.controller.FournisseurController;
import com.stockvente.models.Fournisseur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FournisseurView extends JFrame {

    private final FournisseurController controller;
    private final JTable table;
    private final DefaultTableModel tableModel;

    public FournisseurView() {
        controller = new FournisseurController();

        setTitle("Gestion des Fournisseurs");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        tableModel = new DefaultTableModel(new String[]{
                "ID", "Nom", "Adresse", "Email", "Téléphone"
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

        // Actions
        btnAjouter.addActionListener(e -> ajouterFournisseur());
        btnModifier.addActionListener(e -> modifierFournisseur());
        btnSupprimer.addActionListener(e -> supprimerFournisseur());
        btnActualiser.addActionListener(e -> chargerFournisseurs());

        chargerFournisseurs();
    }

    FournisseurView(AdminController adminController) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void ajouterFournisseur() {
        try {
            String nom = JOptionPane.showInputDialog(this, "Nom complet :");
            String adresse = JOptionPane.showInputDialog(this, "Adresse :");
            String email = JOptionPane.showInputDialog(this, "Email :");
            String telephone = JOptionPane.showInputDialog(this, "Téléphone :");

            if (nom == null || nom.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Le nom ne peut pas être vide.");
                return;
            }

            Fournisseur f = new Fournisseur(0, nom.trim(), adresse.trim(), email.trim(), telephone.trim());
            String msg = controller.ajouterFournisseur("Admin", f);
            JOptionPane.showMessageDialog(this, msg);
            chargerFournisseurs();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur de saisie : " + ex.getMessage());
        }
    }

    private void modifierFournisseur() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un fournisseur à modifier.");
            return;
        }

        try {
            int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            String nom = JOptionPane.showInputDialog(this, "Nom complet :", tableModel.getValueAt(row, 1));
            String adresse = JOptionPane.showInputDialog(this, "Adresse :", tableModel.getValueAt(row, 2));
            String email = JOptionPane.showInputDialog(this, "Email :", tableModel.getValueAt(row, 3));
            String telephone = JOptionPane.showInputDialog(this, "Téléphone :", tableModel.getValueAt(row, 4));

            Fournisseur f = new Fournisseur(id, nom.trim(), adresse.trim(), email.trim(), telephone.trim());
            String msg = controller.mettreAJourFournisseur("Admin", f);
            JOptionPane.showMessageDialog(this, msg);
            chargerFournisseurs();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification : " + ex.getMessage());
        }
    }

    private void supprimerFournisseur() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un fournisseur à supprimer.");
            return;
        }

        int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(this, "Confirmer la suppression ?", "Supprimer", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String msg = controller.supprimerFournisseur("Admin", id);
            JOptionPane.showMessageDialog(this, msg);
            chargerFournisseurs();
        }
    }

    private void chargerFournisseurs() {
        tableModel.setRowCount(0); // Efface les lignes existantes
        String data = controller.afficherTousLesFournisseurs("Admin");
        if (data.startsWith("Liste")) {
            String[] lignes = data.split("\n");
            for (int i = 1; i < lignes.length; i++) {
                String[] tokens = lignes[i].split(", ");
                Object[] row = new Object[5];
                row[0] = tokens[0].split(":")[1].trim();
                row[1] = tokens[1].split(":")[1].trim();
                row[2] = tokens[2].split(":")[1].trim();
                row[3] = tokens[3].split(":")[1].trim();
                row[4] = tokens[4].split(":")[1].trim();
                tableModel.addRow(row);
            }
        } else {
            JOptionPane.showMessageDialog(this, data);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FournisseurView().setVisible(true));
    }
}
