package com.stockvente.models;

import java.util.Date;

public class Stock {
    private final int id_stock;
    private final int id_produit;
    private int quantite_stock;
    private Date date_misejour;

    public Stock(int id_stock, int id_produit, int quantite_stock, Date date_misejour) {
        this.id_stock = id_stock;
        this.id_produit = id_produit;
        this.quantite_stock = quantite_stock;
        this.date_misejour = date_misejour;
    }

    public int getId_stock() { return id_stock; }
    public int getId_produit() { return id_produit; }
    public int getQuantite_stock() { return quantite_stock; }
    public void setQuantite_stock(int quantite_stock) { this.quantite_stock = quantite_stock; }
    public Date getDate_misejour() { return date_misejour; }
    public void setDate_misejour(Date date_misejour) { this.date_misejour = date_misejour; }
}