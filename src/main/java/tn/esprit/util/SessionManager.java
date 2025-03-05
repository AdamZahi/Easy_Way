package tn.esprit.util;

import tn.esprit.models.user.User;

public class SessionManager {
    private static SessionManager instance; // Singleton
    private User user;
    private int id_user; // Attribut de session

    // Constructeur privé pour empêcher l'instanciation directe
    private SessionManager() {}

    // Méthode pour obtenir l'instance unique de SessionManager
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    // Getter et Setter de id_user
    public int getId_user() {
        return this.id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
    public void logout() {
        id_user = 0; // Réinitialiser la session
    }
    public String getUsername() {
        return (user != null) ? user.getNom() : null; // Si user est null, retourne null
    }
}
