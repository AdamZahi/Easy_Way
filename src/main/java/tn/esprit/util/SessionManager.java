package tn.esprit.util;

import tn.esprit.services.user.ServiceUser;

public class SessionManager {
    private static SessionManager instance; // Singleton
    private int id_user; // Attribut de session
    private ServiceUser se;
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





    public String getUsername(){
        return se.getById(id_user).getNom();
    }


}
