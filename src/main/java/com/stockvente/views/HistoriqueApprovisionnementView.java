package com.stockvente.views;

import com.stockvente.controller.ApprovisionnementController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class HistoriqueApprovisionnementView extends JFrame {

    private JTextArea textArea;
    private ApprovisionnementController controller;

    public HistoriqueApprovisionnementView() {
        setTitle("Historique des approvisionnements");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fermer cette fenêtre sans quitter l'appli

        controller = new ApprovisionnementController();

        // Zone de texte pour afficher les données
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Bouton Actualiser
        JButton btnActualiser = new JButton("Actualiser");
        btnActualiser.addActionListener(this::actualiserHistorique);

        // Layout
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnActualiser, BorderLayout.SOUTH);

        add(panel);

        // Affichage initial
        chargerHistorique();
    }

    private void chargerHistorique() {
        List<String> historique = controller.getHistoriqueApprovisionnements();
        textArea.setText(""); // Vider avant de remplir
        for (String ligne : historique) {
            textArea.append(ligne + "\n");
        }
    }

    private void actualiserHistorique(ActionEvent e) {
        chargerHistorique(); // Recharge les données depuis la base
    }
}
