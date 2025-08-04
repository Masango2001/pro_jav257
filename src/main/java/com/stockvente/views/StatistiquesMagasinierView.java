package com.stockvente.views;

import com.stockvente.dao.StatisticsDao;
import com.stockvente.utils.DatabaseConnect;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import javax.swing.border.TitledBorder;

public class StatistiquesMagasinierView extends JFrame {

    public StatistiquesMagasinierView() throws SQLException {
        setTitle("StockVente - Statistiques Magasinier");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 240));
        add(mainPanel, BorderLayout.CENTER);

        // Titre principal
        JLabel title = new JLabel("📊 Tableau de Bord du Magasinier", SwingConstants.CENTER);
        title.setFont(new Font("Bell Mt", Font.BOLD, 26));
        title.setForeground(new Color(33, 150, 243));
        mainPanel.add(title, BorderLayout.NORTH);

        // Bloc Statistiques
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(4, 1, 10, 20));
        statsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(33, 150, 243), 2),
                "Statistiques Générales",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Bell Mt", Font.BOLD, 18),
                new Color(33, 150, 243)
        ));
        statsPanel.setBackground(Color.WHITE);
        Font font = new Font("Bell Mt", Font.PLAIN, 18);

        StatisticsDao statisticsDao = new StatisticsDao(DatabaseConnect.getConnection());

        int totalProduits = statisticsDao.getTotalProduits();
        int produitsEnAlerte = statisticsDao.getProduitsEnAlerte();
        int approvisionnementsAujourdhui = statisticsDao.getApprovisionnementsRecents();
        int approvisionnementsMois = statisticsDao.getTotalApprovisionnementsCeMois();

        JLabel lbl1 = new JLabel("🔹 Nombre total de produits : " + totalProduits);
        JLabel lbl2 = new JLabel("🔹 Produits en alerte (stock bas) : " + produitsEnAlerte);
        JLabel lbl3 = new JLabel("🔹 Approvisionnements aujourd’hui : " + approvisionnementsAujourdhui);
        JLabel lbl4 = new JLabel("🔹 Approvisionnements ce mois : " + approvisionnementsMois);

        for (JLabel lbl : new JLabel[]{lbl1, lbl2, lbl3, lbl4}) {
            lbl.setFont(font);
            statsPanel.add(lbl);
        }

        mainPanel.add(statsPanel, BorderLayout.CENTER);

        // Bouton retour
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnRetour = new JButton("<- Retour");
        btnRetour.setFont(new Font("Bell Mt", Font.BOLD, 16));
        btnRetour.setBackground(new Color(33, 150, 243));
        btnRetour.setForeground(Color.WHITE);
        btnRetour.setPreferredSize(new Dimension(120, 35));
        btnRetour.addActionListener(e -> dispose());
        bottomPanel.add(btnRetour);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }
}
