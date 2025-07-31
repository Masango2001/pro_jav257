package com.stockvente.controller;

import com.stockvente.models.Approvisionnement;
import com.stockvente.models.Produit;
import com.stockvente.models.Stock;
import com.stockvente.models.Utilisateur;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MagasinierController {
    
    private  ProduitController produitController;
    private  ApprovisionnementController approvisionnementController;
    private  StockController stockController;
    private  CategorieController categorieController;
    private  FournisseurController fournisseurController;
    private  SimpleDateFormat dateFormat;
    private Produit produit;
    private Approvisionnement approvisionnement;
    private Stock stock;
    private Utilisateur utilisateur;

    public MagasinierController() {
        this.produitController = new ProduitController();
        this.approvisionnementController = new ApprovisionnementController();
        this.stockController = new StockController();
        this.categorieController = new CategorieController();
        this.fournisseurController = new FournisseurController();
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    
    public MagasinierController(Utilisateur utilisateur){
        this.utilisateur = utilisateur; 
    }
    
    public MagasinierController(ProduitController produitController, ApprovisionnementController approvisionnementController, StockController stockController, CategorieController categorieController, FournisseurController fournisseurController) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String afficherApercuMagasinier(String role) {
        if (!role.equals("Magasinier") && !role.equals("Admin")) {
            return "Accès refusé : seul un Magasinier ou un Admin peut accéder à l'aperçu du Magasinier.";
        }

        StringBuilder result = new StringBuilder("=== Aperçu pour le Magasinier ===\n\n");

        result.append("### Produits ###\n");
        result.append(produitController.afficherTousLesProduits("Magasinier")).append("\n\n");

        result.append("### Approvisionnements ###\n");
        result.append(approvisionnementController.afficherTousLesApprovisionnements("Magasinier")).append("\n\n");

        result.append("### Stocks ###\n");
        result.append(stockController.afficherTousLesStocks("Magasinier")).append("\n\n");

        result.append("### Catégories ###\n");
        result.append(categorieController.afficherToutesLesCategories("Magasinier")).append("\n\n");

        result.append("### Fournisseurs ###\n");
        result.append(fournisseurController.afficherTousLesFournisseurs("Magasinier")).append("\n");

        return result.toString();
    }

    public String ajouterProduit(String role, String nomProduit, int idCategorie) {
        if (!role.equals("Magasinier") && !role.equals("Admin")) {
            return "Accès refusé : seul un Magasinier ou un Admin peut ajouter un produit via MagasinierController.";
        }
        return produitController.ajouterProduit("Magasinier", produit);
    }

    public String mettreAJourProduit(String role, int id_produit, String nom_produit, int id_categorie) {
        if (!role.equals("Magasinier") && !role.equals("Admin")) {
            return "Accès refusé : seul un Magasinier ou un Admin peut mettre à jour un produit via MagasinierController.";
        }
        return produitController.mettreAJourProduit("Magasinier", produit);
    }

    public String ajouterApprovisionnement(String role, int id_produit, int id_fournisseur, String quantite_approvisionnement, float prix_unitaire_achat, Date date_approvisionnement) {
        if (!role.equals("Magasinier") && !role.equals("Admin")) {
            return "Accès refusé : seul un Magasinier ou un Admin peut ajouter un approvisionnement via MagasinierController.";
        }
        if (date_approvisionnement == null) {
            return "Erreur : la date d'approvisionnement ne peut pas être nulle.";
        }
        Date currentDate = new Date(); // 14 mai 2025
        if (date_approvisionnement.after(currentDate)) {
            return "Erreur : la date d'approvisionnement ne peut pas être dans le futur.";
        }
        return approvisionnementController.ajouterApprovisionnement("Magasinier",  approvisionnement);
    }

    public String mettreAJourApprovisionnement(String role, int id_approvisionnement) {
        if (!role.equals("Magasinier") && !role.equals("Admin")) {
            return "Accès refusé : seul un Magasinier ou un Admin peut mettre à jour un approvisionnement via MagasinierController.";
        }
       
        return approvisionnementController.mettreAJourApprovisionnement("Magasinier", approvisionnement);
    }



    public String mettreAJourStock(String role, int id_stock) {
        if (!role.equals("Magasinier") && !role.equals("Admin")) {
            return "Accès refusé : seul un Magasinier ou un Admin peut mettre à jour un stock via MagasinierController.";
        }
      
        return stockController.mettreAJourStock("Magasinier", stock);
    }

    public String ajouterCategorie(String role, String nomCategorie) {
        if (!role.equals("Magasinier") && !role.equals("Admin")) {
            return "Accès refusé : seul un Magasinier ou un Admin peut ajouter une catégorie via MagasinierController.";
        }
        return categorieController.ajouterCategorie("Magasinier", nomCategorie);
    }

    public String mettreAJourCategorie(String role, int id_categorie) {
        if (!role.equals("Magasinier") && !role.equals("Admin")) {
            return "Accès refusé : seul un Magasinier ou un Admin peut mettre à jour une catégorie via MagasinierController.";
        }
        return categorieController.mettreAJourCategorie("Magasinier", id_categorie);
    }

    public String ajouterFournisseur(String role, String nomCompletFournisseur, String adresseFournisseur, String emailFournisseur, String telephoneFournisseur) {
        if (!role.equals("Magasinier") && !role.equals("Admin")) {
            return "Accès refusé : seul un Magasinier ou un Admin peut ajouter un fournisseur via MagasinierController.";
        }
        return fournisseurController.ajouterFournisseur("Magasinier", nomCompletFournisseur, adresseFournisseur, emailFournisseur, telephoneFournisseur);
    }

    public String mettreAJourFournisseur(String role,   int id_fournisseur) {
        if (!role.equals("Magasinier") && !role.equals("Admin")) {
            return "Accès refusé : seul un Magasinier ou un Admin peut mettre à jour un fournisseur via MagasinierController.";
        }
        return fournisseurController.mettreAJourFournisseur("Magasinier", id_fournisseur  );
    }

    public String supprimerApprovisionnement(String role) {
        if (!role.equals("Magasinier") && !role.equals("Admin")) {
            return "Accès refusé : seul un Magasinier ou un Admin peut supprimer un approvisionnement via MagasinierController.";
        }
        try {
            return approvisionnementController.supprimerApprovisionnement("Magasinier");
        } catch (RuntimeException e) {
            return "Erreur lors de la suppression de l'approvisionnement : " + e.getMessage();
        }
    }

    public AdminController getFournisseurController() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public AdminController getCategorieController() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public ProduitController getProduitController() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public AdminController getApprovisionnementController() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}