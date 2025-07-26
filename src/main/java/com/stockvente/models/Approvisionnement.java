package com.stockvente.models;

import java.util.Date;

public class Approvisionnement {
    private final int id_approvisionnement;
    private final int id_produit;
    private final int id_fournisseur;
    private int quantite_approvisionnement;
    private double prix_unitaire_achat;
    private Date date_approvisionnement;

    public Approvisionnement(int id_approvisionnement, int id_produit, int id_fournisseur, int quantite_approvisionnement, double prix_unitaire_achat, Date date_approvisionnement) {
        this.id_approvisionnement = id_approvisionnement;
        this.id_produit = id_produit;
        this.id_fournisseur = id_fournisseur;
        this.quantite_approvisionnement = quantite_approvisionnement;
        this.prix_unitaire_achat = prix_unitaire_achat;
        this.date_approvisionnement = date_approvisionnement;
    }

    public int getId_approvisionnement() { return id_approvisionnement; }
    public int getId_produit() { return id_produit; }
    public int getId_fournisseur() { return id_fournisseur; }
    public int getQuantite_approvisionnement() { return quantite_approvisionnement; }
    public void setQuantite_approvisionnement(int quantite_approvisionnement) { this.quantite_approvisionnement = quantite_approvisionnement; }
    public double getPrix_unitaire_achat() { return prix_unitaire_achat; }
    public void setPrix_unitaire_achat(double prix_unitaire_achat) { this.prix_unitaire_achat = prix_unitaire_achat; }
    public Date getDate_approvisionnement() { return date_approvisionnement; }
    public void setDate_approvisionnement(Date date_approvisionnement) { this.date_approvisionnement = date_approvisionnement; }
    public double getMontantTotalAchat() {
        return this.quantite_approvisionnement * this.prix_unitaire_achat;
    }
}