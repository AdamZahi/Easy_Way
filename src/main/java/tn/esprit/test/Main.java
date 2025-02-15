package tn.esprit.test;

import tn.esprit.models.Trajet;
import tn.esprit.models.Station;
import tn.esprit.services.ServiceTrajet;
import tn.esprit.services.ServiceStation;
import tn.esprit.services.ServiceLigne;


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
