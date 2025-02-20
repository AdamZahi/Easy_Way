package tn.esprit.models;

public class Payement {
    private int id;
    private String mode;
    private float montant;

    public Payement() {}

    public Payement(int id, float montant, String mode) {
        this.id = id;
        this.montant = montant;
        this.mode = mode;
    }

    public Payement(float montant, String mode) {
        this.montant = montant;
        this.mode = mode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    @Override
    public String toString() {
        return "Payement{" +
                "mode='" + mode + '\'' +
                ", montant=" + montant +
                '}';
    }
}
