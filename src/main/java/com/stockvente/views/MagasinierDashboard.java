package com.stockvente.views;

import com.stockvente.controller.ProduitController;
import com.stockvente.controller.StockController;
import javax.swing.*;
import java.awt.*;

public class MagasinierDashboard extends JFrame {

    public MagasinierDashboard() {
        setTitle("Tableau de bord du Magasinier");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelMain = new JPanel();
        panelMain.setLayout(new GridLayout(3, 2, 15, 15));
        panelMain.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JButton btnProduits = new JButton("Gérer les Produits");
        JButton btnApprovisionnements = new JButton("Gérer les Approvisionnements");
        JButton btnStocks = new JButton("Gérer les Stocks");
        JButton btnCategories = new JButton("Gérer les Catégories");
        JButton btnFournisseurs = new JButton("Gérer les Fournisseurs");
        JButton btnDeconnecter = new JButton("Se Déconnecter");

        panelMain.add(btnProduits);
        panelMain.add(btnApprovisionnements);
        panelMain.add(btnStocks);
        panelMain.add(btnCategories);
        panelMain.add(btnFournisseurs);
        panelMain.add(btnDeconnecter);

        add(panelMain);

        // Actions des boutons
        btnProduits.addActionListener(e -> {
            ProduitController produitController = new ProduitController();
            new ProduitView(produitController).setVisible(true);
        });


        btnApprovisionnements.addActionListener(e -> new ApprovisionnementView().setVisible(true));

        btnStocks.addActionListener(e -> {StockController stockController = new StockController();
    
                    new StockView(stockController).setVisible(true);
        });


        btnCategories.addActionListener(e -> new CategorieView().setVisible(true));

        btnFournisseurs.addActionListener(e -> new FournisseurView().setVisible(true));

        btnDeconnecter.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Voulez-vous vraiment vous déconnecter ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                // Rediriger vers la page de connexion (si LoginView existe)
                // new LoginView().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MagasinierDashboard().setVisible(true));
    }
}
