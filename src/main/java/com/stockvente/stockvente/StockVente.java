package com.stockvente.stockvente;

import javax.swing.SwingUtilities;
import com.stockvente.views.LoginView;


public class StockVente {

    // Méthode principale pour lancer l'appli
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginView().setVisible(true);
        });
    }
    
}
