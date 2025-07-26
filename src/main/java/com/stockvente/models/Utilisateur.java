package com.stockvente.models;

import java.util.List;

public class Utilisateur {
    private int id_utilisateur;
    private String username;
    private String password;
    private String email;
    private String role;

    private static final List<String> VALID_ROLES = List.of("Admin", "Vendeur", "Magasinier");

    // ✅ Constructeur pour l'ajout (validation complète)
    public Utilisateur(String username, String password, String email, String role) {
        validateUsername(username);
        validatePassword(password);
        validateEmail(email);
        validateRole(role);
        this.id_utilisateur = 0;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    // ✅ Constructeur pour la mise à jour (valide les champs sauf mot de passe déjà haché)
    public Utilisateur(int id_utilisateur, String username, String password, String email, String role) {
        validateId(id_utilisateur);
        validateUsername(username);
        // ❌ Supprimer la validation du mot de passe ici
        validateEmail(email);
        validateRole(role);
        this.id_utilisateur = id_utilisateur;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    // ✅ Constructeur statique pour chargement depuis la base (aucune validation)
    public static Utilisateur fromDatabase(int id_utilisateur, String username, String password, String email, String role) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.id_utilisateur = id_utilisateur;
        utilisateur.username = username;
        utilisateur.password = password;
        utilisateur.email = email;
        utilisateur.role = role;
        return utilisateur;
    }

    // ✅ Constructeur privé vide pour usage interne
    private Utilisateur() {}

    private void validateId(int id_utilisateur) {
        if (id_utilisateur < 0) {
            throw new IllegalArgumentException("L'ID de l'utilisateur ne peut pas être négatif.");
        }
    }

    private void validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom d'utilisateur ne peut pas être vide ou null.");
        }
        if (username.length() > 50) {
            throw new IllegalArgumentException("Le nom d'utilisateur ne peut pas dépasser 50 caractères.");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe ne peut pas être vide ou null.");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins 8 caractères.");
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("L'email ne peut pas être vide ou null.");
        }
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("L'email n'est pas valide.");
        }
    }

    private void validateRole(String role) {
        if (role == null || role.trim().isEmpty()) {
            throw new IllegalArgumentException("Le rôle ne peut pas être vide ou null.");
        }
        if (!VALID_ROLES.contains(role)) {
            throw new IllegalArgumentException("Le rôle doit être l'un des suivants : " + VALID_ROLES);
        }
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        validateUsername(username);
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        validatePassword(password);
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        validateEmail(email);
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        validateRole(role);
        this.role = role;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
               "id_utilisateur=" + id_utilisateur +
               ", username='" + username + '\'' +
               ", email='" + email + '\'' +
               ", role='" + role + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return id_utilisateur == that.id_utilisateur &&
               username.equals(that.username) &&
               email.equals(that.email) &&
               role.equals(that.role);
    }

    @Override
    public int hashCode() {
        return 31 * id_utilisateur + username.hashCode();
    }
}
