package tn.esprit.models.trajet;

public class Paiement {
    private int id;
    private String pay_id;
    private double montant;

    public Paiement() {}

    public Paiement(int id, String pay_id, double montant) {
        this.id = id;
        this.pay_id = pay_id;
        this.montant = montant;
    }

    public Paiement(String pay_id, double montant) {
        this.pay_id = pay_id;
        this.montant = montant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPay_id() {
        return pay_id;
    }

    public void setPay_id(String pay_id) {
        this.pay_id = pay_id;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    @Override
    public String toString() {
        return "Paiement{" +
                "pay_id='" + pay_id + '\'' +
                ", montant=" + montant +
                '}';
    }
}
