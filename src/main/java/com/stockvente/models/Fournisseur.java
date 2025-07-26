package com.stockvente.models;

public class Fournisseur {
    private final int id_fournisseur;
    private String nom_complet_fournisseur;
    private String adresse_fournisseur;
    private String email_fournisseur;
    private String telephone_fournisseur;

    public Fournisseur(int id_fournisseur, String nom_complet_fournisseur, String adresse_fournisseur, String email_fournisseur, String telephone_fournisseur) {
        this.id_fournisseur = id_fournisseur;
        this.nom_complet_fournisseur = nom_complet_fournisseur;
        this.adresse_fournisseur = adresse_fournisseur;
        this.email_fournisseur = email_fournisseur;
        this.telephone_fournisseur = telephone_fournisseur;
    }

    public int getId_fournisseur() { return id_fournisseur; }
    public String getNom_complet_fournisseur() { return nom_complet_fournisseur; }
    public void setNom_complet_fournisseur(String nom_complet_fournisseur) { this.nom_complet_fournisseur = nom_complet_fournisseur; }
    public String getAdresse_fournisseur() { return adresse_fournisseur; }
    public void setAdresse_fournisseur(String adresse_fournisseur) { this.adresse_fournisseur = adresse_fournisseur; }
    public String getEmail_fournisseur() { return email_fournisseur; }
    public void setEmail_fournisseur(String email_fournisseur) { this.email_fournisseur = email_fournisseur; }
    public String getTelephone_fournisseur() { return telephone_fournisseur; }
    public void setTelephone_fournisseur(String telephone_fournisseur) { this.telephone_fournisseur = telephone_fournisseur; }
}