package tn.esprit.models.reclamation;

import java.util.Objects;

public class reclamations {

    private int id;
    private String email;
    private categories category_id_id; // Utilisation de l'objet `categories` au lieu de `int categorieId`
    private String sujet;
    private String statut;
    private String description;
    private String date_creation;
    private int id_user;

    // Constructeur avec ID
    public reclamations(int id, String email, categories category_id_id, String sujet, String statut, String description, String date_creation, int id_user) {
        this.id = id;
        this.email = email;
        this.category_id_id = category_id_id;
        this.sujet = sujet;
        this.statut = statut;
        this.description = description;
        this.date_creation = date_creation;
        this.id_user = id_user;
    }

    // Constructeur sans ID (pour les insertions)
    public reclamations(String email, categories category_id_id, String sujet, String statut, String description, String date_creation, int id_user) {
        this.email = email;
        this.category_id_id = category_id_id;
        this.sujet = sujet;
        this.statut = statut;
        this.description = description;
        this.date_creation = date_creation;
        this.id_user = id_user;
    }

    public reclamations(int id_user) {

        this.id_user = id_user;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public categories getCategorie() {
        return category_id_id;
    }

    public void setCategorie(categories categorie) {
        this.category_id_id = categorie;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getStatu() {
        return statut;
    }

    public void setStatu(String statu) {
        this.statut = statut;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(String date_creation) {
        this.date_creation = date_creation;
    }

    public int getUser_id() { return id_user; }
    public void setUser_id(int id_user) { this.id_user = id_user; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        reclamations that = (reclamations) o;
        return id == that.id && Objects.equals(email, that.email) &&
                Objects.equals(category_id_id, that.category_id_id) &&
                Objects.equals(sujet, that.sujet) &&
                Objects.equals(statut, that.statut) &&
                Objects.equals(description, that.description) &&
                Objects.equals(date_creation, that.date_creation) &&
                Objects.equals(id_user, that.id_user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, category_id_id, sujet, statut, description, date_creation, id_user);
    }

    @Override
    public String toString() {
        return "reclamations{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", categorie=" + category_id_id +
                ", sujet='" + sujet + '\'' +
                ", statu='" + statut + '\'' +
                ", description='" + description + '\'' +
                ", date_creation='" + date_creation + '\'' +
                ", id_user=" + id_user +
                '}';
    }
}
