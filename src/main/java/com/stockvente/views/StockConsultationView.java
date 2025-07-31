package com.stockvente.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import com.stockvente.dao.StockDao;
import com.stockvente.models.Produit;

public class StockConsultationView extends JFrame {

    public StockConsultationView() {
        setTitle("Consultation des Stocks");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JTable tableStocks = new JTable();
        JScrollPane scrollPane = new JScrollPane(tableStocks);
        add(scrollPane, BorderLayout.CENTER);

        String[] colonnes = {"ID Produit", "Nom Produit", "Quantité en Stock","categorie"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);

        StockDao stockDao = new StockDao();
        List<Produit> produits = stockDao.getAllProduitsAvecStock();

        // Pour stocker les alertes
        StringBuilder alertes = new StringBuilder();

        for (Produit p : produits) {
            int quantite = stockDao.getQuantiteStockParProduit(p.getId_produit());

            // Ajouter à la table
            model.addRow(new Object[]{
                p.getId_produit(), p.getNom_produit(), p.getQuantite_stock(),p.getNom_categorie()
            });

            // Vérifier si la quantité est faible (< 5)
            if (quantite < 5) {
                alertes.append("- ").append(p.getNom_produit())
                       .append(" (stock: ").append(quantite).append(")\n");
            }
        }

        tableStocks.setModel(model);

        // Affichage du popup s'il y a au moins un stock faible
        if (alertes.length() > 0) {
            JOptionPane.showMessageDialog(this,
                    "⚠️ Attention, les produits suivants ont un stock faible :\n\n" + alertes,
                    "Alerte Stock Faible",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
