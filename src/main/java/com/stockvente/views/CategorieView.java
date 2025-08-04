package com.stockvente.views;

import com.stockvente.controller.AdminController;
import com.stockvente.controller.CategorieController;
import com.stockvente.models.Categorie;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CategorieView extends JFrame {

    private CategorieController controller;
    private AdminController adminController;
    private DefaultTableModel tableModel;
    private JTable table;

    private JTextField tfNom;
    private JButton btnAjouter, btnModifier, btnSupprimer, btnActualiser;

    public CategorieView() {
        initUI();
    }

    public CategorieView(AdminController adminController) {
        this.adminController = adminController;
        initUI();
    }

    private void initUI() {
        controller = new CategorieController();

        setTitle("Gestion des Catégories");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(15, 15));

        Color blue = new Color(33, 150, 243);
        Font labelFont = new Font("Bell Mt", Font.PLAIN, 18);
        Font titleFont = new Font("Bell Mt", Font.BOLD, 22);
        Font btnFont = new Font("Bell Mt", Font.BOLD, 16);

        // ---- Formulaire (panel gauche) ----
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(Color.WHITE);
        panelForm.setPreferredSize(new Dimension(350, 600));
        panelForm.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(blue, 2),
                "Ajouter / Modifier Catégorie",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                titleFont,
                blue
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNom = new JLabel("Nom de la catégorie :");
        lblNom.setFont(labelFont);
        tfNom = new JTextField(20);
        tfNom.setFont(labelFont);
        tfNom.setPreferredSize(new Dimension(300, 40));

        btnAjouter = new JButton("Ajouter");
        btnModifier = new JButton("Modifier");
        btnSupprimer = new JButton("Supprimer");
        btnActualiser = new JButton("Actualiser");

        JButton[] buttons = {btnAjouter, btnModifier, btnSupprimer, btnActualiser};
        for (JButton btn : buttons) {
            btn.setFont(btnFont);
            btn.setBackground(blue);
            btn.setForeground(Color.WHITE);
            btn.setPreferredSize(new Dimension(150, 40));
        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelForm.add(lblNom, gbc);
        gbc.gridy++;
        panelForm.add(tfNom, gbc);
        gbc.gridy++;
        panelForm.add(btnAjouter, gbc);
        gbc.gridy++;
        panelForm.add(btnModifier, gbc);
        gbc.gridy++;
        panelForm.add(btnSupprimer, gbc);
        gbc.gridy++;
        panelForm.add(btnActualiser, gbc);

        // ---- Tableau (panel droit) ----
        tableModel = new DefaultTableModel(new String[]{"ID", "Nom"}, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Bell Mt", Font.PLAIN, 16));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Bell Mt", Font.BOLD, 16));
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(blue, 2),
                "Liste des Catégories",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                titleFont,
                blue
        ));
        panelTable.setBackground(Color.WHITE);
        panelTable.add(scrollPane, BorderLayout.CENTER);

        // ---- SplitPane pour responsive gauche/droite ----
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelForm, panelTable);
        splitPane.setResizeWeight(0.35);
        splitPane.setDividerSize(5);
        add(splitPane, BorderLayout.CENTER);

        // ---- Actions ----
        btnAjouter.addActionListener(e -> ajouterCategorie());
        btnModifier.addActionListener(e -> modifierCategorie());
        btnSupprimer.addActionListener(e -> supprimerCategorie());
        btnActualiser.addActionListener(e -> chargerCategories());

        // ---- Sélection ligne ----
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                tfNom.setText(tableModel.getValueAt(row, 1).toString());
            }
        });

        chargerCategories();
    }

    private void ajouterCategorie() {
        String nom = tfNom.getText().trim();
        if (!nom.isEmpty()) {
            Categorie c = new Categorie(0, nom);
            String msg = controller.ajouterCategorie("Admin", c);
            JOptionPane.showMessageDialog(this, msg);
            tfNom.setText("");
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
        String nouveauNom = tfNom.getText().trim();
        if (!nouveauNom.isEmpty()) {
            Categorie c = new Categorie(id, nouveauNom);
            String msg = controller.mettreAJourCategorie("Admin", c);
            JOptionPane.showMessageDialog(this, msg);
            tfNom.setText("");
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
            tfNom.setText("");
            chargerCategories();
        }
    }

    private void chargerCategories() {
        tableModel.setRowCount(0);
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
}
