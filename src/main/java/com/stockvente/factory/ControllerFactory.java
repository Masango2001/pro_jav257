package com.stockvente.factory;

import com.stockvente.controller.*;
import com.stockvente.dao.UtilisateurDao;

public class ControllerFactory {

    public static AdminController buildAdminController() {
        return new AdminController(
            new ProduitController(),
            new FournisseurController(),
            new ApprovisionnementController(),
            new CategorieController(),
            new ClientController(),
            new StockController(),
            new VenteController(),
            new UserManagementController(new UtilisateurDao()),
            new ConcernerController()
        );
    }

    public static MagasinierController buildMagasinierController() {
        return new MagasinierController(
            new ProduitController(),
            new ApprovisionnementController(),
            new StockController(),
            new CategorieController(),
            new FournisseurController()
        );
    }

    // Si tu as un VendeurController, tu peux aussi l'ajouter ici :
    /*
    public static VendeurController buildVendeurController() {
        return new VendeurController(
            new ClientController(),
            new VenteController(),
            new StockController(),
            new ConcernerController()
        );
    }
    */

    public static VendeurController buildVendeurController() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
