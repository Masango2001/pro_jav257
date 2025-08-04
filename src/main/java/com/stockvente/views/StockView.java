package com.stockvente.views;

import com.stockvente.controller.StockController;
import com.stockvente.models.Stock;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StockView extends JFrame {
    private StockController stockController;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField idField, idProdField, quantiteField, dateField;

    public StockView(String role, StockController stockController) {
        this(stockController);
        setTitle("Gestion des Stocks - " + role);
    }

    public StockView(StockController stockController) {
        this.stockController = stockController;
        setTitle("Gestion des Stocks");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(15, 15));

        Color blue = new Color(33, 150, 243);
        Font labelFont = new Font("Bell Mt", Font.PLAIN, 18);
        Font titleFont = new Font("Bell Mt", Font.BOLD, 22);
        Font btnFont = new Font("Bell Mt", Font.BOLD, 16);

        // ---- Formulaire ----
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setPreferredSize(new Dimension(400, 600));
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(blue, 2),
                "Formulaire de Stock",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                titleFont,
                blue
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblId = new JLabel("ID Stock:");
        lblId.setFont(labelFont);
        idField = new JTextField();
        idField.setEditable(false);
        idField.setFont(labelFont);

        JLabel lblIdProd = new JLabel("ID Produit:");
        lblIdProd.setFont(labelFont);
        idProdField = new JTextField();
        idProdField.setFont(labelFont);

        JLabel lblQuantite = new JLabel("Quantité:");
        lblQuantite.setFont(labelFont);
        quantiteField = new JTextField();
        quantiteField.setFont(labelFont);

        JLabel lblDate = new JLabel("Date (dd/MM/yyyy):");
        lblDate.setFont(labelFont);
        dateField = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        dateField.setFont(labelFont);

        JButton btnAjouter = new JButton("Ajouter");
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");
        JButton btnActualiser = new JButton("Actualiser");

        JButton[] buttons = {btnAjouter, btnModifier, btnSupprimer, btnActualiser};
        for (JButton btn : buttons) {
            btn.setFont(btnFont);
            btn.setBackground(blue);
            btn.setForeground(Color.WHITE);
            btn.setPreferredSize(new Dimension(150, 40));
        }

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(lblId, gbc);
        gbc.gridx = 1; formPanel.add(idField, gbc);
        gbc.gridx = 0; gbc.gridy++; formPanel.add(lblIdProd, gbc);
        gbc.gridx = 1; formPanel.add(idProdField, gbc);
        gbc.gridx = 0; gbc.gridy++; formPanel.add(lblQuantite, gbc);
        gbc.gridx = 1; formPanel.add(quantiteField, gbc);
        gbc.gridx = 0; gbc.gridy++; formPanel.add(lblDate, gbc);
        gbc.gridx = 1; formPanel.add(dateField, gbc);
        gbc.gridx = 0; gbc.gridy++; formPanel.add(btnAjouter, gbc);
        gbc.gridx = 1; formPanel.add(btnModifier, gbc);
        gbc.gridx = 0; gbc.gridy++; formPanel.add(btnSupprimer, gbc);
        gbc.gridx = 1; formPanel.add(btnActualiser, gbc);

        // ---- Tableau ----
        tableModel = new DefaultTableModel(new String[]{"ID Stock", "ID Produit", "Quantité", "Date"}, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Bell Mt", Font.PLAIN, 16));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Bell Mt", Font.BOLD, 16));
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBackground(Color.WHITE);
        panelTable.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(blue, 2),
                "Liste des Stocks",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                titleFont,
                blue
        ));
        panelTable.add(scrollPane, BorderLayout.CENTER);

        // ---- Layout split ----
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, panelTable);
        splitPane.setResizeWeight(0.35);
        splitPane.setDividerSize(5);
        add(splitPane, BorderLayout.CENTER);

        // ---- Actions ----
        btnAjouter.addActionListener(e -> ajouterStock());
        btnModifier.addActionListener(e -> modifierStock());
        btnSupprimer.addActionListener(e -> supprimerStock());
        btnActualiser.addActionListener(e -> refreshTable());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                int row = table.getSelectedRow();
                idField.setText(tableModel.getValueAt(row, 0).toString());
                idProdField.setText(tableModel.getValueAt(row, 1).toString());
                quantiteField.setText(tableModel.getValueAt(row, 2).toString());
                dateField.setText(tableModel.getValueAt(row, 3).toString());
            }
        });

        refreshTable();
        setVisible(true);
    }

    private void ajouterStock() {
        try {
            int idProduit = Integer.parseInt(idProdField.getText().trim());
            int quantite = Integer.parseInt(quantiteField.getText().trim());
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateField.getText().trim());

            Stock stock = new Stock(0, idProduit, quantite, date);
            JOptionPane.showMessageDialog(this, stockController.ajouterStock("Magasinier", stock));
            clearFields();
            refreshTable();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur: " + ex.getMessage());
        }
    }

    private void modifierStock() {
        try {
            int idStock = Integer.parseInt(idField.getText().trim());
            int idProduit = Integer.parseInt(idProdField.getText().trim());
            int quantite = Integer.parseInt(quantiteField.getText().trim());
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateField.getText().trim());

            Stock stock = new Stock(idStock, idProduit, quantite, date);
            JOptionPane.showMessageDialog(this, stockController.mettreAJourStock("Magasinier", stock));
            clearFields();
            refreshTable();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur: " + ex.getMessage());
        }
    }

    private void supprimerStock() {
        try {
            int idStock = Integer.parseInt(idField.getText().trim());
            int confirm = JOptionPane.showConfirmDialog(this, "Confirmer la suppression ?", "Supprimer", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, stockController.supprimerStock("Admin", idStock));
                clearFields();
                refreshTable();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur: " + ex.getMessage());
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Stock> stocks = stockController.getStocksList();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (Stock s : stocks) {
            tableModel.addRow(new Object[]{
                s.getId_stock(), s.getId_produit(), s.getQuantite_stock(), sdf.format(s.getDate_misejour())
            });
        }
    }

    private void clearFields() {
        idField.setText("");
        idProdField.setText("");
        quantiteField.setText("");
        dateField.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
    }
}
