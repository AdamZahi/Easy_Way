package tn.esprit.models;

import java.sql.Date;

public class Posts {
    private int id_post;
    private int id_user;
    private String ville_depart;
    private String ville_arrivee;
    private Date date; // Type String
    private String message;

    public Posts() {
    }


    public Posts(int id_post, int id_user, String villeDepart, String villeArrivee, Date date, String message) {
        this.id_post = id_post;
        this.id_user = id_user;
        this.ville_depart = villeDepart;
        this.ville_arrivee = villeArrivee;
        this.date = date;
        this.message = message;
    }

    public Posts(int id_user, String villeDepart, String villeArrivee, Date date, String message) {

        this.id_user = id_user;
        this.ville_depart = villeDepart;
        this.ville_arrivee = villeArrivee;
        this.date = date;
        this.message = message;
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

    public String getVilleDepart() {
        return ville_depart;
    }

    public void setVilleDepart(String ville_depart) {
        this.ville_depart = ville_depart;
    }

    public String getVilleArrivee() {
        return ville_arrivee;
    }

    public void setVilleArrivee(String ville_arrivee) {
        this.ville_arrivee = ville_arrivee;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "Posts{" +

                ", id_user=" + id_user +
                ", villeDepart='" + ville_depart + '\'' +
                ", villeArrivee='" + ville_arrivee + '\'' +
                ", date='" + date + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
