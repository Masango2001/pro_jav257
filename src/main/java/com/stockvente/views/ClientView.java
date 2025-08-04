package com.stockvente.views;

import com.stockvente.controller.AdminController;
import com.stockvente.controller.ClientController;
import com.stockvente.models.Client;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClientView extends JFrame {

    private final ClientController clientController;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField idField;
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField adresseField;
    private JTextField telephoneField;
    private JButton btnValider;
    private JButton btnRetour;
    private final String role;
    private boolean modeModification = false;
    private int idEnCours = -1;

    public ClientView(String vendeur) {
        this.clientController = new ClientController();
        this.role = vendeur;
        initialiserUI();
    }

    public ClientView(AdminController adminController) {
        this.clientController = new ClientController();
        this.role = "Admin";
        initialiserUI();
    }

    private void initialiserUI() {
        setTitle("StockVente - Clients");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        setLayout(new BorderLayout(15, 15));

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 240));

        JLabel title = new JLabel("Gestion des Clients", SwingConstants.CENTER);
        title.setFont(new Font("Bell Mt", Font.BOLD, 26));
        title.setForeground(new Color(33, 150, 243));
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(Color.WHITE);
        panelForm.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(33, 150, 243), 2),
                "Formulaire Client",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Bell Mt", Font.BOLD, 18),
                new Color(33, 150, 243)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font font = new Font("Bell Mt", Font.PLAIN, 16);

        idField = new JTextField();
        nomField = new JTextField();
        prenomField = new JTextField();
        adresseField = new JTextField();
        telephoneField = new JTextField();

        JLabel lblId = new JLabel("ID (pour modifier/supprimer) :");
        lblId.setFont(font);
        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(lblId, gbc);
        gbc.gridx = 1;
        panelForm.add(idField, gbc);

        JLabel lblNom = new JLabel("Nom :");
        lblNom.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelForm.add(lblNom, gbc);
        gbc.gridx = 1;
        panelForm.add(nomField, gbc);

        JLabel lblPrenom = new JLabel("Prénom :");
        lblPrenom.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelForm.add(lblPrenom, gbc);
        gbc.gridx = 1;
        panelForm.add(prenomField, gbc);

        JLabel lblAdresse = new JLabel("Adresse :");
        lblAdresse.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelForm.add(lblAdresse, gbc);
        gbc.gridx = 1;
        panelForm.add(adresseField, gbc);

        JLabel lblTel = new JLabel("Téléphone :");
        lblTel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panelForm.add(lblTel, gbc);
        gbc.gridx = 1;
        panelForm.add(telephoneField, gbc);

        btnValider = new JButton("Ajouter");
        btnValider.setFont(new Font("Bell Mt", Font.BOLD, 16));
        btnValider.setBackground(new Color(33, 150, 243));
        btnValider.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 5;
        panelForm.add(btnValider, gbc);

        // Bouton retour
        btnRetour = new JButton("<- Retour");
        btnRetour.setFont(new Font("Bell Mt", Font.BOLD, 16));
        btnRetour.setBackground(new Color(33, 150, 243));
        btnRetour.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 5;
        panelForm.add(btnRetour, gbc);

        tableModel = new DefaultTableModel(new String[]{"ID", "Nom", "Prénom", "Adresse", "Téléphone"}, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("bell Mt", Font.PLAIN, 14));
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelForm, scrollPane);
        splitPane.setResizeWeight(0.3);
        splitPane.setDividerLocation(300);
        splitPane.setOneTouchExpandable(true);

        mainPanel.add(splitPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");
        JButton btnAfficher = new JButton("Afficher");

        for (JButton btn : new JButton[]{btnModifier, btnSupprimer, btnAfficher}) {
            btn.setFont(new Font("Bell Mt", Font.PLAIN, 15));
            btn.setBackground(new Color(224, 224, 224));
            btn.setPreferredSize(new Dimension(120, 35));
        }

        buttonPanel.add(btnModifier);
        buttonPanel.add(btnSupprimer);
        buttonPanel.add(btnAfficher);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Listeners
        btnValider.addActionListener(e -> ajouterClient());
        btnRetour.addActionListener(e -> {
            this.dispose();
            new VenteView().setVisible(true);
        });

        btnModifier.addActionListener(e -> modifierClient());
        btnSupprimer.addActionListener(e -> supprimerClient());
        btnAfficher.addActionListener(e -> afficherClients());

        afficherClients();
    }

    private void ajouterClient() {
        Client client = new Client();
        client.setNom_client(nomField.getText());
        client.setPrenom_client(prenomField.getText());
        client.setAdresse_client(adresseField.getText());
        client.setTelephone_client(telephoneField.getText());

        String resultat = clientController.ajouterClient(role, client);

        if (resultat.toLowerCase().contains("ajouté")) {
            JOptionPane.showMessageDialog(this, resultat, "Succès", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            afficherClients();
        } else {
            JOptionPane.showMessageDialog(this, resultat, "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierClient() {
        try {
            int id = Integer.parseInt(idField.getText());
            Client client = new Client();
            client.setId_client(id);
            client.setNom_client(nomField.getText());
            client.setPrenom_client(prenomField.getText());
            client.setAdresse_client(adresseField.getText());
            client.setTelephone_client(telephoneField.getText());

            String resultat = clientController.mettreAJourClient(role, client);
            JOptionPane.showMessageDialog(this, resultat);
            afficherClients();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erreur : ID invalide pour la modification.");
        }
    }

    private void supprimerClient() {
        try {
            int id = Integer.parseInt(idField.getText());
            String resultat = clientController.supprimerClient(role, id);
            JOptionPane.showMessageDialog(this, resultat);
            afficherClients();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erreur : ID invalide pour la suppression.");
        }
    }

    private void afficherClients() {
        tableModel.setRowCount(0);
        String data = clientController.afficherTousLesClients(role);

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
        }
    }

    private void clearFields() {
        idField.setText("");
        nomField.setText("");
        prenomField.setText("");
        adresseField.setText("");
        telephoneField.setText("");
    }
}
