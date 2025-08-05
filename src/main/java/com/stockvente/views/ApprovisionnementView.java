package com.stockvente.views;

import com.stockvente.views.HistoriqueApprovisionnementView;


import com.stockvente.controller.AdminController;
import com.stockvente.controller.ApprovisionnementController;
import com.stockvente.models.Approvisionnement;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;

public class ApprovisionnementView extends JFrame {
    private final ApprovisionnementController controller;
    private final JTable table;
    private final DefaultTableModel tableModel;

    private final JTextField txtIdProduit;
    private final JTextField txtIdFournisseur;
    private final JTextField txtQuantite;
    private final JTextField txtPrix;
    private final JButton btnValider;

    private boolean modeModification = false;
    private int idEnCours = -1;

    public ApprovisionnementView() {
        controller = new ApprovisionnementController();
        setTitle("StockVente - Approvisionnements");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);  // IMPORTANT : autoriser redimensionnement
        setLayout(new BorderLayout(15, 15));

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 240));

        // -------- TITRE --------
        JLabel title = new JLabel("Gestion des Approvisionnements", SwingConstants.CENTER);
        title.setFont(new Font("Bell Mt", Font.BOLD, 26));
        title.setForeground(new Color(33, 150, 243));
        mainPanel.add(title, BorderLayout.NORTH);

        // -------- FORMULAIRE --------
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(Color.WHITE);
        panelForm.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(33, 150, 243), 2),
                "Formulaire Approvisionnement",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Bell Mt", Font.BOLD, 18),
                new Color(33, 150, 243)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font font = new Font("Bell Mt", Font.PLAIN, 16);

        JLabel lblIdProduit = new JLabel("ID Produit :");
        lblIdProduit.setFont(font);
        txtIdProduit = new JTextField();
        txtIdProduit.setFont(font);

        JLabel lblIdFournisseur = new JLabel("ID Fournisseur :");
        lblIdFournisseur.setFont(font);
        txtIdFournisseur = new JTextField();
        txtIdFournisseur.setFont(font);

        JLabel lblQuantite = new JLabel("Quantité :");
        lblQuantite.setFont(font);
        txtQuantite = new JTextField();
        txtQuantite.setFont(font);

        JLabel lblPrix = new JLabel("Prix Unitaire :");
        lblPrix.setFont(font);
        txtPrix = new JTextField();
        txtPrix.setFont(font);

        btnValider = new JButton("Ajouter");
        btnValider.setFont(new Font("Bell Mt", Font.BOLD, 16));
        btnValider.setBackground(new Color(33, 150, 243));
        btnValider.setForeground(Color.WHITE);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        panelForm.add(lblIdProduit, gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        panelForm.add(txtIdProduit, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        panelForm.add(lblIdFournisseur, gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        panelForm.add(txtIdFournisseur, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.3;
        panelForm.add(lblQuantite, gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        panelForm.add(txtQuantite, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.3;
        panelForm.add(lblPrix, gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        panelForm.add(txtPrix, gbc);

        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 0; gbc.anchor = GridBagConstraints.EAST;
        panelForm.add(btnValider, gbc);

        // -------- TABLE --------
        tableModel = new DefaultTableModel(new String[]{
                "ID", "Produit", "Fournisseur", "Quantité", "Prix", "Total", "Date"
        }, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);

        // -------- JSplitPane --------
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelForm, scrollPane);
        splitPane.setResizeWeight(0.3); // 30% pour formulaire, 70% pour table
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(280);

        mainPanel.add(splitPane, BorderLayout.CENTER);

        // -------- BOUTONS --------
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");
        JButton btnActualiser = new JButton("Actualiser");
        
        // Nouveau bouton Historique
        JButton btnHistorique = new JButton("Historique");
        btnHistorique.setFont(new Font("Bell Mt", Font.PLAIN, 15));
        btnHistorique.setBackground(new Color(224, 224, 224));
        btnHistorique.setPreferredSize(new Dimension(120, 35));

        // Ajout des boutons au panel
        for (JButton btn : new JButton[]{btnModifier, btnSupprimer, btnActualiser, btnHistorique}) {
            btn.setFont(new Font("Bell Mt", Font.PLAIN, 15));
            btn.setBackground(new Color(224, 224, 224));
            btn.setPreferredSize(new Dimension(120, 35));
        }
        


        buttonPanel.add(btnModifier);
        buttonPanel.add(btnSupprimer);
        buttonPanel.add(btnActualiser);
        buttonPanel.add(btnHistorique); 
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        
        // -------- LISTENERS --------
        btnValider.addActionListener(e -> soumettreApprovisionnement());
        btnModifier.addActionListener(e -> remplirFormulaireDepuisTable());
        btnSupprimer.addActionListener(e -> supprimerApprovisionnement());
        btnActualiser.addActionListener(e -> chargerApprovisionnements());
        btnHistorique.addActionListener(e -> {
            new HistoriqueApprovisionnementView().setVisible(true);
        });


        chargerApprovisionnements();
    }

    public ApprovisionnementView(AdminController adminController) {
        this();
    }

    private void soumettreApprovisionnement() {
        try {
            int idProduit = Integer.parseInt(txtIdProduit.getText());
            int idFournisseur = Integer.parseInt(txtIdFournisseur.getText());
            int quantite = Integer.parseInt(txtQuantite.getText());
            double prix = Double.parseDouble(txtPrix.getText());

            Approvisionnement a = new Approvisionnement(
                    modeModification ? idEnCours : 0,
                    idProduit, idFournisseur, quantite, prix, new Date()
            );

            String msg = modeModification ?
                    controller.mettreAJourApprovisionnement("Admin", a) :
                    controller.ajouterApprovisionnement("Admin", a);

            JOptionPane.showMessageDialog(this, msg);
            chargerApprovisionnements();
            reinitialiserFormulaire();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Champs invalides ou incomplets.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void remplirFormulaireDepuisTable() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligne à modifier.");
            return;
        }

        try {
            idEnCours = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            txtIdProduit.setText(tableModel.getValueAt(row, 1).toString());
            txtIdFournisseur.setText(tableModel.getValueAt(row, 2).toString());
            txtQuantite.setText(tableModel.getValueAt(row, 3).toString());
            txtPrix.setText(tableModel.getValueAt(row, 4).toString());

            modeModification = true;
            btnValider.setText("Mettre à jour");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des données.");
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

    private void reinitialiserFormulaire() {
        txtIdProduit.setText("");
        txtIdFournisseur.setText("");
        txtQuantite.setText("");
        txtPrix.setText("");
        btnValider.setText("Ajouter");
        modeModification = false;
        idEnCours = -1;
    }
}
