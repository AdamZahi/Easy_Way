//package tn.esprit.util;
//
//import tn.esprit.models.user.User;
//
//public class SessionManager {
//    private static SessionManager instance; // Singleton
//    private User user;
//    private int id_user; // Attribut de session
//
//    // Constructeur privé pour empêcher l'instanciation directe
//    private SessionManager() {}
//
//    // Méthode pour obtenir l'instance unique de SessionManager
//    public static SessionManager getInstance() {
//        if (instance == null) {
//            instance = new SessionManager();
//        }
//        return instance;
//    }
//
//    // Getter et Setter de l'utilisateur
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    // Retourner l'ID de l'utilisateur connecté
//
//
//    // Getter et Setter de id_user
//    public int getId_user() {
//        return this.id_user;
//    }
//
//    public void setId_user(int id_user) {
//        this.id_user = id_user;
//    }
//    public void logout() {
//        id_user = 0; // Réinitialiser la session
//    }
//    public String getUsername() {
//        return (user != null) ? user.getNom() : null; // Si user est null, retourne null
//    }
//}
package tn.esprit.util;

import tn.esprit.models.user.User;

public class SessionManager {
    private static SessionManager instance; // Singleton
    private User user;
    private int id_user;// Stocker l'utilisateur connecté

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
    public void setId_user(int id_user) {
        this.id_user = id_user;
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