package com.stockvente.views;

import com.stockvente.controller.AdminController;
import com.stockvente.controller.ProduitController;
import com.stockvente.models.Produit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProduitView extends JFrame {
    private ProduitController produitController;
    private JTextArea textArea;
    private JTextField nomField;
    private JTextField idCatField;
    private JTextField idField;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton viewButton;

    public ProduitView(ProduitController produitController) {
        this.produitController = produitController;

        // Set up the frame
        setTitle("Gestion des Produits");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Text area to display products
        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Panel for input fields and buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        panel.add(new JLabel("ID Produit:"));
        idField = new JTextField();
        panel.add(idField);

        panel.add(new JLabel("Nom Produit:"));
        nomField = new JTextField();
        panel.add(nomField);

        panel.add(new JLabel("ID Catégorie:"));
        idCatField = new JTextField();
        panel.add(idCatField);

        // Buttons
        addButton = new JButton("Ajouter");
        updateButton = new JButton("Mettre à jour");
        deleteButton = new JButton("Supprimer");
        viewButton = new JButton("Afficher Tous");

        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(viewButton);

        add(panel, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = nomField.getText();
                int idCat = Integer.parseInt(idCatField.getText());
                Produit produit = new Produit(0, nom, idCat); // ID will be auto-generated
                String result = produitController.ajouterProduit("Magasinier", produit);
                JOptionPane.showMessageDialog(null, result);
                nomField.setText("");
                idCatField.setText("");
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());
                String nom = nomField.getText();
                int idCat = Integer.parseInt(idCatField.getText());
                Produit produit = new Produit(id, nom, idCat);
                String result = produitController.mettreAJourProduit("Magasinier", produit);
                JOptionPane.showMessageDialog(null, result);
                nomField.setText("");
                idCatField.setText("");
                idField.setText("");
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());
                String result = produitController.supprimerProduit("Magasinier", id);
                JOptionPane.showMessageDialog(null, result);
                idField.setText("");
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = produitController.afficherTousLesProduits("Vendeur");
                textArea.setText(result);
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        ProduitController controller = new ProduitController();
        new ProduitView(controller);
    }

//    ProduitView() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//    ProduitView(AdminController adminController) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
}