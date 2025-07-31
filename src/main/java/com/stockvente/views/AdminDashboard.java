package com.stockvente.views;

import com.stockvente.controller.*;
import com.stockvente.dao.UtilisateurDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminDashboard extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(AdminDashboard.class.getName());
    private String roleConnecte;
    private AdminController adminController;
    private StatisticsController statisticsController;

    public AdminDashboard(String roleConnecte, AdminController adminController) {
        this.roleConnecte = roleConnecte;
        this.adminController = adminController;
        this.statisticsController = new StatisticsController();

        // Vérifier le rôle avant toute initialisation
        if (!"admin".equalsIgnoreCase(roleConnecte)) {
            LOGGER.log(Level.WARNING, "Tentative d'accès à AdminDashboard avec rôle non-admin : {0}", roleConnecte);
            JOptionPane.showMessageDialog(null,
                    "Accès réservé à l'administrateur.",
                    "Accès refusé", JOptionPane.ERROR_MESSAGE);
            return; // Ne pas initialiser l'interface
        }

        initComponents();
    }

    public AdminDashboard(AdminController adminController) {
        this("admin", adminController); // Par défaut, suppose le rôle "admin"
    }

    private void initComponents() {
        try {
            setTitle("Tableau de bord Administrateur");
            setSize(900, 600);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());

            // Titre
            JLabel titleLabel = new JLabel("Tableau de bord Administrateur", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
            titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            add(titleLabel, BorderLayout.NORTH);

            // Panneau central : les boutons de gestion
            JPanel centerPanel = new JPanel(new GridLayout(5, 2, 10, 10));
            centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

            JButton produitsBtn = new JButton("Gérer les Produits");
            JButton fournisseursBtn = new JButton("Gérer les Fournisseurs");
            JButton approvisionnementsBtn = new JButton("Gérer les Approvisionnements");
            JButton categoriesBtn = new JButton("Gérer les Catégories");
            JButton clientsBtn = new JButton("Gérer les Clients");
            JButton stocksBtn = new JButton("Gérer les Stocks");
            JButton ventesBtn = new JButton("Gérer les Ventes");
            JButton concernerBtn = new JButton("Gérer les Lignes de Vente");
            JButton utilisateursAdminBtn = new JButton("Ajouter Utilisateur (Admin)");
            JButton utilisateursRolesBtn = new JButton("Ajouter Utilisateur (Vendeur/Magasinier)");

            produitsBtn.addActionListener(e -> {
                try {
                    new ProduitView(adminController.getProduitController()).setVisible(true);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, "Erreur lors de l'ouverture de ProduitView : {0}", ex.getMessage());
                    JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            });

            fournisseursBtn.addActionListener(e -> {
                try {
                    new FournisseurView(adminController).setVisible(true);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, "Erreur lors de l'ouverture de FournisseurView : {0}", ex.getMessage());
                    JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            });

            approvisionnementsBtn.addActionListener(e -> {
                try {
                    new ApprovisionnementView(adminController).setVisible(true);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, "Erreur lors de l'ouverture de ApprovisionnementView : {0}", ex.getMessage());
                    JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            });

            categoriesBtn.addActionListener(e -> {
                try {
                    new CategorieView(adminController).setVisible(true);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, "Erreur lors de l'ouverture de CategorieView : {0}", ex.getMessage());
                    JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            });

            clientsBtn.addActionListener(e -> {
                try {
                    new ClientView(adminController).setVisible(true);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, "Erreur lors de l'ouverture de ClientView : {0}", ex.getMessage());
                    JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            });

            stocksBtn.addActionListener(e -> {
                try {
                    new StockView("admin", new StockController()).setVisible(true);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, "Erreur lors de l'ouverture de StockView : {0}", ex.getMessage());
                    JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            });

            ventesBtn.addActionListener(e -> {
                try {
                    new VenteView(adminController).setVisible(true);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, "Erreur lors de l'ouverture de VenteView : {0}", ex.getMessage());
                    JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            });

            concernerBtn.addActionListener(e -> {
                try {
                    new ConcernerView(adminController).setVisible(true);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, "Erreur lors de l'ouverture de ConcernerView : {0}", ex.getMessage());
                    JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            });

            utilisateursAdminBtn.addActionListener(e -> {
                try {
                    new UserManagementView(adminController.getUserManagementController(), roleConnecte, "Admin").setVisible(true);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, "Erreur lors de l'ouverture de UserManagementView (Admin) : {0}", ex.getMessage());
                    JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            });

            utilisateursRolesBtn.addActionListener(e -> {
                try {
                    String[] roles = {"Vendeur", "Magasinier"};
                    String roleChoisi = (String) JOptionPane.showInputDialog(this,
                            "Choisissez un rôle à attribuer :", "Rôle",
                            JOptionPane.QUESTION_MESSAGE, null, roles, roles[0]);
                    if (roleChoisi != null) {
                        new UserManagementView(adminController.getUserManagementController(),
                                roleConnecte, roleChoisi).setVisible(true);
                    }
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, "Erreur lors de l'ouverture de UserManagementView (Vendeur/Magasinier) : {0}", ex.getMessage());
                    JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            });

            centerPanel.add(produitsBtn);
            centerPanel.add(fournisseursBtn);
            centerPanel.add(approvisionnementsBtn);
            centerPanel.add(categoriesBtn);
            centerPanel.add(clientsBtn);
            centerPanel.add(stocksBtn);
            centerPanel.add(ventesBtn);
            centerPanel.add(concernerBtn);
            centerPanel.add(utilisateursAdminBtn);
            centerPanel.add(utilisateursRolesBtn);

            add(centerPanel, BorderLayout.CENTER);

            // Panneau bas : Statistiques du tableau de bord
            add(createStatisticsPanel(), BorderLayout.SOUTH);

            setVisible(true);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'initialisation de AdminDashboard : {0}", e.getMessage());
            JOptionPane.showMessageDialog(this, "Erreur lors de l'initialisation : " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    private JPanel createStatisticsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(3, 2, 10, 5));
        statsPanel.setBorder(BorderFactory.createTitledBorder("📊 Statistiques"));

        JLabel totalUsers = new JLabel("🔹 Total utilisateurs : " + statisticsController.getTotalUtilisateurs());
        JLabel totalProduits = new JLabel("🔹 Total produits : " + statisticsController.getTotalProduits());
        JLabel quantiteStock = new JLabel("🔹 Quantité totale en stock : " + statisticsController.getQuantiteTotaleEnStock());
        JLabel totalVentes = new JLabel("🔹 Nombre total de ventes : " + statisticsController.getNombreTotalVentes());
        JLabel vendeurActif = new JLabel("🔹 Vendeur le plus actif : " + statisticsController.getNomVendeurLePlusActif());
        JLabel magasinierActif = new JLabel("🔹 Magasinier le plus actif : " + statisticsController.getMagasinierLePlusActif());

        Font font = new Font("Arial", Font.PLAIN, 14);
        for (JLabel label : new JLabel[]{totalUsers, totalProduits, quantiteStock, totalVentes, vendeurActif, magasinierActif}) {
            label.setFont(font);
            statsPanel.add(label);
        }

        return statsPanel;
    }

}