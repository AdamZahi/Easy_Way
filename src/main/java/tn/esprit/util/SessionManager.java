package tn.esprit.util;

import tn.esprit.models.user.User;

public class SessionManager {
    private static SessionManager instance; // Singleton
    private User user; // Stocker l'utilisateur connecté

    // Constructeur privé pour empêcher l'instanciation directe
    private SessionManager() {}

    // Méthode pour obtenir l'instance unique de SessionManager
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    // Getter et Setter de l'utilisateur
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Retourner l'ID de l'utilisateur connecté
    public int getId_user() {
        return (user != null) ? user.getId_user() : 0; // Si user est null, retourne 0
    }

    // Retourner le nom d'utilisateur de l'utilisateur connecté
    public String getUsername() {
        return (user != null) ? user.getNom() : null; // Si user est null, retourne null
    }

    // Déconnexion de l'utilisateur
    public void logout() {
        user = null; // Supprimer l'utilisateur de la session
    }
}
