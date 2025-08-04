package com.stockvente.views;

import com.stockvente.controller.ProduitController;
import com.stockvente.models.Produit;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class ProduitView extends JFrame {
    private ProduitController produitController;
    private JTextField idField, nomField, idCatField, nomCatField, quantStockField, searchField;
    private JButton addButton, updateButton, deleteButton, viewButton;
    private JTable produitTable;
    private DefaultTableModel tableModel;

    public ProduitView(ProduitController produitController) {
        this.produitController = produitController;

        setTitle("Gestion des Produits");
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
                "Ajouter / Modifier Produit",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                titleFont,
                blue
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        idField = createLabeledField(panelForm, "ID Produit:", 0, gbc, labelFont);
        nomField = createLabeledField(panelForm, "Nom Produit:", 1, gbc, labelFont);
        idCatField = createLabeledField(panelForm, "ID Catégorie:", 2, gbc, labelFont);
        nomCatField = createLabeledField(panelForm, "Nom Catégorie:", 3, gbc, labelFont);
        quantStockField = createLabeledField(panelForm, "Quantité Stock:", 4, gbc, labelFont);

        addButton = createStyledButton("Ajouter", btnFont, blue);
        updateButton = createStyledButton("Mettre à jour", btnFont, blue);
        deleteButton = createStyledButton("Supprimer", btnFont, blue);
        viewButton = createStyledButton("Afficher Tous", btnFont, blue);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        panelForm.add(buttonPanel, gbc);

        // ---- Tableau (panel droit) ----
        tableModel = new DefaultTableModel(new String[]{"ID", "Nom", "ID Catégorie", "Nom Catégorie", "Quantité"}, 0);
        produitTable = new JTable(tableModel);
        produitTable.setFont(new Font("Bell Mt", Font.PLAIN, 16));
        produitTable.setRowHeight(28);
        produitTable.getTableHeader().setFont(new Font("Bell Mt", Font.BOLD, 16));
        JScrollPane scrollPane = new JScrollPane(produitTable);

        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(blue, 2),
                "Liste des Produits",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                titleFont,
                blue
        ));
        panelTable.setBackground(Color.WHITE);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(new JLabel("Recherche:"));
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(250, 28));
        searchPanel.add(searchField);

        panelTable.add(searchPanel, BorderLayout.NORTH);
        panelTable.add(scrollPane, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelForm, panelTable);
        splitPane.setResizeWeight(0.35);
        splitPane.setDividerSize(5);
        add(splitPane, BorderLayout.CENTER);

        addButton.addActionListener(e -> {
            try {
                Produit produit = new Produit(0,
                        nomField.getText(),
                        Integer.parseInt(idCatField.getText()),
                        nomCatField.getText(),
                        Integer.parseInt(quantStockField.getText()));
                String result = produitController.ajouterProduit("Magasinier", produit);
                JOptionPane.showMessageDialog(this, result);
                resetFields();
                refreshTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur: " + ex.getMessage());
            }
        });

        updateButton.addActionListener(e -> {
            try {
                Produit produit = new Produit(
                        Integer.parseInt(idField.getText()),
                        nomField.getText(),
                        Integer.parseInt(idCatField.getText()),
                        nomCatField.getText(),
                        Integer.parseInt(quantStockField.getText()));
                String result = produitController.mettreAJourProduit("Magasinier", produit);
                JOptionPane.showMessageDialog(this, result);
                resetFields();
                refreshTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur: " + ex.getMessage());
            }
        });

        deleteButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String result = produitController.supprimerProduit("Magasinier", id);
                JOptionPane.showMessageDialog(this, result);
                resetFields();
                refreshTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur: " + ex.getMessage());
            }
        });

        viewButton.addActionListener(e -> refreshTable());

        produitTable.getSelectionModel().addListSelectionListener(e -> {
            int row = produitTable.getSelectedRow();
            if (row >= 0) {
                idField.setText(tableModel.getValueAt(row, 0).toString());
                nomField.setText(tableModel.getValueAt(row, 1).toString());
                idCatField.setText(tableModel.getValueAt(row, 2).toString());
                nomCatField.setText(tableModel.getValueAt(row, 3).toString());
                quantStockField.setText(tableModel.getValueAt(row, 4).toString());
            }
        });

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filterTable(); }
            public void removeUpdate(DocumentEvent e) { filterTable(); }
            public void changedUpdate(DocumentEvent e) { filterTable(); }
        });

        refreshTable();
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

    private void resetFields() {
        idField.setText("");
        nomField.setText("");
        idCatField.setText("");
        nomCatField.setText("");
        quantStockField.setText("");
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Produit> produits = produitController.getProduits();
        for (Produit p : produits) {
            tableModel.addRow(new Object[]{
                    p.getId_produit(),
                    p.getNom_produit(),
                    p.getId_categorie(),
                    p.getNom_categorie(),
                    p.getQuantite_stock()
            });
        }
    }

    private void filterTable() {
        String searchText = searchField.getText().toLowerCase();
        tableModel.setRowCount(0);
        List<Produit> produits = produitController.getProduits();
        for (Produit p : produits) {
            boolean match = String.valueOf(p.getId_produit()).contains(searchText)
                    || p.getNom_produit().toLowerCase().contains(searchText)
                    || String.valueOf(p.getId_categorie()).contains(searchText)
                    || p.getNom_categorie().toLowerCase().contains(searchText)
                    || String.valueOf(p.getQuantite_stock()).contains(searchText);
            if (match) {
                tableModel.addRow(new Object[]{
                        p.getId_produit(),
                        p.getNom_produit(),
                        p.getId_categorie(),
                        p.getNom_categorie(),
                        p.getQuantite_stock()
                });
            }
        }
    }
}
