package com.stockvente.views;
import com.stockvente.views.LoginView;
import com.stockvente.controller.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminDashboard extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(AdminDashboard.class.getName());
    private String roleConnecte;
    private AdminController adminController;
    private StatisticsController statisticsController;
    
    // Couleurs modernes
    private static final Color PRIMARY_COLOR = new Color(52, 73, 94);
    private static final Color SECONDARY_COLOR = new Color(102, 126, 234);
    private static final Color ACCENT_COLOR = new Color(118, 75, 162);
    private static final Color BACKGROUND_COLOR = new Color(248, 249, 250);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_PRIMARY = new Color(44, 62, 80);
    private static final Color TEXT_SECONDARY = new Color(127, 140, 141);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color WARNING_COLOR = new Color(241, 196, 15);

    public AdminDashboard(String roleConnecte, AdminController adminController) {
        this.roleConnecte = roleConnecte;
        this.adminController = adminController;
        this.statisticsController = new StatisticsController();

        // Vérifier le rôle avant toute initialisation
        if (!"admin".equalsIgnoreCase(roleConnecte)) {
            LOGGER.log(Level.WARNING, "Tentative d'accès à AdminDashboard avec rôle non-admin : {0}", roleConnecte);
            showModernErrorDialog("Accès réservé à l'administrateur.", "Accès refusé");
            return;
        }

        initModernComponents();
    }

    public AdminDashboard(AdminController adminController) {
        this("admin", adminController);
    }

    private void initModernComponents() {
        try {
            // Configuration de base
            setTitle(" Administrateur");
            setSize(1200, 800);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());
            
            // Anti-aliasing pour un rendu plus net
            System.setProperty("awt.useSystemAAFontSettings", "on");
            System.setProperty("swing.aatext", "true");

            // Couleur de fond principale
            getContentPane().setBackground(BACKGROUND_COLOR);

            // Header moderne
            add(createModernHeader(), BorderLayout.NORTH);
            
            // Panneau principal avec scroll
            JScrollPane scrollPane = new JScrollPane(createMainContentPanel());
            scrollPane.setBorder(null);
            scrollPane.getVerticalScrollBar().setUnitIncrement(16);
            add(scrollPane, BorderLayout.CENTER);

            setVisible(true);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'initialisation de AdminDashboard : {0}", e.getMessage());
            showModernErrorDialog("Erreur lors de l'initialisation : " + e.getMessage(), "Erreur");
            dispose();
        }
    }

    private JPanel createModernHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        // Titre principal
        JLabel titleLabel = new JLabel("Administrateur");
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);

        // Sous-titre
        JLabel subtitleLabel = new JLabel("Gérez votre système de stockage et de vente en toute simplicité");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(255, 255, 255, 180));

        JPanel textPanel = new JPanel(new BorderLayout(0, 8));
        textPanel.setOpaque(false);
        textPanel.add(titleLabel, BorderLayout.NORTH);
        textPanel.add(subtitleLabel, BorderLayout.CENTER);

        // Indicateur de rôle
        JLabel roleIndicator = new JLabel("👑 " + roleConnecte.toUpperCase());
        roleIndicator.setFont(new Font("Segoe UI", Font.BOLD, 12));
        roleIndicator.setForeground(SUCCESS_COLOR);
        roleIndicator.setHorizontalAlignment(SwingConstants.RIGHT);

        headerPanel.add(textPanel, BorderLayout.CENTER);
        headerPanel.add(roleIndicator, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createMainContentPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        // Grille des cartes de gestion
        JPanel managementGrid = createManagementGrid();
        
        // Section de gestion des utilisateurs
        JPanel userManagementSection = createUserManagementSection();
        
        // Section des statistiques
        JPanel statisticsSection = createStatisticsSection();

        // Section des actions rapides
        JPanel quickActionsSection = createQuickActionsSection();
        
        // Assemblage
        JPanel contentPanel = new JPanel(new BorderLayout(0, 30));
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.add(managementGrid, BorderLayout.NORTH);
        
        JPanel bottomPanel = new JPanel(new BorderLayout(0, 30));
        bottomPanel.setBackground(BACKGROUND_COLOR);
        bottomPanel.add(userManagementSection, BorderLayout.NORTH);
        bottomPanel.add(statisticsSection, BorderLayout.CENTER);
        bottomPanel.add(quickActionsSection, BorderLayout.SOUTH);
        
        contentPanel.add(bottomPanel, BorderLayout.CENTER);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel createManagementGrid() {
        JPanel gridPanel = new JPanel(new GridLayout(2, 4, 20, 20));
        gridPanel.setBackground(BACKGROUND_COLOR);

        // Données des cartes (icône, titre, description, action)
        Object[][] cardData = {
            {"📦", "Gérer les Produits", "Ajoutez, modifiez et supprimez vos produits", (Runnable) this::openProduitView},
            {"🏢", "Gérer les Fournisseurs", "Gérez vos partenaires fournisseurs", (Runnable) this::openFournisseurView},
            {"🚚", "Gérer les Approvisionnements", "Suivez vos commandes et livraisons", (Runnable) this::openApprovisionnementView},
            {"🏷️", "Gérer les Catégories", "Organisez vos produits par catégories", (Runnable) this::openCategorieView},
            {"👥", "Gérer les Clients", "Base de données clients complète", (Runnable) this::openClientView},
            {"📊", "Gérer les Stocks", "Contrôlez vos niveaux de stock", (Runnable) this::openStockView},
            {"💰", "Gérer les Ventes", "Suivi complet des transactions", (Runnable) this::openVenteView},
            {"📋", "Gérer les Lignes de Vente", "Détails des ventes par ligne", (Runnable) this::openConcernerView}
        };

        for (Object[] card : cardData) {
            JPanel cardPanel = createModernCard((String) card[0], (String) card[1], (String) card[2], (Runnable) card[3]);
            gridPanel.add(cardPanel);
        }

        return gridPanel;
    }

    private JPanel createModernCard(String icon, String title, String description, Runnable action) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(0, 15));
        card.setBackground(CARD_COLOR);
        card.setBorder(new EmptyBorder(25, 25, 25, 25));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Effet d'ombre avec setBorder personnalisé
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 20), 1),
            new EmptyBorder(24, 24, 24, 24)
        ));

        // Icône
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        iconLabel.setHorizontalAlignment(SwingConstants.LEFT);

        // Titre
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(TEXT_PRIMARY);

        // Description
        JLabel descLabel = new JLabel("<html>" + description + "</html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(TEXT_SECONDARY);

        // Panel pour titre et description
        JPanel textPanel = new JPanel(new BorderLayout(0, 8));
        textPanel.setOpaque(false);
        textPanel.add(titleLabel, BorderLayout.NORTH);
        textPanel.add(descLabel, BorderLayout.CENTER);

        card.add(iconLabel, BorderLayout.NORTH);
        card.add(textPanel, BorderLayout.CENTER);

        // Effets de survol
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(248, 249, 250));
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(SECONDARY_COLOR, 2),
                    new EmptyBorder(23, 23, 23, 23)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(CARD_COLOR);
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0, 0, 0, 20), 1),
                    new EmptyBorder(24, 24, 24, 24)
                ));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                action.run();
            }
        });

        return card;
    }

    private JPanel createUserManagementSection() {
        JPanel section = new JPanel(new GridLayout(1, 2, 20, 0));
        section.setBackground(BACKGROUND_COLOR);

        // Carte Admin
        JPanel adminCard = createUserManagementCard(
            "👑", "Ajouter un utilisateur", "Créer un nouveau compte utilisateur",
            SECONDARY_COLOR, this::openAdminUserManagement
        );

        // Carte Employé
        JPanel employeeCard = createUserManagementCard(
            "👤", "Ajouter Employé", "Vendeur ou Magasinier",
            ACCENT_COLOR, this::openEmployeeUserManagement
        );

        section.add(adminCard);
        section.add(employeeCard);

        return section;
    }

    private JPanel createUserManagementCard(String icon, String title, String subtitle, Color bgColor, Runnable action) {
        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(bgColor);
        card.setBorder(new EmptyBorder(30, 30, 30, 30));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel(subtitle, SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(255, 255, 255, 200));

        JPanel textPanel = new JPanel(new BorderLayout(0, 5));
        textPanel.setOpaque(false);
        textPanel.add(titleLabel, BorderLayout.NORTH);
        textPanel.add(subtitleLabel, BorderLayout.CENTER);

        card.add(iconLabel, BorderLayout.NORTH);
        card.add(textPanel, BorderLayout.CENTER);

        // Effet de survol
        card.addMouseListener(new MouseAdapter() {
            Color originalColor = bgColor;
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(bgColor.darker());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(originalColor);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                action.run();
            }
        });

        return card;
    }

    private JPanel createStatisticsSection() {
        JPanel section = new JPanel(new BorderLayout(0, 20));
        section.setBackground(BACKGROUND_COLOR);

        // Titre de section
        JLabel sectionTitle = new JLabel("📊 Statistiques du Système");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        sectionTitle.setForeground(TEXT_PRIMARY);

        // Grille des statistiques - Modifiée pour 5 éléments au lieu de 6
        JPanel statsGrid = new JPanel(new GridLayout(2, 3, 15, 15));
        statsGrid.setBackground(BACKGROUND_COLOR);

        // Données des statistiques - LIGNE MAGASINIER SUPPRIMÉE
        Object[][] statsData = {
            {"👥", "Total utilisateurs", String.valueOf(statisticsController.getTotalUtilisateurs())},
            {"📦", "Total produits", String.valueOf(statisticsController.getTotalProduits())},
            {"📊", "Quantité en stock", String.valueOf(statisticsController.getQuantiteTotaleEnStock())},
            {"💰", "Total ventes", String.valueOf(statisticsController.getNombreTotalVentes())},
            {"🏆", "Vendeur le plus actif", statisticsController.getNomVendeurLePlusActif()}
        };

        for (Object[] stat : statsData) {
            JPanel statCard = createStatCard((String) stat[0], (String) stat[1], (String) stat[2]);
            statsGrid.add(statCard);
        }

        section.add(sectionTitle, BorderLayout.NORTH);
        section.add(statsGrid, BorderLayout.CENTER);

        return section;
    }

    private JPanel createStatCard(String icon, String label, String value) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 10), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));

        // Icône
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        iconLabel.setPreferredSize(new Dimension(40, 40));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Texte
        JPanel textPanel = new JPanel(new BorderLayout(0, 5));
        textPanel.setOpaque(false);

        JLabel labelText = new JLabel(label);
        labelText.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        labelText.setForeground(TEXT_SECONDARY);

        JLabel valueText = new JLabel(value);
        valueText.setFont(new Font("Segoe UI", Font.BOLD, 16));
        valueText.setForeground(TEXT_PRIMARY);

        textPanel.add(labelText, BorderLayout.NORTH);
        textPanel.add(valueText, BorderLayout.CENTER);

        card.add(iconLabel, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);

        return card;
    }

    // Méthodes d'ouverture des vues (gardez votre logique existante)
    private void openProduitView() {
        try {
            new ProduitView(adminController.getProduitController()).setVisible(true);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'ouverture de ProduitView : {0}", ex.getMessage());
            showModernErrorDialog("Erreur : " + ex.getMessage(), "Erreur");
        }
    }

    private void openFournisseurView() {
        try {
            new FournisseurView(adminController).setVisible(true);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'ouverture de FournisseurView : {0}", ex.getMessage());
            showModernErrorDialog("Erreur : " + ex.getMessage(), "Erreur");
        }
    }

    private void openApprovisionnementView() {
        try {
            new ApprovisionnementView(adminController).setVisible(true);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'ouverture de ApprovisionnementView : {0}", ex.getMessage());
            showModernErrorDialog("Erreur : " + ex.getMessage(), "Erreur");
        }
    }

    private void openCategorieView() {
        try {
            new CategorieView(adminController).setVisible(true);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'ouverture de CategorieView : {0}", ex.getMessage());
            showModernErrorDialog("Erreur : " + ex.getMessage(), "Erreur");
        }
    }

    private void openClientView() {
        try {
            new ClientView(adminController).setVisible(true);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'ouverture de ClientView : {0}", ex.getMessage());
            showModernErrorDialog("Erreur : " + ex.getMessage(), "Erreur");
        }
    }

    private void openStockView() {
        try {
            new StockView("admin", new StockController()).setVisible(true);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'ouverture de StockView : {0}", ex.getMessage());
            showModernErrorDialog("Erreur : " + ex.getMessage(), "Erreur");
        }
    }

    private void openVenteView() {
        try {
            new VenteView(adminController).setVisible(true);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'ouverture de VenteView : {0}", ex.getMessage());
            showModernErrorDialog("Erreur : " + ex.getMessage(), "Erreur");
        }
    }

    private void openConcernerView() {
        try {
            new ConcernerView(adminController).setVisible(true);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'ouverture de ConcernerView : {0}", ex.getMessage());
            showModernErrorDialog("Erreur : " + ex.getMessage(), "Erreur");
        }
    }

    private void openAdminUserManagement() {
        try {
            new UserManagementView(adminController.getUserManagementController(), roleConnecte, "Admin").setVisible(true);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'ouverture de UserManagementView (Admin) : {0}", ex.getMessage());
            showModernErrorDialog("Erreur : " + ex.getMessage(), "Erreur");
        }
    }

    private void openEmployeeUserManagement() {
        try {
            String[] roles = {"Vendeur", "Magasinier"};
            String roleChoisi = (String) JOptionPane.showInputDialog(this,
                    "Choisissez un rôle à attribuer :", "Sélection du rôle",
                    JOptionPane.QUESTION_MESSAGE, null, roles, roles[0]);
            if (roleChoisi != null) {
                new UserManagementView(adminController.getUserManagementController(),
                        roleConnecte, roleChoisi).setVisible(true);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'ouverture de UserManagementView (Employé) : {0}", ex.getMessage());
            showModernErrorDialog("Erreur : " + ex.getMessage(), "Erreur");
        }
    }

    private void showModernErrorDialog(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    private JPanel createQuickActionsSection() {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(BACKGROUND_COLOR);

        JLabel sectionTitle = new JLabel("⚡ Actions Rapides");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sectionTitle.setForeground(TEXT_PRIMARY);
        sectionTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        // Panel des boutons d'action
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        actionsPanel.setBackground(BACKGROUND_COLOR);

        // Bouton de déconnexion moderne
        JButton btnDeconnecter = createModernButton("🚪 Se Déconnecter", new Color(231, 76, 60));
        btnDeconnecter.addActionListener(e -> handleLogout());

        // Autres boutons d'actions rapides
        JButton btnRefresh = createModernButton("🔄 Actualiser", SECONDARY_COLOR);
        btnRefresh.addActionListener(e -> refreshDashboard());

        JButton btnHelp = createModernButton("❓ Aide", new Color(149, 165, 166));
        btnHelp.addActionListener(e -> showHelp());

        actionsPanel.add(btnRefresh);
        actionsPanel.add(btnHelp);
        actionsPanel.add(btnDeconnecter);

        section.add(sectionTitle, BorderLayout.NORTH);
        section.add(actionsPanel, BorderLayout.CENTER);

        return section;
    }

    private JButton createModernButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Effets de survol
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void handleLogout() {
        // Dialogue de confirmation moderne
        int result = JOptionPane.showConfirmDialog(
            this,
            "Voulez-vous vraiment vous déconnecter de l'administration ?",
            "Confirmation de déconnexion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (result == JOptionPane.YES_OPTION) {
            dispose();
            // Rediriger vers la page de connexion (si LoginView existe)
            new LoginView().setVisible(true);
        }
    }

    private void refreshDashboard() {
        // Animation de rafraîchissement - on peut rafraîchir les statistiques
        try {
            // Recharger les statistiques
            statisticsController = new StatisticsController();
            JOptionPane.showMessageDialog(this, 
                "Tableau de bord administrateur actualisé avec succès !", 
                "Actualisation", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            showModernErrorDialog("Erreur lors de l'actualisation : " + e.getMessage(), "Erreur");
        }
    }

    private void showHelp() {
        String helpMessage = """
            🔧 AIDE - TABLEAU DE BORD ADMINISTRATEUR
            
            📦 Gérer les Produits : Gestion complète des produits
            🏢 Gérer les Fournisseurs : Base de données fournisseurs
            🚚 Gérer les Approvisionnements : Suivi des commandes
            🏷️ Gérer les Catégories : Organisation des produits
            👥 Gérer les Clients : Base de données clients
            📊 Gérer les Stocks : Contrôle des niveaux de stock
            💰 Gérer les Ventes : Suivi des transactions
            📋 Gérer les Lignes de Vente : Détails des ventes
            👑 Ajouter Administrateur : Créer des comptes admin
            📊 Section Statistiques : Vue d'ensemble du système
            
            Pour plus d'aide, consultez la documentation système.
            """;
        
        JOptionPane.showMessageDialog(this, helpMessage, "Aide - Administration", JOptionPane.INFORMATION_MESSAGE);
    }
}