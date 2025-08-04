package com.stockvente.views;

import com.stockvente.controller.AdminController;
import com.stockvente.controller.VenteController;
import com.stockvente.models.Vente;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VenteView extends JFrame {
    private final VenteController venteController;
    private JTextField dateField, utilisateurField, clientField;
    private JTable table;
    private DefaultTableModel tableModel;
    private final String role;

    public VenteView(String role) {
        this.venteController = new VenteController();
        this.role = (role != null && !role.isEmpty()) ? role : "Vendeur";
        initUI();
    }

    public VenteView() {
        this("Vendeur");
    }

    public VenteView(AdminController adminController) {
        this("Admin");
    }

    private void initUI() {
        setTitle("Gestion des Ventes - Rôle : " + role);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 240));

        JLabel title = new JLabel("Gestion des Ventes", SwingConstants.CENTER);
        title.setFont(new Font("Bell Mt", Font.BOLD, 26));
        title.setForeground(new Color(33, 150, 243));
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(33, 150, 243), 2),
                "Formulaire Vente",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Bell Mt", Font.BOLD, 18),
                new Color(33, 150, 243)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font font = new Font("Bell Mt", Font.PLAIN, 16);

        JLabel lblDate = new JLabel("Date (dd/MM/yyyy) :");
        lblDate.setFont(font);
        dateField = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        dateField.setFont(font);

        JLabel lblUtilisateur = new JLabel("ID Utilisateur :");
        lblUtilisateur.setFont(font);
        utilisateurField = new JTextField();
        utilisateurField.setFont(font);

        JLabel lblClient = new JLabel("ID Client :");
        lblClient.setFont(font);
        clientField = new JTextField();
        clientField.setFont(font);

        JButton btnAjouter = new JButton("Ajouter");
        JButton btnAfficher = new JButton("Afficher");
        JButton btnModifier = new JButton("Modifier");

        btnAjouter.setFont(font);
        btnAfficher.setFont(font);
        btnModifier.setFont(font);
        btnAjouter.setBackground(new Color(33, 150, 243));
        btnAjouter.setForeground(Color.WHITE);

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(lblDate, gbc);
        gbc.gridx = 1;
        formPanel.add(dateField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(lblUtilisateur, gbc);
        gbc.gridx = 1;
        formPanel.add(utilisateurField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(lblClient, gbc);
        gbc.gridx = 1;
        formPanel.add(clientField, gbc);

        gbc.gridx = 1; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.add(btnAjouter);
        actionPanel.add(btnAfficher);
        if ("Admin".equalsIgnoreCase(role)) {
            actionPanel.add(btnModifier);
        }
        formPanel.add(actionPanel, gbc);

        tableModel = new DefaultTableModel(new String[]{"ID", "Date", "Utilisateur", "Client"}, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Bell Mt", Font.PLAIN, 14));
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Liste des ventes"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, scrollPane);
        splitPane.setResizeWeight(0.3);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(300);

        mainPanel.add(splitPane, BorderLayout.CENTER);

        // ===== Bouton Retour (Ajouté uniquement ici) =====
        JButton btnRetour = new JButton("← Retour");
        btnRetour.setFont(new Font("Bell Mt", Font.BOLD, 16));
        btnRetour.setBackground(new Color(33, 150, 243));
        btnRetour.setForeground(Color.WHITE);
        btnRetour.setFocusPainted(false);
        btnRetour.addActionListener(e -> {
            this.dispose();
            new ConcernerView().setVisible(true);
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBackground(new Color(240, 240, 240));
        bottomPanel.add(btnRetour);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // ================================================

        add(mainPanel);

        btnAjouter.addActionListener(this::ajouterVente);
        btnAfficher.addActionListener(this::afficherVentes);
        btnModifier.addActionListener(this::modifierVente);

        setVisible(true);
    }

    private void ajouterVente(ActionEvent e) {
        try {
            String dateText = dateField.getText().trim();
            String utilisateurText = utilisateurField.getText().trim();
            String clientText = clientField.getText().trim();

            if (dateText.isEmpty() || utilisateurText.isEmpty() || clientText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires.");
                return;
            }

            if (!utilisateurText.matches("\\d+") || !clientText.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Les ID doivent être des nombres entiers.");
                return;
            }

            Date dateVente = new SimpleDateFormat("dd/MM/yyyy").parse(dateText);
            int idUtilisateur = Integer.parseInt(utilisateurText);
            int idClient = Integer.parseInt(clientText);

            Vente vente = new Vente(0, dateVente, idUtilisateur, idClient);
            String result = venteController.ajouterVente(role, vente);
            JOptionPane.showMessageDialog(this, result);
            afficherVentes(null);

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "La date doit être au format dd/MM/yyyy.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
        }
    }

    private void afficherVentes(ActionEvent e) {
        try {
            tableModel.setRowCount(0);
            List<Vente> ventes = venteController.getToutesLesVentes(role);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            for (Vente v : ventes) {
                tableModel.addRow(new Object[]{
                        v.getId_vente(),
                        sdf.format(v.getDate_vente()),
                        v.getId_utilisateur(),
                        v.getId_client()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur d'affichage : " + ex.getMessage());
        }
    }

    private void modifierVente(ActionEvent e) {
        JOptionPane.showMessageDialog(this, "Fonctionnalité de modification non implémentée.");
    }
}
