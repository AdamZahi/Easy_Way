package tn.esprit.models;

import java.util.Date;
import java.util.Objects;

public class reclamations {

    private int id;

    private String email;

    private int categorieId;

    private String sujet;
    private String statu;
    private String description;
    private String date_creation;

    public reclamations(int id, String email, int categorieId, String sujet, String statu, String description, String date_creation) {
        this.id = id;
        this.email = email;
        this.categorieId = categorieId;
        this.sujet = sujet;
        this.statu = statu;
        this.description = description;
        this.date_creation = date_creation;
    }

    public reclamations(String email, int categorieId, String sujet, String statu, String description, String date_creation) {
        this.email = email;
        this.categorieId = categorieId;
        this.sujet = sujet;
        this.statu = statu;
        this.description = description;
        this.date_creation = date_creation;
    }

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

    public int getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(int categorieId) {
        this.categorieId = categorieId;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        reclamations that = (reclamations) o;
        return categorieId == that.categorieId && Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(sujet, that.sujet) && Objects.equals(statu, that.statu) && Objects.equals(description, that.description) && Objects.equals(date_creation, that.date_creation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, categorieId, sujet, statu, description, date_creation);
    }

    @Override
    public String toString() {
        return "reclamations{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", categorieId=" + categorieId +
                ", sujet='" + sujet + '\'' +
                ", statu='" + statu + '\'' +
                ", description='" + description + '\'' +
                ", date_creation=" + date_creation +
                '}';
    }
}
