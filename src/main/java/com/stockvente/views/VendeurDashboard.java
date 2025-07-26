package com.stockvente.views;

import com.stockvente.controller.StockController;
import com.stockvente.controller.VendeurController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class VendeurDashboard extends JFrame {

    public VendeurDashboard() {
        setTitle("Tableau de bord - Vendeur");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Titre
        JLabel titre = new JLabel("Dashboard Vendeur", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 24));
        add(titre, BorderLayout.NORTH);

        // Boutons de navigation
        JPanel panelBoutons = new JPanel(new GridLayout(2, 2, 15, 15));
        panelBoutons.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JButton clientBtn = new JButton("Gérer les Clients");
        JButton venteBtn = new JButton("Gérer les Ventes");
        JButton stockBtn = new JButton("Afficher les Stocks");
        JButton concernerBtn = new JButton("Afficher les Lignes de Vente");

        clientBtn.addActionListener(e -> new ClientView("Vendeur").setVisible(true));
        venteBtn.addActionListener(e -> new VenteView("Vendeur").setVisible(true));
        stockBtn.addActionListener(e -> {
                StockController stockController = new StockController();
                new StockView("Vendeur", stockController).setVisible(true);
        });

        concernerBtn.addActionListener(e -> new ConcernerView("Vendeur").setVisible(true));

        panelBoutons.add(clientBtn);
        panelBoutons.add(venteBtn);
        panelBoutons.add(stockBtn);
        panelBoutons.add(concernerBtn);

        add(panelBoutons, BorderLayout.CENTER);

        // Panel pour bouton de déconnexion
        JPanel bottomPanel = new JPanel();
        JButton logoutBtn = new JButton("Se Déconnecter");
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setBackground(new Color(220, 53, 69)); // Rouge

        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Voulez-vous vraiment vous déconnecter ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                dispose(); // Fermer la fenêtre actuelle
                // Rediriger vers LoginView si disponible
                // new LoginView().setVisible(true);
            }
        });

        bottomPanel.add(logoutBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VendeurDashboard().setVisible(true);
        });
    }

    VendeurDashboard(String role, VendeurController vendeurController) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
