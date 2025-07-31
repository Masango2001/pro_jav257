package com.stockvente.models;

public class Produit {
    private int id_produit; // ❗ enleve `final`
    private String nom_produit;
    private int id_categorie; // ❗ enleve `final`
    private String nom_categorie;
    private int quantite_stock;
    
    public Produit() {
        // maintenant autorisé
    }

    public Produit(int id_produit, String nom_produit, int id_categorie,String nom_categorie,int quantite_stock) {
        this.id_produit = id_produit;
        this.nom_produit = nom_produit;
        this.id_categorie = id_categorie;
        this. nom_categorie =nom_categorie;
        this. quantite_stock = quantite_stock;
    }

    // Getters et setters
    public int getId_produit() { return id_produit; }
    public void setId_produit(int id_produit) { this.id_produit = id_produit; }

    public String getNom_produit() { return nom_produit; }
    public void setNom_produit(String nom_produit) { this.nom_produit = nom_produit; }

    public int getId_categorie() { return id_categorie; }
    public void setId_categorie(int id_categorie) { this.id_categorie = id_categorie; }
    
    public String getNom_categorie() {
    return nom_categorie;
}

    public void setNom_categorie(String nom_categorie) {
        this.nom_categorie = nom_categorie;
    }

    public int getQuantite_stock() {
        return quantite_stock;
    }

    public void setQuantite_stock(int quantite_stock) {
        this.quantite_stock = quantite_stock;
    }

}
