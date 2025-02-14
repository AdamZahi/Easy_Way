package tn.esprit.test;

import tn.esprit.models.Trajet;
import tn.esprit.models.Station;
import tn.esprit.services.ServiceTrajet;
import tn.esprit.services.ServiceStation;
import tn.esprit.services.ServiceLigne;


public class Main {
    public static void main(String[] args) {

        ServiceTrajet t = new ServiceTrajet();
        t.getAll();
        ServiceStation s = new ServiceStation();
        s.getAll();
        ServiceLigne l = new ServiceLigne();
        l.getAll();
        System.out.println("ça marche");

    }

}
