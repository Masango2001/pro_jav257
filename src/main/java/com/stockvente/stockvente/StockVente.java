//package com.stockvente.stockvente;
//
//import com.stockvente.controller.AdminController;
//import com.stockvente.controller.MagasinierController;
//import com.stockvente.controller.VendeurController;
//import com.stockvente.models.Utilisateur;
//import com.stockvente.views.AdminDashboard;
//import com.stockvente.views.MagasinierDashboard;
//import com.stockvente.views.VendeurDashboard;
//import com.stockvente.views.LoginView;
//
//import javax.swing.SwingUtilities;
//
//public class StockVente {
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            // ✅ Instanciation correcte de LoginView
//            LoginView loginView = new LoginView();
//
//            // ✅ Appel correct de la méthode
//            Utilisateur utilisateur = loginView.afficherEtObtenirUtilisateur();
//
//            // ✅ Vérifie que l'utilisateur est bien connecté
//            if (utilisateur != null) {
//                String role = utilisateur.getRole();
//
//                switch (role) {
//                    case "Admin":
//                        AdminController adminController = new AdminController(utilisateur);
//                        new AdminDashboard(adminController).setVisible(true);
//                        break;
//
//                    case "Magasinier":
//                        MagasinierController magasinierController = new MagasinierController(utilisateur);
//                        new MagasinierDashboard(magasinierController).setVisible(true);
//                        break;
//
//                    case "Vendeur":
//                        VendeurController vendeurController = new VendeurController(utilisateur);
//                        new VendeurDashboard(vendeurController).setVisible(true);
//                        break;
//
//                    default:
//                        System.err.println("Rôle non reconnu : " + role);
//                        break;
//                }
//            } else {
//                System.err.println("Aucun utilisateur connecté.");
//            }
//        });
//    }
//}


package com.stockvente.stockvente;

import javax.swing.SwingUtilities;
import com.stockvente.views.LoginView;
import com.stockvente.views.LoginView;

public class StockVente {

    // Méthode principale pour lancer l'appli
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginView().setVisible(true);
        });
    }
    
}
