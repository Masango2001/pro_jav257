package com.stockvente.models;

import java.util.Date;


public class Vente {
    private final int id_vente;
    private Date date_vente;
    private final int id_utilisateur;
    private final int id_client;

    public Vente(int id_vente, Date date_vente, int id_utilisateur, int id_client) {
        this.id_vente = id_vente;
        this.date_vente = date_vente;
        this.id_utilisateur = id_utilisateur;
        this.id_client = id_client;
        
    }

    
    public int getId_vente() { return id_vente; }
    public Date getDate_vente() { return date_vente; }
    public void setDate_vente(Date date_vente) {
        if (date_vente == null) {
            throw new IllegalArgumentException("La date de la vente ne peut pas être null.");
        }
        this.date_vente = date_vente;
    }
    public int getId_utilisateur() { return id_utilisateur; }
    public int getId_client() { return id_client; }
    
    
}