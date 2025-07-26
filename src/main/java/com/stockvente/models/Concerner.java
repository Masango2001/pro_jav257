package com.stockvente.models;

public class Concerner {
    private final int id_vente;
    private final int id_produit;
    private int quantite_vendue;
    private double prix_unitaire_vendue;

    public Concerner(int id_vente, int id_produit, int quantite_vendue, double prix_unitaire_vendue) {
        this.id_vente = id_vente;
        this.id_produit = id_produit;
        this.quantite_vendue = quantite_vendue;
        this.prix_unitaire_vendue = prix_unitaire_vendue;
    }

    public int getId_vente() { return id_vente; }
    public int getId_produit() { return id_produit; }
    public int getQuantite_vendue() { return quantite_vendue; }
    public void setQuantite_vendue(int quantite_vendue) { this.quantite_vendue = quantite_vendue; }
    public double getPrix_unitaire_vendue() { return prix_unitaire_vendue; }
    public void setPrix_unitaire_vendue(double prix_unitaire_vendue) { this.prix_unitaire_vendue = prix_unitaire_vendue; }
    public double getMontantTotalVente() {
        return this.quantite_vendue * this.prix_unitaire_vendue;
    }
}