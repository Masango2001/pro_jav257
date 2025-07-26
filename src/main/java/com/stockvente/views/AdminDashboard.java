package com.stockvente.views;

import com.stockvente.controller.AdminController;
import com.stockvente.controller.*;
import com.stockvente.dao.UtilisateurDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboard extends JFrame {
    private final String roleConnecte;
    private final AdminController adminController;

    public AdminDashboard(String roleConnecte, AdminController adminController) {
        this.roleConnecte = roleConnecte;
        this.adminController = adminController;

        if (!"Admin".equalsIgnoreCase(roleConnecte)) {
            JOptionPane.showMessageDialog(this,
                    "Accès réservé à l'administrateur.",
                    "Accès refusé", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        initComponents();
    }



    private void initComponents() {
        setTitle("Tableau de bord Administrateur");
        setSize(700, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Tableau de bord Administrateur", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Boutons
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

        // Actions de navigation (à connecter aux vues réelles)
       produitsBtn.addActionListener(e -> new ProduitView(adminController.getProduitController()).setVisible(true));

        fournisseursBtn.addActionListener(e -> new FournisseurView(adminController).setVisible(true));
        approvisionnementsBtn.addActionListener(e -> new ApprovisionnementView(adminController).setVisible(true));
        categoriesBtn.addActionListener(e -> new CategorieView(adminController).setVisible(true));
        clientsBtn.addActionListener(e -> new ClientView(adminController).setVisible(true));
        stocksBtn.addActionListener(e -> {
            StockController stockController = new StockController();
            new StockView("Admin", stockController).setVisible(true);
        });

        ventesBtn.addActionListener(e -> new VenteView(adminController).setVisible(true));
        concernerBtn.addActionListener(e -> new ConcernerView(adminController).setVisible(true));

        utilisateursAdminBtn.addActionListener(e -> {
            new UserManagementView(adminController.getUserManagementController(),
                    roleConnecte, "Admin").setVisible(true);
        });

        utilisateursRolesBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] roles = {"Vendeur", "Magasinier"};
                String roleChoisi = (String) JOptionPane.showInputDialog(AdminDashboard.this, "Choisissez un rôle à attribuer :", "Rôle", JOptionPane.QUESTION_MESSAGE, null, roles, roles[0]);
                if (roleChoisi != null) {
                    new UserManagementView(adminController.getUserManagementController(),
                            roleConnecte, roleChoisi).setVisible(true);
                }
            }
        });

        // Ajout des boutons au panneau
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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Initialisation des contrôleurs réels (ou mock si nécessaire)
            AdminController adminController = new AdminController(
                    new ProduitController(),
                    new FournisseurController(),
                    new ApprovisionnementController(),
                    new CategorieController(),
                    new ClientController(),
                    new StockController(),
                    new VenteController(),
                    new UserManagementController(new UtilisateurDao()),
                    new ConcernerController()
            );

            new AdminDashboard("Admin", adminController).setVisible(true);
        });
    }
}
