package tn.esprit.models.user;

public class Passager extends User {
    private int nb_trajet_effectues;

    // Constructeur principal
    public Passager(String nom, String prenom, String email, String mot_de_passe, int telephonne, String photo_profil) {
        super(nom, prenom, email, mot_de_passe, telephonne, photo_profil, Role.ROLE_PASSAGER); // ROLE_PASSAGER comme valeur pour le rôle
        this.nb_trajet_effectues = 0;
    }

    // Constructeur avec un mot de passe vide
    public Passager(String nom, String prenom, String email, String mot_de_passe) {
        super(nom, prenom, email, mot_de_passe, Role.ROLE_PASSAGER); // ROLE_PASSAGER comme valeur pour le rôle
        this.nb_trajet_effectues = 0;
    }

    // Constructeur avec tous les paramètres
    public Passager(int id_user, String nom, String prenom, String email, String mot_de_passe, int telephonne, String photo_profil, int nb_trajet_effectues) {
        super(nom, prenom, email, mot_de_passe, telephonne, photo_profil, Role.ROLE_PASSAGER); // ROLE_PASSAGER comme valeur pour le rôle
        super.setId_user(id_user); // Assigner l'ID utilisateur
        this.nb_trajet_effectues = nb_trajet_effectues;
    }
    // ✅ Ajout du constructeur avec seulement l'ID
    public Passager(int id_user) {
        super(id_user);  // Si `User` a un champ `id_user`
    }
    // ✅ Ajouter un getter
    public int getId_user() {
        return super.getId_user();  // Hérité de `User`
    }


    public int getNbTrajetsEffectues() {
        return nb_trajet_effectues;
    }

    public void setNbTrajetsEffectues(int nb_trajet_effectues) {
        this.nb_trajet_effectues = nb_trajet_effectues;
    }

    @Override
    public String toString() {
        return "Passager{" +super.toString()+
                "nbTrajetsEffectues=" + nb_trajet_effectues +
                '}';
    }
}

