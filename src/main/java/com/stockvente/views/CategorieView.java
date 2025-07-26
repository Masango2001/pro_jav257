package com.stockvente.views;

import com.stockvente.controller.AdminController;
import com.stockvente.controller.CategorieController;
import com.stockvente.models.Categorie;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CategorieView extends JFrame {

    private final CategorieController controller;
    private final DefaultTableModel tableModel;
    private final JTable table;

    public CategorieView() {
        controller = new CategorieController();
        setTitle("Gestion des Catégories");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Nom"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Buttons
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
        btnAjouter.addActionListener(e -> ajouterCategorie());
        btnModifier.addActionListener(e -> modifierCategorie());
        btnSupprimer.addActionListener(e -> supprimerCategorie());
        btnActualiser.addActionListener(e -> chargerCategories());

        // Charger au lancement
        chargerCategories();
    }

    CategorieView(AdminController adminController) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void ajouterCategorie() {
        String nom = JOptionPane.showInputDialog(this, "Nom de la catégorie :");
        if (nom != null && !nom.trim().isEmpty()) {
            // Utilisation du constructeur complet avec ID = 0 (non utilisé pour insertions auto-incrémentées)
            Categorie c = new Categorie(0, nom.trim());
            String msg = controller.ajouterCategorie("Admin", c);
            JOptionPane.showMessageDialog(this, msg);
            chargerCategories();
        } else {
            JOptionPane.showMessageDialog(this, "Le nom ne peut pas être vide.");
        }
    }

    private void modifierCategorie() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une catégorie à modifier.");
            return;
        }

        int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
        String ancienNom = tableModel.getValueAt(row, 1).toString();

        String nouveauNom = JOptionPane.showInputDialog(this, "Nouveau nom de la catégorie :", ancienNom);
        if (nouveauNom != null && !nouveauNom.trim().isEmpty()) {
            Categorie c = new Categorie(id, nouveauNom.trim());
            String msg = controller.mettreAJourCategorie("Admin", c);
            JOptionPane.showMessageDialog(this, msg);
            chargerCategories();
        } else {
            JOptionPane.showMessageDialog(this, "Le nom ne peut pas être vide.");
        }
    }

    private void supprimerCategorie() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une catégorie à supprimer.");
            return;
        }

        int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(this, "Confirmer la suppression ?", "Supprimer", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String msg = controller.supprimerCategorie("Admin", id);
            JOptionPane.showMessageDialog(this, msg);
            chargerCategories();
        }
    }

    private void chargerCategories() {
        tableModel.setRowCount(0); // Clear
        String resultat = controller.afficherToutesLesCategories("Admin");
        if (resultat.startsWith("Liste")) {
            String[] lignes = resultat.split("\n");
            for (int i = 1; i < lignes.length; i++) {
                String[] tokens = lignes[i].split(", ");
                int id = Integer.parseInt(tokens[0].split(":")[1].trim());
                String nom = tokens[1].split(":")[1].trim();
                tableModel.addRow(new Object[]{id, nom});
            }
        } else {
            JOptionPane.showMessageDialog(this, resultat, "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CategorieView().setVisible(true));
    }
}
