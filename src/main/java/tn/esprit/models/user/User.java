package tn.esprit.models.user;

import java.time.LocalDateTime;

public class User {

    public enum Role {
        ROLE_PASSAGER,
        ROLE_CONDUCTEUR,
        ROLE_ADMIN;
    }

    private int id_user;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private int telephonne;  // Corrigé "telephonne" en "telephone"
    private String photo_profil;
    private LocalDateTime dateCreation;
    private Role roles; // Rôle de l'utilisateur (stocké comme une chaîne)

    // Constantes pour les rôles
    public static final String ROLE_PASSAGER = "ROLE_PASSAGER";
    public static final String ROLE_CONDUCTEUR = "ROLE_CONDUCTEUR";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    // Constructeur avec tous les attributs
    public User(String nom, String prenom, String email, String password, int telephonne, String photo_profil, Role roles) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.telephonne = telephonne;
        this.photo_profil = photo_profil;
        this.roles = roles;
    }

    // Constructeur qui prend uniquement `id_user` (utile pour récupérer un utilisateur par son ID)
    public User(int id_user) {
        this.id_user = id_user;
    }

    // Constructeur spécial sans téléphone ni photo
    public User(String nom, String prenom, String email, String password, Role roles) {
        this(nom, prenom, email, password, 0, null, roles);
    }

    // Constructeur par défaut
    public User() {}

    // Getters et Setters
    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMot_de_passe() {
        return password;
    }

    public void setMot_de_passe(String password) {
        this.password = password;
    }

    public int getTelephonne() {
        return telephonne;
    }

    public void setTelephonne(int telephonne) {
        this.telephonne = telephonne;
    }

    public String getPhoto_profil() {
        return photo_profil;
    }

    public void setPhoto_profil(String photo_profil) {
        this.photo_profil = photo_profil;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Role getRole() {
        return roles;
    }

    public void setRole(Role roles) {
        this.roles = roles;
    }

    // Méthode pour afficher l'utilisateur sous forme de chaîne
    @Override
    public String toString() {
        return "User{" +
                "id_user=" + id_user +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", telephonne=" + telephonne +
                ", photo_profil='" + photo_profil + '\'' +
                ", dateCreation=" + dateCreation +
                ", role='" + roles + '\'' +
                '}';
    }
}
