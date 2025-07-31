package com.stockvente.views;

import com.stockvente.controller.StockController;
import com.stockvente.controller.VendeurController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class VendeurDashboard extends JFrame {
    
    // Couleurs modernes
    private static final Color PRIMARY_COLOR = new Color(52, 73, 94);      // Bleu foncé
    private static final Color SECONDARY_COLOR = new Color(236, 240, 241); // Gris clair
    private static final Color ACCENT_COLOR = new Color(41, 128, 185);     // Bleu
    private static final Color SUCCESS_COLOR = new Color(39, 174, 96);     // Vert
    private static final Color DANGER_COLOR = new Color(231, 76, 60);      // Rouge
    private static final Color TEXT_COLOR = new Color(44, 62, 80);         // Gris foncé
    private static final Color WHITE = Color.WHITE;
    
    private VendeurController vendeurController;
    
    public VendeurDashboard() {
        initializeFrame();
        createComponents();
        setVisible(false);
    }
    
    private void initializeFrame() {
        setTitle("Tableau de bord - Vendeur");
        setSize(700, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(SECONDARY_COLOR);
        
        // Icône de l'application (optionnel)
        try {
            setIconImage(Toolkit.getDefaultToolkit().getImage("resources/icon.png"));
        } catch (Exception e) {
            // Icône par défaut si pas trouvée
        }
    }
    
    private void createComponents() {
        createHeader();
        createMainContent();
        createFooter();
    }
    
    private void createHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        
        // Titre principal
        JLabel titre = new JLabel("Dashboard Vendeur", SwingConstants.CENTER);
        titre.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titre.setForeground(WHITE);
        
        // Sous-titre
        JLabel sousTitre = new JLabel("Gestion des ventes et clients", SwingConstants.CENTER);
        sousTitre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sousTitre.setForeground(SECONDARY_COLOR);
        
        JPanel titleContainer = new JPanel(new BorderLayout());
        titleContainer.setBackground(PRIMARY_COLOR);
        titleContainer.add(titre, BorderLayout.CENTER);
        titleContainer.add(sousTitre, BorderLayout.SOUTH);
        
        headerPanel.add(titleContainer, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);
    }
    
    private void createMainContent() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(SECONDARY_COLOR);
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        
        // Panel des boutons avec GridBagLayout pour plus de contrôle
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(SECONDARY_COLOR);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        // Création des boutons stylisés
        JButton clientBtn = createStyledButton("👥 Gérer les Clients", ACCENT_COLOR);
        JButton venteBtn = createStyledButton("💰 Gérer les Ventes", SUCCESS_COLOR);
        JButton stockBtn = createStyledButton("📦 Consulter le Stock", new Color(155, 89, 182));
        JButton concernerBtn = createStyledButton("📋 Lignes de Vente", new Color(230, 126, 34));
        
        // Ajout des listeners
        clientBtn.addActionListener(e -> openClientView());
        venteBtn.addActionListener(e -> openVenteView());
        stockBtn.addActionListener(e -> openStockView());
        concernerBtn.addActionListener(e -> openConcernerView());
        
        // Positionnement des boutons
        gbc.gridx = 0; gbc.gridy = 0;
        buttonPanel.add(clientBtn, gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        buttonPanel.add(venteBtn, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        buttonPanel.add(stockBtn, gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        buttonPanel.add(concernerBtn, gbc);
        
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(WHITE);
        button.setBackground(backgroundColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(250, 80));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Effet hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            Color originalColor = backgroundColor;
            
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor.brighter());
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
        });
        
        return button;
    }
    
    private void createFooter() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(SECONDARY_COLOR);
        footerPanel.setBorder(new EmptyBorder(20, 30, 30, 30));
        
        JButton logoutBtn = createLogoutButton();
        footerPanel.add(logoutBtn);
        
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    private JButton createLogoutButton() {
        JButton logoutBtn = new JButton("🚪 Se Déconnecter");
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutBtn.setForeground(WHITE);
        logoutBtn.setBackground(DANGER_COLOR);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setPreferredSize(new Dimension(180, 40));
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Effet hover
        logoutBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutBtn.setBackground(DANGER_COLOR.brighter());
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutBtn.setBackground(DANGER_COLOR);
            }
        });
        
        logoutBtn.addActionListener(this::handleLogout);
        return logoutBtn;
    }
    
    // Méthodes d'action
    private void openClientView() {
        try {
            new ClientView("Vendeur").setVisible(true);
        } catch (Exception e) {
            showErrorMessage("Erreur lors de l'ouverture de la gestion des clients");
        }
    }
    
    private void openVenteView() {
        try {
            new VenteView("Vendeur").setVisible(true);
        } catch (Exception e) {
            showErrorMessage("Erreur lors de l'ouverture de la gestion des ventes");
        }
    }
    
    private void openStockView() {
        try {
            new StockConsultationView().setVisible(true);
        } catch (Exception e) {
            showErrorMessage("Erreur lors de l'ouverture de la consultation du stock");
        }
    }
    
    private void openConcernerView() {
        try {
            new ConcernerView("Vendeur").setVisible(true);
        } catch (Exception e) {
            showErrorMessage("Erreur lors de l'ouverture des lignes de vente");
        }
    }
    
    private void handleLogout(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Voulez-vous vraiment vous déconnecter ?",
            "Confirmation de déconnexion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            // Redirection vers LoginView si disponible
            // SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
        }
    }
    
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Erreur",
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    // Constructeurs
    public VendeurDashboard(VendeurController vendeurController) {
        this();
        this.vendeurController = vendeurController;
    }
    
  
}