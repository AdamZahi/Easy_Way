package tn.esprit.test;

import tn.esprit.models.Events.Evenements;
import tn.esprit.models.Events.StatusEvenement;
import tn.esprit.models.Events.TypeEvenement;
import tn.esprit.services.ServiceConducteur ;


import tn.esprit.models.user.Conducteur;
import tn.esprit.services.ServiceEvenement;


public class Main {
    public static void main(String[] args) {
         ServiceConducteur sc = new ServiceConducteur();
        ServiceEvenement se = new ServiceEvenement();
        se.delete(new Evenements(1, TypeEvenement.RETARD,3,"update",
                new java.sql.Date(2025,03,01),
                new java.sql.Date(2025,03,02), StatusEvenement.RESOLU,4) );


        //test Conducteur
//        sc.add(new Conducteur("foulen","feltani","exemple@gmail.com","123456",5566332
//                ,"https://scontent.ftun9-1.fna.fbcdn.net/v/t39.30808-6/476973258_479698575190094_7338888870042912014_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=6ee11a&_nc_ohc=Pp-S5DxMB6QQ7kNvgEKM2Jr&_nc_oc=Adhd_oxVUY92NYtQRlxEJuEhDwnGxgC-UERiarqQqg-AW94Q-U9VeC33G6oJ7pq-N98&_nc_zt=23&_nc_ht=scontent.ftun9-1.fna&_nc_gid=A9soiCDa2_K54-OGoc4MCHe&oh=00_AYB7GVFjptoWsQueX5wBlz3EQpjI45NWz8Z9iQdhXTLv8A&oe=67B2CE8E"
//        ,"permis1526","3ans experience "));
        //Conducteur updatedCon = new Conducteur(22,"foulen","flani","abcd@email.tn","123np",94103115,"picture here nigga","0000","10snin");
//        sc.update(updatedCon);
        //sc.delete(updatedCon);
        System.out.println(se.getAll());





    }

}
