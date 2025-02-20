package tn.esprit.models;

public class Reservation {
    private int id,nb;
    private String depart,arret,vehicule;

    public Reservation() {}

    public Reservation(int id, String depart, String arret, String vehicule,int nb) {
        this.id = id;
        this.depart = depart;
        this.arret = arret;
        this.vehicule = vehicule;
        this.nb = nb;
    }

    public Reservation(String depart, String arret, String vehicule,int nb) {
        this.depart = depart;
        this.arret = arret;
        this.vehicule = vehicule;
        this.nb = nb;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getArret() {
        return arret;
    }

    public void setArret(String arret) {
        this.arret = arret;
    }

    public String getVehicule() {
        return vehicule;
    }

    public void setVehicule(String vehicule) {
        this.vehicule = vehicule;
    }

    public int getNb() {
        return nb;
    }

    public void setNb(int nb) {
        this.nb = nb;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "depart='" + depart + '\'' +
                ", arret='" + arret + '\'' +
                ", vehicule='" + vehicule + '\'' +
                ", nb='" + nb + '\'' +
                '}';
    }
}
