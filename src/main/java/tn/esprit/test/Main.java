package tn.esprit.test;

import tn.esprit.models.trajet.Trajet;
import tn.esprit.services.trajet.ServiceTrajet;
import tn.esprit.services.trajet.ServiceStation;
import tn.esprit.services.trajet.ServiceLigne;


public class Main {
    public static void main(String[] args) {

        Trajet trajet = new Trajet("01:00",50,"10:00","11:00","Tunis","Lac2","libre");
        ServiceTrajet t = new ServiceTrajet();
        t.add(trajet);
        t.getAll();
        ServiceStation s = new ServiceStation();
        s.getAll();
        ServiceLigne l = new ServiceLigne();
        l.getAll();
        System.out.println("ça marche");

    }

}
