package com.stockvente.views;

import com.stockvente.dao.StatisticsDao;
import com.stockvente.utils.DatabaseConnect;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class StatistiquesMagasinierView extends JFrame {

    public StatistiquesMagasinierView() throws SQLException {
        setTitle("Tableau de Bord - Statistiques Magasinier");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Ajout d'un titre en haut
        JLabel titre = new JLabel("📊 Tableau de Bord du Magasinier", JLabel.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titre);

        StatisticsDao statisticsDao = new StatisticsDao(DatabaseConnect.getConnection());

        int totalProduits = statisticsDao.getTotalProduits();
        int produitsEnAlerte = statisticsDao.getProduitsEnAlerte();
        int approvisionnementsAujourdhui = statisticsDao.getApprovisionnementsRecents();
        int approvisionnementsMois = statisticsDao.getTotalApprovisionnementsCeMois();

        panel.add(new JLabel("🔹 Nombre total de produits : " + totalProduits));
        panel.add(new JLabel("🔹 Produits en alerte (stock bas) : " + produitsEnAlerte));
        panel.add(new JLabel("🔹 Approvisionnements aujourd’hui : " + approvisionnementsAujourdhui));
        panel.add(new JLabel("🔹 Approvisionnements ce mois : " + approvisionnementsMois));

        add(panel);
        setVisible(true); // Affiche la fenêtre
    }
}
