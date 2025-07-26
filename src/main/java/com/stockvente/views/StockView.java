package com.stockvente.views;

import com.stockvente.controller.AdminController;
import com.stockvente.controller.StockController;
import com.stockvente.models.Stock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StockView extends JFrame {
    private StockController stockController;
    private JTextArea textArea;
    private JTextField idField;
    private JTextField idProdField;
    private JTextField quantiteField;
    private JTextField dateField;
    
    

    public StockView(String role, StockController stockController) {
        this(stockController);
        setTitle("Gestion des Stocks - " + role);
    }
    
    public StockView(StockController stockController) {
        this.stockController = stockController;
        
 

        // Set up the frame
        setTitle("Gestion des Stocks");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Text area to display stocks
        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Panel for input fields and buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        panel.add(new JLabel("ID Stock:"));
        idField = new JTextField();
        panel.add(idField);

        panel.add(new JLabel("ID Produit:"));
        idProdField = new JTextField();
        panel.add(idProdField);

        panel.add(new JLabel("Quantité:"));
        quantiteField = new JTextField();
        panel.add(quantiteField);

        panel.add(new JLabel("Date de mise à jour (dd/MM/yyyy):"));
        dateField = new JTextField();
        panel.add(dateField);

        // Buttons
        JButton addButton = new JButton("Ajouter");
        JButton updateButton = new JButton("Mettre à jour");
        JButton deleteButton = new JButton("Supprimer");
        JButton viewButton = new JButton("Afficher Tous");

        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(viewButton);

        add(panel, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int idProduit = Integer.parseInt(idProdField.getText());
                    int quantite = Integer.parseInt(quantiteField.getText());
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date dateMiseJour = sdf.parse(dateField.getText());
                    Stock stock = new Stock(0, idProduit, quantite, dateMiseJour);
                    String result = stockController.ajouterStock("Magasinier", stock);
                    JOptionPane.showMessageDialog(null, result);
                    clearFields();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erreur d'entrée: " + ex.getMessage());
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int idStock = Integer.parseInt(idField.getText());
                    int idProduit = Integer.parseInt(idProdField.getText());
                    int quantite = Integer.parseInt(quantiteField.getText());
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date dateMiseJour = sdf.parse(dateField.getText());
                    Stock stock = new Stock(idStock, idProduit, quantite, dateMiseJour);
                    String result = stockController.mettreAJourStock("Magasinier", stock);
                    JOptionPane.showMessageDialog(null, result);
                    clearFields();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erreur d'entrée: " + ex.getMessage());
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int idStock = Integer.parseInt(idField.getText());
                    String result = stockController.supprimerStock("Admin", idStock);
                    JOptionPane.showMessageDialog(null, result);
                    clearFields();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erreur d'entrée: " + ex.getMessage());
                }
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = stockController.afficherTousLesStocks("Vendeur");
                textArea.setText(result);
            }
        });

        setVisible(true);
    }


    private void clearFields() {
        idField.setText("");
        idProdField.setText("");
        quantiteField.setText("");
        dateField.setText("");
    }

    public static void main(String[] args) {
        StockController controller = new StockController();
        new StockView(controller);
    }
}