package com.stockvente.service;

import com.stockvente.dao.UtilisateurDao;
import com.stockvente.models.Utilisateur;

import java.util.List;

public class UserService {
    private final UtilisateurDao utilisateurDao;

    public UserService() {
        this.utilisateurDao = new UtilisateurDao();
    }

    public Utilisateur findByUsername(String username) {
        List<Utilisateur> utilisateurs = utilisateurDao.afficherTous();
        for (Utilisateur u : utilisateurs) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }
        return null;
    }

    public void saveUser(Utilisateur user) {
        utilisateurDao.save(user);
    }
}
