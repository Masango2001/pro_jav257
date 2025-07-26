package com.stockvente.models;

public class Client {
    private  int id_client;
    private String nom_client;
    private String prenom_client;
    private String adresse_client;
    private String telephone_client;
    
    
    public Client(){
    
    }
    public Client(int id_client, String nom_client, String prenom_client, String adresse_client, String telephone_client) {
        this.id_client = id_client;
        this.nom_client = nom_client;
        this.prenom_client = prenom_client;
        this.adresse_client = adresse_client;
        this.telephone_client = telephone_client;
    }
    public Client( int par, String nom_client, String prenom_client) {

           this.nom_client = nom_client;
           this.prenom_client = prenom_client;
           this.adresse_client = adresse_client;
           this.telephone_client = telephone_client;
       }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }
   
 
    public String getNom_client() {
        return nom_client;
    }

    public void setNom_client(String nom_client) {
        this.nom_client = nom_client;
    }

    public String getPrenom_client() {
        return prenom_client;
    }

    public void setPrenom_client(String prenom_client) {
        this.prenom_client = prenom_client;
    }

    public String getAdresse_client() {
        return adresse_client;
    }

    public void setAdresse_client(String adresse_client) {
        this.adresse_client = adresse_client;
    }

    public String getTelephone_client() {
        return telephone_client;
    }

    public void setTelephone_client(String telephone_client) {
        this.telephone_client = telephone_client;
    }

  
   

}