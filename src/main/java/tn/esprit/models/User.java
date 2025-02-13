package tn.esprit.models;


public class User {
    private int id_user ;
    private String nom, prenom,email,mot_de_passe;
    private int telephonne ;
    private String photo_profil; // Nouveau champ


    public User(){
    }
    public User( String nom, String prenom,String email , String mot_de_passe, int telephonne ,  String photo_profil  ) {
        this.nom = nom ;
        this.prenom = prenom ;
        this.email = email ;
        this.mot_de_passe = mot_de_passe ;
        this.telephonne = telephonne ;
        this.photo_profil=photo_profil;
    }

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
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
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



    @Override
    public String toString() {
        return "User{" +
                "id_user=" + id_user +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", mot_de_passe='" + mot_de_passe + '\'' +
                ", telephonne=" + telephonne +
                ", photo_profil='" + photo_profil + '\'' +
                '}';
    }
}


