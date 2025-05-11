package tn.esprit.models.user;

public class Conducteur extends User {
    private int id_conducteur;
    private int nb_trajet_effectues;
    private int nb_passagers_transportes;

    // Constructeur vide
    public Conducteur() {}

    // Constructeur avec id_conducteur
    public Conducteur(int id_conducteur) {
        this.id_conducteur = id_conducteur;
    }

    // Constructeur complet
    public Conducteur(int id_user, String nom, String prenom, String email, String mot_de_passe, int telephonne, String photo_profil, int nb_trajet_effectues, int nb_passagers_transportes) {
        super(nom, prenom, email, mot_de_passe, telephonne, photo_profil, Role.ROLE_CONDUCTEUR);  // Appel du constructeur parent User
        this.id_conducteur = id_user;
        this.nb_trajet_effectues = nb_trajet_effectues;
        this.nb_passagers_transportes = nb_passagers_transportes;
    }

    // Constructeur pour initialiser avec un ID utilisateur et les autres paramètres spécifiques à un conducteur
    public Conducteur(int id_user, int nb_trajet_effectues, int nb_passagers_transportes) {
        setId_user(id_user);  // On utilise setId_user de la classe parent User
        this.nb_trajet_effectues = nb_trajet_effectues;
        this.nb_passagers_transportes = nb_passagers_transportes;
    }
    public Conducteur(int nb_trajet_effectues, int nb_passagers_transportes) {
        this.nb_trajet_effectues = nb_trajet_effectues;
        this.nb_passagers_transportes = nb_passagers_transportes;
    }


    // Getters et Setters
    public int getId_conducteur() {
        return id_conducteur;
    }

    public void setId_conducteur(int id_conducteur) {
        this.id_conducteur = id_conducteur;
    }

    public int getNb_trajet_effectues() {
        return nb_trajet_effectues;
    }

    public void setNb_trajet_effectues(int nb_trajet_effectues) {
        this.nb_trajet_effectues = nb_trajet_effectues;
    }

    public int getNb_passagers_transportes() {
        return nb_passagers_transportes;
    }

    public void setNb_passagers_transportes(int nb_passager_transportes) {
        this.nb_passagers_transportes = nb_passagers_transportes;
    }

    // Méthode toString pour afficher un conducteur de manière lisible
    @Override
    public String toString() {
        return "Conducteur{" +
                "id_conducteur=" + id_conducteur +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", nb_trajet_effectues=" + nb_trajet_effectues +
                ", nb_passagers_transportes=" + nb_passagers_transportes +
                '}';
    }
}
