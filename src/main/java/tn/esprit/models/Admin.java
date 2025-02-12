package tn.esprit.models;

import tn.esprit.models.User;

public class Admin extends User{

    public Admin() {
        super(); // Appelle le constructeur de la classe User (par défaut)
    }

    public Admin(int id_user, String nom, String prenom, String email, String mot_de_passe,
                 int telephonne, String photo_profil, Role role) {
        super(id_user, nom, prenom, email, mot_de_passe, telephonne,photo_profil, role); // Appel du constructeur de User
    }

    // Accès aux attributs hérités
    public String getNom() {
        return super.getNom(); // Accède à l'attribut `nom` de la classe `User`
    }

    public String getMot_de_passe() {
        return super.getMot_de_passe(); // Accède à l'attribut `mot_de_passe` de `User`
    }

    public String getPrenom() {
        return super.getPrenom(); // Accède à l'attribut `prenom` de `User`
    }

    public int getId_user() {
        return super.getId_user(); // Accède à l'attribut `id_user` de `User`
    }


    @Override
    public String toString() {
        return "Admin{}";
    }
}
