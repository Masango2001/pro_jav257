package com.stockvente.models;

public class Produit {
    private final int id_produit;
    private String nom_produit;
    private final int id_categorie;

    public Produit(int id_produit, String nom_produit, int id_categorie) {
        this.id_produit = id_produit;
        this.nom_produit = nom_produit;
        this.id_categorie = id_categorie;
    }

    public Produit() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Getters et setters
    public int getId_produit() { return id_produit; }
    public String getNom_produit() { return nom_produit; }
    public void setNom_produit(String nom_produit) { this.nom_produit = nom_produit; }
    public int getId_categorie() { return id_categorie; }

    public void setId_categorie(int id_categorie) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setId_produit(int id_produit) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}