package com.stockvente.views;

import com.stockvente.views.LoginView;
import com.stockvente.controller.MagasinierController;
import com.stockvente.controller.ProduitController;
import com.stockvente.controller.StockController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MagasinierDashboard extends JFrame {
    private MagasinierController magasinierController;
    
    // Couleurs modernes pour le design
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color ACCENT_COLOR = new Color(155, 89, 182);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color WARNING_COLOR = new Color(230, 126, 34);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_PRIMARY = new Color(44, 62, 80);
    private static final Color TEXT_SECONDARY = new Color(127, 140, 141);

    public MagasinierDashboard() {
        initModernComponents();
    }

    // Ajouter ce constructeur dans MagasinierDashboard
    public MagasinierDashboard(MagasinierController magasinierController) {
        this.magasinierController = magasinierController;
        initModernComponents();
    }

    private void initModernComponents() {
        // Configuration de base
        setTitle("Magasinier");
        setSize(900, 700);
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

        // Footer avec informations
        add(createFooter(), BorderLayout.SOUTH);
    }

    private JPanel createModernHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(new EmptyBorder(25, 35, 25, 35));

        // Titre principal avec icône
        JLabel titleLabel = new JLabel("📦 Magasinier");
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);

        // Sous-titre
        JLabel subtitleLabel = new JLabel("Gérez vos stocks, produits et approvisionnements efficacement");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setForeground(new Color(255, 255, 255, 180));

        JPanel textPanel = new JPanel(new BorderLayout(0, 8));
        textPanel.setOpaque(false);
        textPanel.add(titleLabel, BorderLayout.NORTH);
        textPanel.add(subtitleLabel, BorderLayout.CENTER);

        // Indicateur de statut
        JLabel statusIndicator = new JLabel("🟢 En ligne");
        statusIndicator.setFont(new Font("Segoe UI", Font.BOLD, 12));
        statusIndicator.setForeground(SUCCESS_COLOR);
        statusIndicator.setHorizontalAlignment(SwingConstants.RIGHT);

        headerPanel.add(textPanel, BorderLayout.CENTER);
        headerPanel.add(statusIndicator, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createMainContentPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(30, 35, 30, 35));

        // Section principale avec les cartes
        JPanel cardsSection = createCardsSection();
        
        // Section des actions rapides
        JPanel quickActionsSection = createQuickActionsSection();

        // Assemblage
        JPanel contentPanel = new JPanel(new BorderLayout(0, 25));
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.add(cardsSection, BorderLayout.CENTER);
        contentPanel.add(quickActionsSection, BorderLayout.SOUTH);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel createCardsSection() {
        JPanel section = new JPanel(new GridLayout(2, 3, 20, 20));
        section.setBackground(BACKGROUND_COLOR);

        // Données des cartes (icône, titre, description, couleur, action)
        Object[][] cardData = {
            {"📦", "Gérer les Produits", "Ajouter, modifier et consulter les produits", PRIMARY_COLOR, (Runnable) this::openProduitView},
            {"🚚", "Gérer les Approvisionnements", "Suivi des commandes et livraisons", SECONDARY_COLOR, (Runnable) this::openApprovisionnementView},
            {"📊", "Gérer les Stocks", "Contrôle des niveaux de stock", SUCCESS_COLOR, (Runnable) this::openStockView},
            {"🏷️", "Gérer les Catégories", "Organisation par catégories", ACCENT_COLOR, (Runnable) this::openCategorieView},
            {"🏢", "Gérer les Fournisseurs", "Base de données fournisseurs", WARNING_COLOR, (Runnable) this::openFournisseurView},
            {"📈", "Voir Tableau de Bord", "Statistiques et rapports", new Color(52, 73, 94), (Runnable) this::openStatsView}
        };

        for (Object[] card : cardData) {
            JPanel cardPanel = createModernCard(
                (String) card[0], 
                (String) card[1], 
                (String) card[2], 
                (Color) card[3], 
                (Runnable) card[4]
            );
            section.add(cardPanel);
        }

        return section;
    }

    private JPanel createModernCard(String icon, String title, String description, Color accentColor, Runnable action) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(0, 12));
        card.setBackground(CARD_COLOR);
        card.setBorder(new EmptyBorder(25, 20, 25, 20));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Bordure avec effet d'ombre
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 15), 1),
            new EmptyBorder(24, 19, 24, 19)
        ));

        // Header de la carte avec icône et accent coloré
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        iconLabel.setHorizontalAlignment(SwingConstants.LEFT);

        // Barre d'accent colorée
        JPanel accentBar = new JPanel();
        accentBar.setBackground(accentColor);
        accentBar.setPreferredSize(new Dimension(4, 35));

        headerPanel.add(accentBar, BorderLayout.WEST);
        headerPanel.add(Box.createHorizontalStrut(15), BorderLayout.CENTER);
        headerPanel.add(iconLabel, BorderLayout.EAST);

        // Titre
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(TEXT_PRIMARY);

        // Description
        JLabel descLabel = new JLabel("<html>" + description + "</html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(TEXT_SECONDARY);

        // Panel pour titre et description
        JPanel textPanel = new JPanel(new BorderLayout(0, 6));
        textPanel.setOpaque(false);
        textPanel.add(titleLabel, BorderLayout.NORTH);
        textPanel.add(descLabel, BorderLayout.CENTER);

        card.add(headerPanel, BorderLayout.NORTH);
        card.add(textPanel, BorderLayout.CENTER);

        // Effets de survol
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(248, 249, 250));
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(accentColor, 2),
                    new EmptyBorder(23, 18, 23, 18)
                ));
                // Effet de "lift"
                card.setLocation(card.getX(), card.getY() - 2);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(CARD_COLOR);
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0, 0, 0, 15), 1),
                    new EmptyBorder(24, 19, 24, 19)
                ));
                // Retour à la position normale
                card.setLocation(card.getX(), card.getY() + 2);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Animation de clic
                card.setBackground(accentColor.brighter());
                Timer timer = new Timer(100, evt -> {
                    card.setBackground(CARD_COLOR);
                    ((Timer) evt.getSource()).stop();
                });
                timer.start();
                action.run();
            }
        });

        return card;
    }

    private JPanel createQuickActionsSection() {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(BACKGROUND_COLOR);

        JLabel sectionTitle = new JLabel("⚡ Actions Rapides");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sectionTitle.setForeground(TEXT_PRIMARY);
        sectionTitle.setBorder(new EmptyBorder(0, 0, 15, 0));

        // Panel des boutons d'action
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        actionsPanel.setBackground(BACKGROUND_COLOR);

        // Bouton de déconnexion moderne
        JButton btnDeconnecter = createModernButton("🚪 Se Déconnecter", DANGER_COLOR);
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
        button.setBorder(new EmptyBorder(12, 20, 12, 20));
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

    private JPanel createFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(52, 73, 94));
        footer.setBorder(new EmptyBorder(15, 35, 15, 35));

        JLabel footerText = new JLabel("StockVente © 2025 - Système de Gestion de Stock");
        footerText.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerText.setForeground(new Color(189, 195, 199));

        JLabel versionText = new JLabel("v1.0");
        versionText.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        versionText.setForeground(new Color(189, 195, 199));

        footer.add(footerText, BorderLayout.WEST);
        footer.add(versionText, BorderLayout.EAST);

        return footer;
    }

    // Méthodes d'action (votre code existant conservé)
    private void openProduitView() {
        ProduitController produitController = new ProduitController();
        new ProduitView(produitController).setVisible(true);
    }

    private void openApprovisionnementView() {
        new ApprovisionnementView().setVisible(true);
    }

    private void openStockView() {
        StockController stockController = new StockController();
        new StockView(stockController).setVisible(true);
    }

    private void openCategorieView() {
        new CategorieView().setVisible(true);
    }

    private void openFournisseurView() {
        new FournisseurView().setVisible(true);
    }

    private void openStatsView() {
        try {
            new StatistiquesMagasinierView().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(MagasinierDashboard.class.getName()).log(Level.SEVERE, null, ex);
            showModernErrorDialog("Erreur lors de l'ouverture des statistiques : " + ex.getMessage());
        }
    }

    private void handleLogout() {
        // Dialogue de confirmation moderne
        int result = JOptionPane.showConfirmDialog(
            this,
            "Voulez-vous vraiment vous déconnecter ?",
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
        // Animation de rafraîchissement
        JOptionPane.showMessageDialog(this, 
            "Tableau de bord actualisé avec succès !", 
            "Actualisation", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void showHelp() {
        String helpMessage = """
            🔧 AIDE - TABLEAU DE BORD MAGASINIER
            
            📦 Gérer les Produits : Ajouter, modifier, supprimer des produits
            🚚 Gérer les Approvisionnements : Suivi des commandes
            📊 Gérer les Stocks : Contrôle des niveaux de stock
            🏷️ Gérer les Catégories : Organisation des produits
            🏢 Gérer les Fournisseurs : Base de données
            📈 Tableau de Bord : Statistiques et rapports
            
            Pour plus d'aide, contactez l'administrateur système.
            """;
        
        JOptionPane.showMessageDialog(this, helpMessage, "Aide", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showModernErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}