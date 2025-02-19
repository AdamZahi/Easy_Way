package tn.esprit.models;

import java.sql.Date;

public class Commentaire {
    private int id_com;
    private int id_post;
    private int id_user;
    private String contenu;
    private Date date_creat;  // Attribut pour la date de création du commentaire

    // Constructeurs
    public Commentaire() {
    }

    public Commentaire(int id_com, int id_post, int id_user, String contenu, Date date_creat) {
        this.id_com = id_com;
        this.id_post = id_post;
        this.id_user = id_user;
        this.contenu = contenu;
        this.date_creat = date_creat;
    }

    public Commentaire(int id_post, int id_user, String contenu, Date date_creat) {
        this.id_post = id_post;
        this.id_user = id_user;
        this.contenu = contenu;
        this.date_creat = date_creat;  // Attribuer la date de création
    }


    // Getters et Setters
    public int getId_com() {
        return id_com;
    }

    public void setId_com(int id_com) {
        this.id_com = id_com;
    }

    public int getId_post() {
        return id_post;
    }

    public void setId_post(int id_post) {
        this.id_post = id_post;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDate_creat() {
        return date_creat;
    }

    public void setDate_creat(Date date_creat) {
        this.date_creat = date_creat;
    }

    @Override
    public String toString() {
        return "Commentaire{" +

                ", id_post=" + id_post +
                ", id_user=" + id_user +
                ", contenu='" + contenu + '\'' +
                ", date_creat=" + date_creat +
                '}';
    }
}
