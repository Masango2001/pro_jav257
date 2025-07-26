package com.stockvente.controller;

import com.stockvente.models.Utilisateur;
import com.stockvente.service.UserService;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController {
    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());
    private final UserService userService;

    public LoginController() {
        this.userService = new UserService(); // En production, préférer l'injection de dépendances
    }

    public Utilisateur login(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom d'utilisateur ne peut pas être vide.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe ne peut pas être vide.");
        }

        try {
            Utilisateur user = userService.findByUsername(username);
            if (user == null || !password.equals(user.getPassword())) {
                throw new IllegalArgumentException("Nom d'utilisateur ou mot de passe incorrect.");
            }
            return user;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'authentification : {0}", e.getMessage());
            throw new RuntimeException("Erreur lors de l'authentification : " + e.getMessage(), e);
        }
    }

    public void register(String username, String password, String email, String role) {
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            role == null || role.trim().isEmpty()) {
            throw new IllegalArgumentException("Tous les champs sont obligatoires.");
        }

        try {
            Utilisateur newUser = new Utilisateur(username, password, email, role);
            userService.saveUser(newUser);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'enregistrement : {0}", e.getMessage());
            throw new RuntimeException("Erreur lors de l'enregistrement : " + e.getMessage(), e);
        }
    }
}
