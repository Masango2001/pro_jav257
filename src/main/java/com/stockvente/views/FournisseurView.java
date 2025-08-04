package com.stockvente.views;

import com.stockvente.controller.AdminController;
import com.stockvente.controller.FournisseurController;
import com.stockvente.models.Fournisseur;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FournisseurView extends JFrame {

    private AdminController adminController;
    private FournisseurController controller;
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField nomField, adresseField, emailField, telephoneField;
    private JButton btnAjouter, btnModifier, btnSupprimer, btnActualiser;

    public FournisseurView(AdminController adminController) {
        this.adminController = adminController;
        initUI();
    }

    public FournisseurView() {
        initUI();
    }

    private void initUI() {
        controller = new FournisseurController();

        setTitle("Gestion des Fournisseurs");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(15, 15));

        Color blue = new Color(33, 150, 243);
        Font labelFont = new Font("Bell Mt", Font.PLAIN, 18);
        Font titleFont = new Font("Bell Mt", Font.BOLD, 22);
        Font btnFont = new Font("Bell Mt", Font.BOLD, 16);

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(Color.WHITE);
        panelForm.setPreferredSize(new Dimension(350, 600));
        panelForm.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(blue, 2),
                "Ajouter / Modifier Fournisseur",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                titleFont,
                blue
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nomField = createLabeledField(panelForm, "Nom:", 0, gbc, labelFont);
        adresseField = createLabeledField(panelForm, "Adresse:", 1, gbc, labelFont);
        emailField = createLabeledField(panelForm, "Email:", 2, gbc, labelFont);
        telephoneField = createLabeledField(panelForm, "Téléphone:", 3, gbc, labelFont);

        btnAjouter = createStyledButton("Ajouter", btnFont, blue);
        btnModifier = createStyledButton("Modifier", btnFont, blue);
        btnSupprimer = createStyledButton("Supprimer", btnFont, blue);
        btnActualiser = createStyledButton("Actualiser", btnFont, blue);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnAjouter);
        buttonPanel.add(btnModifier);
        buttonPanel.add(btnSupprimer);
        buttonPanel.add(btnActualiser);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panelForm.add(buttonPanel, gbc);

        tableModel = new DefaultTableModel(new String[]{"ID", "Nom", "Adresse", "Email", "Téléphone"}, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Bell Mt", Font.PLAIN, 16));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Bell Mt", Font.BOLD, 16));

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(blue, 2),
                "Liste des Fournisseurs",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                titleFont,
                blue
        ));
        panelTable.setBackground(Color.WHITE);
        panelTable.add(scrollPane, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelForm, panelTable);
        splitPane.setResizeWeight(0.35);
        splitPane.setDividerSize(5);
        add(splitPane, BorderLayout.CENTER);

        btnAjouter.addActionListener(e -> ajouterFournisseur());
        btnModifier.addActionListener(e -> modifierFournisseur());
        btnSupprimer.addActionListener(e -> supprimerFournisseur());
        btnActualiser.addActionListener(e -> chargerFournisseurs());

        chargerFournisseurs();
        setVisible(true);
    }

    private JTextField createLabeledField(JPanel panel, String label, int y, GridBagConstraints gbc, Font font) {
        gbc.gridx = 0;
        gbc.gridy = y;
        JLabel lbl = new JLabel(label);
        lbl.setFont(font);
        panel.add(lbl, gbc);

        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(300, 40));
        field.setFont(font);
        gbc.gridx = 1;
        panel.add(field, gbc);
        return field;
    }

    private JButton createStyledButton(String text, Font font, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(font);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setPreferredSize(new Dimension(150, 40));
        return btn;
    }

    private void ajouterFournisseur() {
        try {
            String nom = nomField.getText();
            String adresse = adresseField.getText();
            String email = emailField.getText();
            String telephone = telephoneField.getText();

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
            String nom = tableModel.getValueAt(row, 1).toString();
            String adresse = tableModel.getValueAt(row, 2).toString();
            String email = tableModel.getValueAt(row, 3).toString();
            String telephone = tableModel.getValueAt(row, 4).toString();

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
        tableModel.setRowCount(0);
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
}
