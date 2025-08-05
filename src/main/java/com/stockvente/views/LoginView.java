package com.stockvente.views;

import com.stockvente.controller.AdminController;
import com.stockvente.controller.LoginController;
import com.stockvente.models.Utilisateur;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class LoginView extends JFrame {
    private final LoginController loginController;
    private Utilisateur utilisateur;

    public LoginView() {
        this.loginController = new LoginController();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Connexion - StockVente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.setBackground(new Color(240, 240, 240));

        JLabel titleLabel = new JLabel("StockVente - Connexion", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Bell Mt", Font.BOLD, 28));
        titleLabel.setForeground(new Color(33, 150, 243));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(33, 150, 243), 2),
                "Connexion",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Bell Mt", Font.BOLD, 20),
                new Color(33, 150, 243)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("Nom d'utilisateur :");
        userLabel.setFont(new Font("Bell Mt", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(userLabel, gbc);

        JTextField userField = new JTextField(20);
        userField.setFont(new Font("Bell Mt", Font.PLAIN, 18));
        userField.setPreferredSize(new Dimension(300, 40));
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(userField, gbc);

        JLabel passwordLabel = new JLabel("Mot de passe :");
        passwordLabel.setFont(new Font("Bell Mt", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Bell Mt", Font.PLAIN, 18));
        passwordField.setPreferredSize(new Dimension(300, 40));
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(passwordField, gbc);

        JButton loginButton = new JButton("Se connecter");
        loginButton.setFont(new Font("Bell Mt", Font.BOLD, 16));
        loginButton.setBackground(new Color(33, 150, 243));
        loginButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(150, 40));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(loginButton, gbc);

        // Action du bouton
        loginButton.addActionListener(e -> {
            try {
                Utilisateur user = loginController.login(
                        userField.getText(),
                        new String(passwordField.getPassword())
                );
                this.utilisateur = user;  // Stocker l'utilisateur connecté

                // Redirection selon le rôle
                switch (user.getRole().toLowerCase()) {
                    case "admin":
                        AdminController adminController = new AdminController();
                        new AdminDashboard("Admin", adminController).setVisible(true);
                        dispose();
                        break;
                    case "vendeur":
                        new VendeurDashboard().setVisible(true);
                        dispose();
                        break;
                    case "magasinier":
                        new MagasinierDashboard().setVisible(true);
                        dispose();
                        break;
                    default:
                        JOptionPane.showMessageDialog(LoginView.this,
                                "Rôle inconnu : " + user.getRole(),
                                "Erreur", JOptionPane.ERROR_MESSAGE);
                }

            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(LoginView.this, ex.getMessage(), "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(LoginView.this, ex.getMessage(), "Erreur système", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Entrée = bouton se connecter
        getRootPane().setDefaultButton(loginButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);
        setVisible(true);
    }

    public Utilisateur afficherEtObtenirUtilisateur() {
        this.setVisible(true);

        while (utilisateur == null) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return utilisateur;
    }
}
