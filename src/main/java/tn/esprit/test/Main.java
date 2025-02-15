package tn.esprit.test;

import tn.esprit.services.ServiceAdmin;
import tn.esprit.services.ServiceUser ;
import tn.esprit.services.ServiceConducteur ;


import tn.esprit.models.user.Admin;
import tn.esprit.models.user.User;
import tn.esprit.models.Conducteur;


public class Main {
    public static void main(String[] args) {
         ServiceConducteur sc = new ServiceConducteur();



        //test Conducteur
//        sc.add(new Conducteur("foulen","feltani","exemple@gmail.com","123456",5566332
//                ,"https://scontent.ftun9-1.fna.fbcdn.net/v/t39.30808-6/476973258_479698575190094_7338888870042912014_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=6ee11a&_nc_ohc=Pp-S5DxMB6QQ7kNvgEKM2Jr&_nc_oc=Adhd_oxVUY92NYtQRlxEJuEhDwnGxgC-UERiarqQqg-AW94Q-U9VeC33G6oJ7pq-N98&_nc_zt=23&_nc_ht=scontent.ftun9-1.fna&_nc_gid=A9soiCDa2_K54-OGoc4MCHe&oh=00_AYB7GVFjptoWsQueX5wBlz3EQpjI45NWz8Z9iQdhXTLv8A&oe=67B2CE8E"
//        ,"permis1526","3ans experience "));
        Conducteur updatedCon = new Conducteur(21,"foulen","flani","abcd@email.tn","123np",94103115,"picture here nigga","0000","10snin");
        sc.update(updatedCon);
//        sc.delete(updatedCon);
        System.out.println(sc.getAll());





    }

}
