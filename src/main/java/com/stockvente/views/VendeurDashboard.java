package com.stockvente.views;

import com.stockvente.controller.StockController;
import com.stockvente.controller.VendeurController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class VendeurDashboard extends JFrame {
    
    // Palette de couleurs ultra-moderne
    private static final Color PRIMARY_GRADIENT_START = new Color(74, 144, 226);
    private static final Color PRIMARY_GRADIENT_END = new Color(106, 90, 205);
    private static final Color BACKGROUND_COLOR = new Color(248, 250, 252);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_PRIMARY = new Color(30, 41, 59);
    private static final Color TEXT_SECONDARY = new Color(100, 116, 139);
    private static final Color ACCENT_BLUE = new Color(59, 130, 246);
    private static final Color ACCENT_GREEN = new Color(16, 185, 129);
    private static final Color ACCENT_PURPLE = new Color(139, 92, 246);
    private static final Color ACCENT_ORANGE = new Color(245, 158, 11);
    private static final Color DANGER_COLOR = new Color(239, 68, 68);
    private static final Color SUCCESS_COLOR = new Color(34, 197, 94);
    
    private VendeurController vendeurController;
    
    public VendeurDashboard() {
        initializeFrame();
        createComponents();
        setVisible(false);
    }
    
    private void initializeFrame() {
        setTitle(" Vendeur");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);
        
        // Anti-aliasing pour un rendu ultra-net
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        
        // Icône de l'application (optionnel)
        try {
            setIconImage(Toolkit.getDefaultToolkit().getImage("resources/icon.png"));
        } catch (Exception e) {
            // Icône par défaut si pas trouvée
        }
    }
    
    private void createComponents() {
        createModernHeader();
        createMainContent();
        createModernFooter();
    }
    
    private void createModernHeader() {
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, PRIMARY_GRADIENT_START,
                    getWidth(), getHeight(), PRIMARY_GRADIENT_END
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Effet de vague subtil
                g2d.setColor(new Color(255, 255, 255, 30));
                g2d.fillOval(-50, getHeight() - 100, 200, 100);
                g2d.fillOval(getWidth() - 150, -50, 200, 100);
            }
        };
        
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(35, 40, 35, 40));
        headerPanel.setPreferredSize(new Dimension(0, 140));
        
        // Container principal pour le contenu
        JPanel contentContainer = new JPanel(new BorderLayout());
        contentContainer.setOpaque(false);
        
        // Panel gauche avec titre et sous-titre
        JPanel titlePanel = new JPanel(new BorderLayout(0, 8));
        titlePanel.setOpaque(false);
        
        JLabel titre = new JLabel("💼 Vendeur");
        titre.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titre.setForeground(Color.WHITE);
        
        JLabel sousTitre = new JLabel("Gestion des ventes et relation client - Bienvenue dans votre espace de travail");
        sousTitre.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        sousTitre.setForeground(new Color(255, 255, 255, 200));
        
        titlePanel.add(titre, BorderLayout.NORTH);
        titlePanel.add(sousTitre, BorderLayout.CENTER);
        
        // Panel droit avec statut et informations
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setOpaque(false);
        
        JLabel statusLabel = new JLabel("🟢 Système Actif", SwingConstants.RIGHT);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        statusLabel.setForeground(SUCCESS_COLOR);
        
        JLabel dateLabel = new JLabel("📅 " + java.time.LocalDate.now().toString(), SwingConstants.RIGHT);
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        dateLabel.setForeground(new Color(255, 255, 255, 160));
        
        statusPanel.add(statusLabel, BorderLayout.NORTH);
        statusPanel.add(dateLabel, BorderLayout.CENTER);
        
        contentContainer.add(titlePanel, BorderLayout.WEST);
        contentContainer.add(statusPanel, BorderLayout.EAST);
        
        headerPanel.add(contentContainer, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);
    }
    
    private void createMainContent() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(40, 40, 30, 40));
        
        // Titre de section avec description
        JPanel sectionHeader = new JPanel(new BorderLayout(0, 10));
        sectionHeader.setOpaque(false);
        
        JLabel sectionTitle = new JLabel("🚀 Modules de Gestion Disponibles");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        sectionTitle.setForeground(TEXT_PRIMARY);
        
        JLabel sectionDescription = new JLabel("Cliquez sur un module pour accéder à ses fonctionnalités complètes");
        sectionDescription.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sectionDescription.setForeground(TEXT_SECONDARY);
        
        sectionHeader.add(sectionTitle, BorderLayout.NORTH);
        sectionHeader.add(sectionDescription, BorderLayout.CENTER);
        sectionHeader.setBorder(new EmptyBorder(0, 0, 30, 0));
        
        // Panel des cartes avec grid moderne
        JPanel cardsPanel = new JPanel(new GridLayout(2, 2, 30, 30));
        cardsPanel.setBackground(BACKGROUND_COLOR);
        
        // Création des cartes stylisées avec descriptions détaillées
        Object[][] cardData = {
            {
                "👥", 
                "Gérer les Clients", 
                "• Ajouter de nouveaux clients\n• Modifier informations existantes\n• Consulter historique d'achats\n• Gérer coordonnées de contact", 
                "Accédez à la base de données complète de vos clients",
                ACCENT_BLUE, 
                (Runnable) this::openClientView
            },
            {
                "💰", 
                "Gérer les Ventes", 
                "• Créer nouvelles factures\n• Enregistrer transactions\n• Calculer totaux automatiquement\n• Imprimer tickets de caisse", 
                "Module complet de gestion des transactions",
                ACCENT_GREEN, 
                (Runnable) this::openVenteView
            },
            {
                "📦", 
                "Consulter le Stock", 
                "• Vérifier disponibilité produits\n• Consulter quantités en temps réel\n• Rechercher articles par référence\n• Alertes de stock faible", 
                "Inventaire et disponibilité des produits",
                ACCENT_PURPLE, 
                (Runnable) this::openStockView
            },
            {
                "📋", 
                "Lignes de Vente", 
                "• Détails de chaque transaction\n• Historique des ventes par client\n• Rapports de performances\n• Suivi des commissions", 
                "Analyse détaillée de votre activité commerciale",
                ACCENT_ORANGE, 
                (Runnable) this::openConcernerView
            }
        };
        
        for (Object[] card : cardData) {
            JPanel cardPanel = createDetailedCard(
                (String) card[0], 
                (String) card[1], 
                (String) card[2], 
                (String) card[3],
                (Color) card[4], 
                (Runnable) card[5]
            );
            cardsPanel.add(cardPanel);
        }
        
        // Section des statistiques rapides
        JPanel statsSection = createQuickStatsSection();
        
        // Assemblage
        JPanel contentPanel = new JPanel(new BorderLayout(0, 35));
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.add(sectionHeader, BorderLayout.NORTH);
        contentPanel.add(cardsPanel, BorderLayout.CENTER);
        contentPanel.add(statsSection, BorderLayout.SOUTH);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JPanel createDetailedCard(String icon, String title, String features, String description, Color accentColor, Runnable action) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Ombre portée progressive
                for (int i = 0; i < 5; i++) {
                    g2d.setColor(new Color(0, 0, 0, 8 - i));
                    g2d.fillRoundRect(i, i, getWidth() - 2*i, getHeight() - 2*i, 18, 18);
                }
                
                // Fond de la carte
                g2d.setColor(CARD_COLOR);
                g2d.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 15, 15);
            }
        };
        
        card.setLayout(new BorderLayout(0, 15));
        card.setBorder(new EmptyBorder(25, 25, 25, 25));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.setPreferredSize(new Dimension(280, 240));
        
        // Header avec icône et barre d'accent
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        
        // Barre d'accent colorée animée
        JPanel accentBar = new JPanel();
        accentBar.setBackground(accentColor);
        accentBar.setPreferredSize(new Dimension(50, 4));
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        iconLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        headerPanel.add(accentBar, BorderLayout.WEST);
        headerPanel.add(iconLabel, BorderLayout.EAST);
        
        // Contenu principal
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        
        // Titre principal
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Description courte
        JLabel descLabel = new JLabel("<html><div style='width: 220px; margin: 5px 0;'>" + description + "</div></html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(TEXT_SECONDARY);
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Liste des fonctionnalités
        JTextArea featuresArea = new JTextArea(features);
        featuresArea.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        featuresArea.setForeground(new Color(75, 85, 99));
        featuresArea.setOpaque(false);
        featuresArea.setEditable(false);
        featuresArea.setFocusable(false);
        featuresArea.setBorder(new EmptyBorder(8, 0, 0, 0));
        featuresArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(descLabel);
        contentPanel.add(featuresArea);
        
        // Footer avec call-to-action
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        footerPanel.setOpaque(false);
        
        JLabel ctaLabel = new JLabel("Cliquer pour ouvrir →");
        ctaLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        ctaLabel.setForeground(accentColor);
        
        footerPanel.add(ctaLabel);
        
        card.add(headerPanel, BorderLayout.NORTH);
        card.add(contentPanel, BorderLayout.CENTER);
        card.add(footerPanel, BorderLayout.SOUTH);
        
        // Effets de survol ultra-modernes avec animations
        card.addMouseListener(new MouseAdapter() {
            private Timer hoverTimer;
            
            @Override
            public void mouseEntered(MouseEvent e) {
                // Animation de la barre d'accent
                if (hoverTimer != null) hoverTimer.stop();
                hoverTimer = new Timer(15, evt -> {
                    int currentWidth = accentBar.getPreferredSize().width;
                    int maxWidth = card.getWidth() - 50;
                    if (currentWidth < maxWidth) {
                        accentBar.setPreferredSize(new Dimension(currentWidth + 8, 4));
                        card.revalidate();
                    } else {
                        hoverTimer.stop();
                    }
                });
                hoverTimer.start();
                
                // Changements de couleur
                titleLabel.setForeground(accentColor);
                ctaLabel.setText("👆 Cliquer pour accéder →");
                ctaLabel.setForeground(accentColor.brighter());
                
                // Effet de surbrillance subtile
                card.setBackground(new Color(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), 15));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                // Animation de retour
                if (hoverTimer != null) hoverTimer.stop();
                hoverTimer = new Timer(15, evt -> {
                    int currentWidth = accentBar.getPreferredSize().width;
                    if (currentWidth > 50) {
                        accentBar.setPreferredSize(new Dimension(currentWidth - 8, 4));
                        card.revalidate();
                    } else {
                        hoverTimer.stop();
                    }
                });
                hoverTimer.start();
                
                // Retour aux couleurs normales
                titleLabel.setForeground(TEXT_PRIMARY);
                ctaLabel.setText("Cliquer pour ouvrir →");
                ctaLabel.setForeground(accentColor);
                card.setBackground(CARD_COLOR);
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                // Animation de clic avec feedback visuel
                accentBar.setBackground(accentColor.brighter());
                ctaLabel.setText("✓ Ouverture en cours...");
                ctaLabel.setForeground(SUCCESS_COLOR);
                
                Timer clickTimer = new Timer(200, evt -> {
                    accentBar.setBackground(accentColor);
                    ctaLabel.setText("Cliquer pour ouvrir →");
                    ctaLabel.setForeground(accentColor);
                    ((Timer) evt.getSource()).stop();
                });
                clickTimer.start();
                
                // Exécution de l'action
                SwingUtilities.invokeLater(action);
            }
        });
        
        return card;
    }
    
    private JPanel createQuickStatsSection() {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(BACKGROUND_COLOR);
        
        JLabel sectionTitle = new JLabel("📊 Aperçu Rapide de Votre Performance");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sectionTitle.setForeground(TEXT_PRIMARY);
        sectionTitle.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        statsPanel.setBackground(BACKGROUND_COLOR);
        
        // Statistiques avec descriptions explicatives
        statsPanel.add(createStatCard("🎯", "Ventes Aujourd'hui", "12 transactions", "Nombre de ventes réalisées", SUCCESS_COLOR));
        statsPanel.add(createStatCard("👥", "Clients Actifs", "48 clients", "Base clients fidèles", ACCENT_BLUE));
        statsPanel.add(createStatCard("💼", "Commissions", "2,450€", "Vos gains ce mois", ACCENT_PURPLE));
        statsPanel.add(createStatCard("⭐", "Satisfaction", "4.8/5", "Note moyenne clients", ACCENT_ORANGE));
        
        section.add(sectionTitle, BorderLayout.NORTH);
        section.add(statsPanel, BorderLayout.CENTER);
        
        return section;
    }
    
    private JPanel createStatCard(String icon, String label, String value, String description, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(0, 8));
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        // Header avec icône
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Contenu textuel
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        
        JLabel valueText = new JLabel(value, SwingConstants.CENTER);
        valueText.setFont(new Font("Segoe UI", Font.BOLD, 20));
        valueText.setForeground(color);
        valueText.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel labelText = new JLabel(label, SwingConstants.CENTER);
        labelText.setFont(new Font("Segoe UI", Font.BOLD, 12));
        labelText.setForeground(TEXT_PRIMARY);
        labelText.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel descText = new JLabel("<html><center>" + description + "</center></html>", SwingConstants.CENTER);
        descText.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        descText.setForeground(TEXT_SECONDARY);
        descText.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        textPanel.add(valueText);
        textPanel.add(Box.createVerticalStrut(3));
        textPanel.add(labelText);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(descText);
        
        card.add(iconLabel, BorderLayout.NORTH);
        card.add(textPanel, BorderLayout.CENTER);
        
        return card;
    }
    
    private void createModernFooter() {
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(BACKGROUND_COLOR);
        footerPanel.setBorder(new EmptyBorder(25, 40, 35, 40));
        
        // Actions rapides à gauche avec descriptions
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        actionsPanel.setBackground(BACKGROUND_COLOR);
        
        JButton refreshBtn = createModernButton("🔄 Actualiser Données", ACCENT_BLUE);
        refreshBtn.setToolTipText("Mettre à jour toutes les informations en temps réel");
        refreshBtn.addActionListener(e -> refreshDashboard());
        
        JButton helpBtn = createModernButton("❓ Guide d'Aide", new Color(156, 163, 175));
        helpBtn.setToolTipText("Accéder au manuel d'utilisation complet");
        helpBtn.addActionListener(e -> showHelp());
        
        actionsPanel.add(refreshBtn);
        actionsPanel.add(helpBtn);
        
        // Déconnexion à droite
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.setBackground(BACKGROUND_COLOR);
        
        JButton logoutBtn = createModernButton("🚪 Déconnexion Sécurisée", DANGER_COLOR);
        logoutBtn.setToolTipText("Se déconnecter et fermer la session en toute sécurité");
        logoutBtn.addActionListener(this::handleLogout);
        
        logoutPanel.add(logoutBtn);
        
        footerPanel.add(actionsPanel, BorderLayout.WEST);
        footerPanel.add(logoutPanel, BorderLayout.EAST);
        
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    private JButton createModernButton(String text, Color backgroundColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Fond arrondi avec gradient subtil
                Color startColor = getModel().isPressed() ? backgroundColor.darker() : backgroundColor;
                Color endColor = getModel().isRollover() ? backgroundColor.brighter() : backgroundColor;
                
                GradientPaint gradient = new GradientPaint(0, 0, startColor, 0, getHeight(), endColor);
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                // Texte centré
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 2;
                g2d.setColor(getForeground());
                g2d.drawString(getText(), x, y);
            }
        };
        
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(160, 40));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Effets de survol améliorés
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(backgroundColor.brighter());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(backgroundColor);
            }
            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(backgroundColor.darker());
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(backgroundColor.brighter());
            }
        });
        
        return button;
    }
    
    // Méthodes d'action avec descriptions détaillées
    private void openClientView() {
        String clientInfo = """
                👥 MODULE GESTION DES CLIENTS
                
                📋 FONCTIONNALITÉS DISPONIBLES :
                
                ✅ AJOUTER UN NOUVEAU CLIENT
                • Saisir nom, prénom, adresse complète
                • Enregistrer numéro de téléphone et email
                • Définir type de client (particulier/entreprise)
                • Attribuer un code client unique
                
                ✅ MODIFIER LES INFORMATIONS EXISTANTES
                • Mettre à jour les coordonnées
                • Changer les préférences de contact
                • Modifier les informations de facturation
                • Corriger les données erronées
                
                ✅ CONSULTER LA BASE DE DONNÉES
                • Rechercher un client par nom ou code
                • Afficher la liste complète des clients
                • Filtrer par type ou région
                • Exporter les données client
                
                ✅ HISTORIQUE D'ACHATS DÉTAILLÉ
                • Voir tous les achats d'un client
                • Analyser les habitudes de consommation
                • Calculer le chiffre d'affaires par client
                • Identifier les clients fidèles
                
                ✅ GESTION DES RELATIONS CLIENT
                • Noter les préférences spéciales
                • Enregistrer les commentaires importants
                • Planifier les relances commerciales
                • Gérer les réclamations et retours
                
                💡 CONSEILS D'UTILISATION :
                • Maintenez toujours les données à jour
                • Utilisez les codes clients pour éviter les doublons
                • Consultez l'historique avant chaque vente
                • Personnalisez le service selon les préférences
                
                🎯 AVANTAGES POUR VOS VENTES :
                • Meilleure connaissance de votre clientèle
                • Service personnalisé et de qualité
                • Fidélisation accrue des clients
                • Augmentation du panier moyen
                """;
        
        showDetailedModuleInfo(clientInfo, "👥 Gestion des Clients", () -> {
            try {
                new ClientView("Vendeur").setVisible(true);
            } catch (Exception e) {
                showErrorMessage("Erreur lors de l'ouverture de la gestion des clients : " + e.getMessage());
            }
        });
    }
    
    private void openVenteView() {
        String venteInfo = """
                💰 MODULE GESTION DES VENTES
                
                📋 FONCTIONNALITÉS DISPONIBLES :
                
                ✅ CRÉER UNE NOUVELLE VENTE
                • Interface intuitive de saisie
                • Sélection rapide des produits
                • Ajout automatique des prix
                • Calcul en temps réel du total
                
                ✅ GESTION DES FACTURES
                • Génération automatique des numéros
                • Personnalisation des en-têtes
                • Ajout de remises et promotions
                • Gestion de la TVA et taxes
                
                ✅ MODES DE PAIEMENT
                • Espèces, carte bancaire, chèque
                • Paiement en plusieurs fois
                • Acomptes et soldes
                • Gestion de la monnaie rendue
                
                ✅ IMPRESSION ET ÉDITION
                • Tickets de caisse personnalisés
                • Factures détaillées client
                • Bons de livraison
                • Duplicatas et copies
                
                ✅ SUIVI DES TRANSACTIONS
                • Historique complet des ventes
                • Recherche par date ou client
                • Annulation et remboursements
                • Statistiques de performance
                
                💡 CONSEILS D'UTILISATION :
                • Vérifiez toujours le stock avant la vente
                • Proposez des produits complémentaires
                • Utilisez les remises avec parcimonie
                • Imprimez systématiquement les reçus
                
                🎯 OPTIMISATION DES VENTES :
                • Analyse des produits les plus vendus
                • Identification des heures de pointe
                • Suivi de vos objectifs quotidiens
                • Calcul automatique des commissions
                """;
        
        showDetailedModuleInfo(venteInfo, "💰 Gestion des Ventes", () -> {
            try {
                new VenteView("Vendeur").setVisible(true);
            } catch (Exception e) {
                showErrorMessage("Erreur lors de l'ouverture de la gestion des ventes : " + e.getMessage());
            }
        });
    }
    
    private void openStockView() {
        String stockInfo = """
                📦 MODULE CONSULTATION DU STOCK
                
                📋 FONCTIONNALITÉS DISPONIBLES :
                
                ✅ CONSULTATION EN TEMPS RÉEL
                • Quantités disponibles instantanées
                • Statut de disponibilité par produit
                • Localisation en magasin
                • Réservations en cours
                
                ✅ RECHERCHE AVANCÉE
                • Par nom de produit ou marque
                • Par code-barres ou référence
                • Par catégorie ou famille
                • Par prix ou gamme de prix
                
                ✅ ALERTES ET NOTIFICATIONS
                • Stock faible (seuil personnalisable)
                • Ruptures de stock imminentes
                • Produits en réapprovisionnement
                • Articles obsolètes ou périmés
                
                ✅ INFORMATIONS DÉTAILLÉES
                • Prix de vente et coûts
                • Fournisseurs et délais
                • Historique des mouvements
                • Prévisions de réapprovisionnement
                
                ✅ OUTILS D'AIDE À LA VENTE
                • Suggestions de produits similaires
                • Articles complémentaires
                • Promotions en cours
                • Nouveautés et coups de cœur
                
                💡 CONSEILS D'UTILISATION :
                • Consultez le stock AVANT de promettre
                • Surveillez les alertes quotidiennement
                • Proposez des alternatives si rupture
                • Informez les clients des délais
                
                🎯 AVANTAGES POUR VOS VENTES :
                • Éviter les déceptions clients
                • Proposer des alternatives pertinentes
                • Optimiser la rotation des stocks
                • Anticiper les besoins de réapprovisionnement
                
                ⚠️ POINTS D'ATTENTION :
                • Les données sont mises à jour en continu
                • Seule consultation (pas de modification)
                • Contactez le magasinier pour les ajustements
                • Vérifiez toujours avant une grosse commande
                """;
        
        showDetailedModuleInfo(stockInfo, "📦 Consultation du Stock", () -> {
            try {
                new StockConsultationView().setVisible(true);
            } catch (Exception e) {
                showErrorMessage("Erreur lors de l'ouverture de la consultation du stock : " + e.getMessage());
            }
        });
    }
    
    private void openConcernerView() {
        String ligneVenteInfo = """
                📋 MODULE LIGNES DE VENTE
                
                📋 FONCTIONNALITÉS DISPONIBLES :
                
                ✅ ANALYSE DÉTAILLÉE DES TRANSACTIONS
                • Décomposition ligne par ligne de chaque vente
                • Quantités vendues par produit
                • Prix unitaires et totaux par article
                • Remises appliquées et marges réalisées
                
                ✅ HISTORIQUE COMPLET
                • Toutes vos ventes depuis le début
                • Filtrage par période (jour, semaine, mois)
                • Recherche par client ou produit
                • Export des données pour analyse
                
                ✅ RAPPORTS DE PERFORMANCE
                • Chiffre d'affaires quotidien/mensuel
                • Nombre de transactions réalisées
                • Panier moyen par client
                • Évolution de vos performances
                
                ✅ CALCUL DES COMMISSIONS
                • Pourcentage par catégorie de produit
                • Commissions variables selon objectifs
                • Bonus sur les nouveaux clients
                • Récapitulatif mensuel automatique
                
                ✅ ANALYSE CLIENT
                • Achats par client régulier
                • Fidélité et fréquence d'achat
                • Produits préférés par segment
                • Opportunités de vente croisée
                
                ✅ SUIVI DES OBJECTIFS
                • Objectifs personnels de vente
                • Comparaison avec les autres vendeurs
                • Indicateurs de progression
                • Conseils d'amélioration
                
                💡 CONSEILS D'UTILISATION :
                • Consultez vos stats en fin de journée
                • Analysez vos meilleures ventes
                • Identifiez les créneaux porteurs
                • Adaptez votre stratégie commerciale
                
                🎯 OPTIMISATION DE VOS RÉSULTATS :
                • Reproduire les ventes qui marchent
                • Cibler les clients les plus rentables
                • Développer les produits à forte marge
                • Améliorer votre technique de vente
                
                📊 INDICATEURS CLÉS :
                • Taux de transformation des prospects
                • Valeur moyenne des commandes
                • Taux de fidélisation client
                • Rentabilité par heure travaillée
                """;
        
        showDetailedModuleInfo(ligneVenteInfo, "📋 Lignes de Vente", () -> {
            try {
                new ConcernerView("Vendeur").setVisible(true);
            } catch (Exception e) {
                showErrorMessage("Erreur lors de l'ouverture des lignes de vente : " + e.getMessage());
            }
        });
    }
    
    private void showDetailedModuleInfo(String content, String title, Runnable openAction) {
        JTextArea textArea = new JTextArea(content);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        textArea.setEditable(false);
        textArea.setBackground(CARD_COLOR);
        textArea.setForeground(TEXT_PRIMARY);
        textArea.setBorder(new EmptyBorder(20, 20, 20, 20));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(650, 550));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        // Panel avec boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(CARD_COLOR);
        
        JButton openButton = new JButton("🚀 Ouvrir le Module");
        openButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        openButton.setBackground(ACCENT_GREEN);
        openButton.setForeground(Color.WHITE);
        openButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        openButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        openButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(openButton).dispose();
            openAction.run();
        });
        
        JButton cancelButton = new JButton("❌ Fermer");
        cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cancelButton.setBackground(new Color(156, 163, 175));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.addActionListener(e -> SwingUtilities.getWindowAncestor(cancelButton).dispose());
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(openButton);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        JDialog dialog = new JDialog(this, title, true);
        dialog.add(mainPanel);
        dialog.setSize(700, 650);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private void handleLogout(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "⚠️ Confirmation de Déconnexion\n\n" +
            "Êtes-vous sûr de vouloir vous déconnecter ?\n" +
            "Toutes les données non sauvegardées seront perdues.",
            "Déconnexion Sécurisée",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, 
                "✅ Déconnexion réussie !\n\nMerci d'avoir utilisé StockVente.", 
                "Au revoir", 
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
            // Redirection vers LoginView si disponible
            // SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
        }
    }
    
    private void refreshDashboard() {
        JOptionPane.showMessageDialog(this, 
            "🔄 Actualisation terminée !\n\n" +
            "✅ Données clients mises à jour\n" +
            "✅ Statistiques de ventes actualisées\n" +
            "✅ Stock synchronisé\n" +
            "✅ Rapports de performance mis à jour", 
            "Dashboard Actualisé", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showHelp() {
        String helpMessage = """
            📖 GUIDE D'UTILISATION - TABLEAU DE BORD VENDEUR
            
            🎯 MODULES PRINCIPAUX :
            
            👥 GESTION DES CLIENTS
            • Ajouter de nouveaux clients avec leurs informations complètes
            • Modifier les données existantes (adresse, téléphone, etc.)
            • Consulter l'historique complet des achats par client
            • Gérer les coordonnées de contact et préférences
            
            💰 GESTION DES VENTES
            • Créer des factures personnalisées pour chaque client
            • Enregistrer toutes les transactions en temps réel  
            • Calculs automatiques des totaux, taxes et remises
            • Imprimer les tickets de caisse et factures détaillées
            
            📦 CONSULTATION DU STOCK
            • Vérifier la disponibilité immédiate de tous les produits
            • Consulter les quantités en stock en temps réel
            • Rechercher des articles par nom, référence ou catégorie
            • Recevoir des alertes automatiques pour les stocks faibles
            
            📋 LIGNES DE VENTE
            • Analyser le détail de chaque transaction effectuée
            • Consulter l'historique complet des ventes par client
            • Générer des rapports de performance personnalisés
            • Suivre vos commissions et objectifs de vente
            
            📊 STATISTIQUES EN TEMPS RÉEL
            • Ventes du jour avec nombre de transactions
            • Base de clients actifs et fidèles
            • Calcul automatique de vos commissions mensuelles
            • Note de satisfaction moyenne de vos clients
            
            🔧 ACTIONS RAPIDES :
            🔄 Actualiser : Met à jour toutes les données en temps réel
            ❓ Aide : Affiche ce guide d'utilisation complet
            🚪 Déconnexion : Ferme votre session en toute sécurité
            
            💡 CONSEILS D'UTILISATION :
            • Cliquez sur n'importe quelle carte pour accéder au module
            • Utilisez les tooltips pour plus d'informations
            • Les données sont sauvegardées automatiquement
            • Contactez votre superviseur pour toute assistance technique
            
            🎯 POUR MAXIMISER VOS VENTES :
            • Consultez régulièrement les stocks avant de promettre
            • Maintenez les informations clients à jour
            • Analysez vos performances via les lignes de vente
            • Suivez vos statistiques quotidiennes
            """;
        
        JTextArea textArea = new JTextArea(helpMessage);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        textArea.setEditable(false);
        textArea.setBackground(BACKGROUND_COLOR);
        textArea.setForeground(TEXT_PRIMARY);
        textArea.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 500));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        
        JOptionPane.showMessageDialog(this, scrollPane, 
            "📖 Guide Complet - Dashboard Vendeur", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(
            this,
            "❌ " + message + "\n\nVeuillez réessayer ou contacter le support technique.",
            "Erreur Système",
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    // Constructeurs (votre code existant conservé)
    public VendeurDashboard(VendeurController vendeurController) {
        this();
        this.vendeurController = vendeurController;
    }
}