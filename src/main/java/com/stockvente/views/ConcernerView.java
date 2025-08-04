package com.stockvente.views;

import com.stockvente.controller.AdminController;
import com.stockvente.controller.ConcernerController;
import com.stockvente.models.Concerner;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConcernerView extends JFrame {
    private final ConcernerController concernerController;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JTextField venteField, produitField, quantiteField, prixField;
    private final String role;

    public ConcernerView(String role) {
        this.role = (role != null && !role.isEmpty()) ? role : "Vendeur";
        this.concernerController = new ConcernerController();
        setTitle("Gestion des Lignes de Vente");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 240));

        JLabel title = new JLabel("Lignes de Vente", SwingConstants.CENTER);
        title.setFont(new Font("Bell Mt", Font.BOLD, 26));
        title.setForeground(new Color(33, 150, 243));
        mainPanel.add(title, BorderLayout.NORTH);

        // Formulaire
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(Color.WHITE);
        panelForm.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(33, 150, 243), 2),
                "Formulaire Ligne de Vente",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Bell Mt", Font.BOLD, 18),
                new Color(33, 150, 243)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Font font = new Font("Bell Mt", Font.PLAIN, 16);

        JLabel lblVente = new JLabel("ID Vente :");
        lblVente.setFont(font);
        venteField = new JTextField();
        venteField.setFont(font);

        JLabel lblProduit = new JLabel("ID Produit :");
        lblProduit.setFont(font);
        produitField = new JTextField();
        produitField.setFont(font);

        JLabel lblQuantite = new JLabel("Quantité :");
        lblQuantite.setFont(font);
        quantiteField = new JTextField();
        quantiteField.setFont(font);

        JLabel lblPrix = new JLabel("Prix Unitaire :");
        lblPrix.setFont(font);
        prixField = new JTextField();
        prixField.setFont(font);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        panelForm.add(lblVente, gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        panelForm.add(venteField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        panelForm.add(lblProduit, gbc);
        gbc.gridx = 1;
        panelForm.add(produitField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panelForm.add(lblQuantite, gbc);
        gbc.gridx = 1;
        panelForm.add(quantiteField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panelForm.add(lblPrix, gbc);
        gbc.gridx = 1;
        panelForm.add(prixField, gbc);

        JButton ajouterBtn = new JButton("Ajouter");
        ajouterBtn.setFont(new Font("Bell Mt", Font.BOLD, 16));
        ajouterBtn.setBackground(new Color(33, 150, 243));
        ajouterBtn.setForeground(Color.WHITE);

        gbc.gridx = 1; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST;
        panelForm.add(ajouterBtn, gbc);

        // Table
        tableModel = new DefaultTableModel(new String[] {"ID Vente", "ID Produit", "Quantité", "Prix Unitaire", "Total"}, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Bell Mt", Font.PLAIN, 14));
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lignes de vente"));

        // Split
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelForm, scrollPane);
        splitPane.setResizeWeight(0.3);
        splitPane.setDividerLocation(300);

        mainPanel.add(splitPane, BorderLayout.CENTER);

        // Bas de page avec boutons
        JButton afficherBtn = new JButton("Afficher");
        afficherBtn.setFont(new Font("Bell Mt", Font.PLAIN, 15));
        afficherBtn.setBackground(new Color(224, 224, 224));
        afficherBtn.setPreferredSize(new Dimension(120, 35));

        JButton retourBtn = new JButton("Retour");
        retourBtn.setFont(new Font("Bell Mt", Font.PLAIN, 15));
        retourBtn.setBackground(new Color(224, 224, 224));
        retourBtn.setPreferredSize(new Dimension(120, 35));
        retourBtn.addActionListener(e -> {
            dispose();
            try {
                new StockConsultationView();
            } catch (SQLException ex) {
                Logger.getLogger(ConcernerView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(retourBtn);
        btnPanel.add(afficherBtn);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        add(mainPanel);

        ajouterBtn.addActionListener(this::ajouterLigneDeVente);
        afficherBtn.addActionListener(this::afficherToutesLesLignes);
        afficherToutesLesLignes(null);
    }

    public ConcernerView() {
        this("Vendeur");
    }

    public ConcernerView(AdminController adminController) {
        this("Admin");
    }

    private void ajouterLigneDeVente(ActionEvent e) {
        try {
            int idVente = Integer.parseInt(venteField.getText());
            int idProduit = Integer.parseInt(produitField.getText());
            int quantite = Integer.parseInt(quantiteField.getText());
            double prix = Double.parseDouble(prixField.getText());

            Concerner ligne = new Concerner(idVente, idProduit, quantite, prix);
            String result = concernerController.ajouterLigneDeVente(role, ligne);
            JOptionPane.showMessageDialog(this, result);
            afficherToutesLesLignes(null);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Champs invalides.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void afficherToutesLesLignes(ActionEvent e) {
        tableModel.setRowCount(0);
        List<Concerner> lignes = concernerController.getToutesLesLignesDeVente(role);
        for (Concerner c : lignes) {
            tableModel.addRow(new Object[]{
                    c.getId_vente(),
                    c.getId_produit(),
                    c.getQuantite_vendue(),
                    c.getPrix_unitaire_vendue(),
                    c.getMontantTotalVente()
            });
        }
    }
}
