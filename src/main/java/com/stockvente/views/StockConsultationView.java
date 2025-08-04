package com.stockvente.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import com.stockvente.dao.StockDao;
import com.stockvente.models.Produit;
import com.stockvente.utils.DatabaseConnect;
import java.sql.Connection;
import java.sql.SQLException;

public class StockConsultationView extends JFrame {

    public StockConsultationView() throws SQLException {
        setTitle("Consultation des Stocks");
        setSize(800, 500);  // largeur plus grande pour meilleure lisibilité
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Panel principal avec BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tableau des stocks
        JTable tableStocks = new JTable();
        JScrollPane scrollPane = new JScrollPane(tableStocks);

        String[] colonnes = {"ID Produit", "Nom Produit", "Quantité en Stock", "Catégorie"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);

        StockDao stockDao = new StockDao();
        List<Produit> produits = stockDao.getAllProduitsAvecStock();

        StringBuilder alertes = new StringBuilder();
        Connection conn = DatabaseConnect.getConnection();

        for (Produit p : produits) {
            int quantite = stockDao.getQuantiteStockParProduit(p.getId_produit(), conn);
            model.addRow(new Object[]{
                p.getId_produit(), p.getNom_produit(), p.getQuantite_stock(), p.getNom_categorie()
            });

            if (quantite < 5) {
                alertes.append("- ").append(p.getNom_produit())
                        .append(" (stock: ").append(quantite).append(")\n");
            }
        }

        tableStocks.setModel(model);

        // Bouton de retour
        JButton btnRetour = new JButton("← Retour");
        btnRetour.setFocusPainted(false);
        btnRetour.setPreferredSize(new Dimension(100, 30));
        btnRetour.addActionListener(e -> dispose());  // ferme la fenêtre actuelle

        // Panel inférieur pour bouton
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(btnRetour);

        // Ajout dans le panel principal
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Ajout du panel principal à la JFrame
        add(mainPanel);

        // Affichage popup stock faible
        if (alertes.length() > 0) {
            JOptionPane.showMessageDialog(this,
                    "⚠️ Attention, les produits suivants ont un stock faible :\n\n" + alertes,
                    "Alerte Stock Faible",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
