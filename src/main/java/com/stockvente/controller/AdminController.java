package com.stockvente.controller;

import com.stockvente.dao.UtilisateurDao;
import com.stockvente.models.Produit;
import com.stockvente.models.Utilisateur;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminController {
    private static final Logger LOGGER = Logger.getLogger(AdminController.class.getName());
    private  ProduitController produitController;
    private  FournisseurController fournisseurController;
    private  ApprovisionnementController approvisionnementController;
    private  CategorieController categorieController;
    private  ClientController clientController;
    private  StockController stockController;
    private  VenteController venteController;
    private  UserManagementController userManagementController;
    private  ConcernerController concernerController;
    private  Utilisateur utilisateur;
//    
//    
//    public Admin
    
    
    
    
     // ✅ Nouveau constructeur sans argument ajouté ici
    public AdminController() {
        this.produitController = new ProduitController();
        this.fournisseurController = new FournisseurController();
        this.approvisionnementController = new ApprovisionnementController();
        this.categorieController = new CategorieController();
        this.clientController = new ClientController();
        this.stockController = new StockController();
        this.venteController = new VenteController();
//      
        this.concernerController = new ConcernerController();
        
        UtilisateurDao utilisateurDao = new UtilisateurDao();
        this.userManagementController = new UserManagementController(utilisateurDao);

    }
    public UserManagementController getUserManagementController() {
        return userManagementController;
    }
    

    public AdminController(ProduitController produitController,
                           FournisseurController fournisseurController,
                           ApprovisionnementController approvisionnementController,
                           CategorieController categorieController,
                           ClientController clientController,
                           StockController stockController,
                           VenteController venteController,
                           UserManagementController userManagementController,
                           ConcernerController concernerController) {
        this.produitController = produitController;
        this.fournisseurController = fournisseurController;
        this.approvisionnementController = approvisionnementController;
        this.categorieController = categorieController;
        this.clientController = clientController;
        this.stockController = stockController;
        this.venteController = venteController;
        this.userManagementController = userManagementController;
        this.concernerController = concernerController;
    }
     public AdminController(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    private String checkAdminAccess(String role) {
        if (role == null || !"Admin".equalsIgnoreCase(role)) {
            return "Accès refusé : seul un Admin peut effectuer cette opération via AdminController.";
        }
        return null;
    }

    public String afficherApercuComplet(String role) {
        String accessCheck = checkAdminAccess(role);
        if (accessCheck != null) return accessCheck;

        try {
            StringBuilder result = new StringBuilder("=== Aperçu complet du système ===\n\n");

            result.append("### Produits ###\n")
                  .append(produitController.afficherTousLesProduits(role)).append("\n\n")
                  .append("### Fournisseurs ###\n")
                  .append(fournisseurController.afficherTousLesFournisseurs(role)).append("\n\n")
                  .append("### Approvisionnements ###\n")
                  .append(approvisionnementController.afficherTousLesApprovisionnements(role)).append("\n\n")
                  .append("### Catégories ###\n")
                  .append(categorieController.afficherToutesLesCategories(role)).append("\n\n")
                  .append("### Clients ###\n")
                  .append(clientController.afficherTousLesClients(role)).append("\n\n")
                  .append("### Stocks ###\n")
                  .append(stockController.afficherTousLesStocks(role)).append("\n\n")
                  .append("### Ventes ###\n")
                  .append(venteController.afficherToutesLesVentes(role)).append("\n\n")
                  .append("### Utilisateurs ###\n");

            List<Utilisateur> utilisateurs = userManagementController.afficherTousLesUtilisateurs();
            for (Utilisateur u : utilisateurs) {
                result.append(String.format("- ID: %d | Nom: %s | Email: %s | Rôle: %s\n",
                        u.getId_utilisateur(), u.getUsername(), u.getEmail(), u.getRole()));
            }

            return result.toString();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la génération de l'aperçu complet : {0}", e.getMessage());
            return "Erreur lors de la génération de l'aperçu complet : " + e.getMessage();
        }
    }

    // --- UTILISATEURS ---
    public String ajouterUtilisateur(String role, String username, String password, String email, String roleUtilisateur) {
        String accessCheck = checkAdminAccess(role);
        if (accessCheck != null) return accessCheck;
        return userManagementController.ajouterUtilisateur(username, password, email, roleUtilisateur);
    }

    public String mettreAJourUtilisateur(String role, int id_utilisateur, String username, String password, String email, String roleUtilisateur) {
        String accessCheck = checkAdminAccess(role);
        if (accessCheck != null) return accessCheck;
        return userManagementController.mettreAJourUtilisateur(id_utilisateur, username, password, email, roleUtilisateur);
    }

    public String supprimerUtilisateur(String role, int id_utilisateur) {
        String accessCheck = checkAdminAccess(role);
        if (accessCheck != null) return accessCheck;
        return userManagementController.supprimerUtilisateur(id_utilisateur, role);
    }

    // --- PRODUITS ---
   public String ajouterProduit(String role, String nom_produit, int id_categorie) {
    String accessCheck = checkAdminAccess(role);
    if (accessCheck != null) return accessCheck;

    Produit produit = new Produit();
    produit.setNom_produit(nom_produit);
    produit.setId_categorie(id_categorie);

    return produitController.ajouterProduit(role, produit);
}

public String mettreAJourProduit(String role, int id_produit, String nom_produit, int id_categorie) {
    String accessCheck = checkAdminAccess(role);
    if (accessCheck != null) return accessCheck;

    Produit produit = new Produit();
    produit.setId_produit(id_produit);
    produit.setNom_produit(nom_produit);
    produit.setId_categorie(id_categorie);

    return produitController.mettreAJourProduit(role, produit);
}


    public String supprimerProduit(String role, int id_produit) {
        String accessCheck = checkAdminAccess(role);
        if (accessCheck != null) return accessCheck;
        return produitController.supprimerProduit(role, id_produit);
    }

    // --- FOURNISSEURS ---
    public String ajouterFournisseur(String role, String nom, String adresse, String email, String tel) {
        String accessCheck = checkAdminAccess(role);
        if (accessCheck != null) return accessCheck;
        return fournisseurController.ajouterFournisseur(role, nom, adresse, email, tel);
    }

    public String mettreAJourFournisseur(String role, int id, String nom, String adresse, String email, String tel) {
        String accessCheck = checkAdminAccess(role);
        if (accessCheck != null) return accessCheck;
        return fournisseurController.mettreAJourFournisseur(role, id, nom, adresse, email, tel);
    }

    public String supprimerFournisseur(String role, int id) {
        String accessCheck = checkAdminAccess(role);
        if (accessCheck != null) return accessCheck;
        return fournisseurController.supprimerFournisseur(role, id);
    }

    // --- APPROVISIONNEMENTS ---
    public String ajouterApprovisionnement(String role, int id_produit, int id_fournisseur, int quantite, float prix_unitaire, Date date) {
        String accessCheck = checkAdminAccess(role);
        if (accessCheck != null) return accessCheck;
        return approvisionnementController.ajouterApprovisionnement(role, id_produit, id_fournisseur, quantite, prix_unitaire, date);
    }

    public String mettreAJourApprovisionnement(String role, int id, int id_produit, int id_fournisseur, int quantite, float prix_unitaire, Date date) {
        String accessCheck = checkAdminAccess(role);
        if (accessCheck != null) return accessCheck;
        return approvisionnementController.mettreAJourApprovisionnement(role, id, id_produit, id_fournisseur, quantite, prix_unitaire, date);
    }

    public String supprimerApprovisionnement(String role, int id) {
        String accessCheck = checkAdminAccess(role);
        if (accessCheck != null) return accessCheck;
        return approvisionnementController.supprimerApprovisionnement(role, id);
    }

    // --- CATÉGORIES ---
    public String ajouterCategorie(String role, String nom) {
        String accessCheck = checkAdminAccess(role);
        if (accessCheck != null) return accessCheck;
        return categorieController.ajouterCategorie(role, nom);
    }

    public String mettreAJourCategorie(String role, int id, String nom) {
        String accessCheck = checkAdminAccess(role);
        if (accessCheck != null) return accessCheck;
        return categorieController.mettreAJourCategorie(role, id, nom);
    }

    public String supprimerCategorie(String role, int id) {
        String accessCheck = checkAdminAccess(role);
        if (accessCheck != null) return accessCheck;
        return categorieController.supprimerCategorie(role, id);
    }

    // --- CLIENTS ---
    public String ajouterClient(String role, String nom, String prenom, String adresse, String telephone) {
        String accessCheck = checkAdminAccess(role);
        if (accessCheck != null) return accessCheck;
        return clientController.ajouterClient(role, nom, prenom, adresse, telephone);
    }

    public String mettreAJourClient(String role, int id, String nom, String prenom, String adresse, String telephone) {
        String accessCheck = checkAdminAccess(role);
        if (accessCheck != null) return accessCheck;
        return clientController.mettreAJourClient(role, id, nom, prenom, adresse, telephone);
    }

    public String supprimerClient(String role, int id) {
        String accessCheck = checkAdminAccess(role);
        if (accessCheck != null) return accessCheck;
        return clientController.supprimerClient(role, id);
    }

    // --- VENTES ---
    public String ajouterVente(String role, Date date, int id_utilisateur, int id_client) {
        String accessCheck = checkAdminAccess(role);
        if (accessCheck != null) return accessCheck;
        return venteController.ajouterVente(role, date, id_utilisateur, id_client);
    }

    public String mettreAJourVente(String role, int id, Date date, int id_utilisateur, int id_client) {
        String accessCheck = checkAdminAccess(role);
        if (accessCheck != null) return accessCheck;
        return venteController.mettreAJourVente(role, id, date, id_utilisateur, id_client);
    }

    public String supprimerVente(String role, int id) {
        String accessCheck = checkAdminAccess(role);
        if (accessCheck != null) return accessCheck;
        return venteController.supprimerVente(role, id);
    }

    // --- CONCERNER ---
    public String ajouterConcerner(String role, int id_vente, int id_produit, int quantite, double prix_unitaire) {
        String accessCheck = checkAdminAccess(role);
        if (accessCheck != null) return accessCheck;
        return concernerController.ajouterLigneDeVente(role, id_vente, id_produit, quantite, prix_unitaire);
    }

    public String mettreAJourConcerner(String role, int id_vente, int id_produit, int quantite, double prix_unitaire) {
        String accessCheck = checkAdminAccess(role);
        if (accessCheck != null) return accessCheck;
        return concernerController.mettreAJourLigneDeVente(role, id_vente, id_produit, quantite, prix_unitaire);
    }

    public String supprimerConcerner(String role, int id_vente, int id_produit) {
        String accessCheck = checkAdminAccess(role);
        if (accessCheck != null) return accessCheck;
        return concernerController.supprimerLigneDeVente(role, id_vente, id_produit);
    }

    // --- STOCK ---
    public String ajouterStock(String role, int id_produit, int quantite, Date date_misejour) {
        String accessCheck = checkAdminAccess(role);
        if (accessCheck != null) return accessCheck;
        return stockController.ajouterStock(role, id_produit, quantite, date_misejour);
    }

    public String mettreAJourStock(String role, int id_stock, int id_produit, int quantite, Date date_misejour) {
        String accessCheck = checkAdminAccess(role);
        if (accessCheck != null) return accessCheck;
        return stockController.mettreAJourStock(role, id_stock, id_produit, quantite, date_misejour);
    }

    public String supprimerStock(String role, int id_stock) {
        String accessCheck = checkAdminAccess(role);
        if (accessCheck != null) return accessCheck;
        return stockController.supprimerStock(role, id_stock);
    }

    // ** MÉTHODE À INSÉRER POUR ADMIN DASHBOARD **
//    public UserManagementController getUserManagementController() {
//        return userManagementController;
//    }
    public ProduitController getProduitController() {
    return produitController;
    }

}
