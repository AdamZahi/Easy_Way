package tn.esprit.test;

import tn.esprit.models.Events.Evenements;
import tn.esprit.models.Events.StatusEvenement;
import tn.esprit.models.Events.TypeEvenement;
import tn.esprit.services.user.ServiceConducteur ;


import tn.esprit.models.user.Conducteur;
import tn.esprit.services.ServiceEvenement;
import tn.esprit.services.user.ServiceUser;


public class Main {
    public static void main(String[] args) {

        //    ServiceAdmin sa = new ServiceAdmin();
        //  ServiceConducteur sc = new ServiceConducteur();
        ServiceUser su = new ServiceUser();


        // test admin
        // sa.add(new Admin( "Mejri","Eya","mejri@gmail.com","123456" , 99556633 , "https://scontent.ftun9-1.fna.fbcdn.net/v/t39.30808-6/476973258_479698575190094_7338888870042912014_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=6ee11a&_nc_ohc=Pp-S5DxMB6QQ7kNvgEKM2Jr&_nc_oc=Adhd_oxVUY92NYtQRlxEJuEhDwnGxgC-UERiarqQqg-AW94Q-U9VeC33G6oJ7pq-N98&_nc_zt=23&_nc_ht=scontent.ftun9-1.fna&_nc_gid=A9soiCDa2_K54-OGoc4MCHe&oh=00_AYB7GVFjptoWsQueX5wBlz3EQpjI45NWz8Z9iQdhXTLv8A&oe=67B2CE8E"));
        //     Admin AdminUpdate = new Admin(14 , "xs", "xs", "ghofrane@gmail.com", "newpassword123" , 12345678 , "https://scontent.ftun9-1.fna.fbcdn.net/v/t39.30808-6/476973258_479698575190094_7338888870042912014_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=6ee11a&_nc_ohc=Pp-S5DxMB6QQ7kNvgEKM2Jr&_nc_oc=Adhd_oxVUY92NYtQRlxEJuEhDwnGxgC-UERiarqQqg-AW94Q-U9VeC33G6oJ7pq-N98&_nc_zt=23&_nc_ht=scontent.ftun9-1.fna&_nc_gid=A9soiCDa2_K54-OGoc4MCHe&oh=00_AYB7GVFjptoWsQueX5wBlz3EQpjI45NWz8Z9iQdhXTLv8A&oe=67B2CE8E" );
        //   System.out.println(AdminUpdate.getId_admin());
        //    sa.update(AdminUpdate);
        //  System.out.println(sa.getAll());
        //Admin a = sa.getById(3);
        //   System.out.println(a);
        // ********  hedhi tfasakh m tableau admin khw yaani lezm tetgadd **********
        //    Admin adminDelete = new Admin(3);
        //    sa.delete(adminDelete);



        // test user
        //su.add(new User("Benhssan","Ghofrane","ghofrane384@gmail.com","123456",99556332,"https://scontent.ftun9-1.fna.fbcdn.net/v/t39.30808-6/476973258_479698575190094_7338888870042912014_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=6ee11a&_nc_ohc=Pp-S5DxMB6QQ7kNvgEKM2Jr&_nc_oc=Adhd_oxVUY92NYtQRlxEJuEhDwnGxgC-UERiarqQqg-AW94Q-U9VeC33G6oJ7pq-N98&_nc_zt=23&_nc_ht=scontent.ftun9-1.fna&_nc_gid=A9soiCDa2_K54-OGoc4MCHe&oh=00_AYB7GVFjptoWsQueX5wBlz3EQpjI45NWz8Z9iQdhXTLv8A&oe=67B2CE8E"));
        //   User userToUpdate = new User(4 ,"xs", "xs", "ghofrane@gmail.com", "newpassword123", 99556332 ,"https://scontent.ftun9-1.fna.fbcdn.net/v/t39.30808-6/476973258_479698575190094_7338888870042912014_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=6ee11a&_nc_ohc=Pp-S5DxMB6QQ7kNvgEKM2Jr&_nc_oc=Adhd_oxVUY92NYtQRlxEJuEhDwnGxgC-UERiarqQqg-AW94Q-U9VeC33G6oJ7pq-N98&_nc_zt=23&_nc_ht=scontent.ftun9-1.fna&_nc_gid=A9soiCDa2_K54-OGoc4MCHe&oh=00_AYB7GVFjptoWsQueX5wBlz3EQpjI45NWz8Z9iQdhXTLv8A&oe=67B2CE8E");
        //   System.out.println(userToUpdate.getId_user());
        //   su.update(userToUpdate);

        // User userToDelete = new User(4);
        // su.delete(userToDelete);
        System.out.println(su.getAll());

        // User u = su.getById(6);
        // System.out.println(u);


        //test Conducteur
//      sc.add(new Conducteur("Benhssan","Ghofrane","ghofrane@gmail.com","123456",5566332
//             ,"https://scontent.ftun9-1.fna.fbcdn.net/v/t39.30808-6/476973258_479698575190094_7338888870042912014_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=6ee11a&_nc_ohc=Pp-S5DxMB6QQ7kNvgEKM2Jr&_nc_oc=Adhd_oxVUY92NYtQRlxEJuEhDwnGxgC-UERiarqQqg-AW94Q-U9VeC33G6oJ7pq-N98&_nc_zt=23&_nc_ht=scontent.ftun9-1.fna&_nc_gid=A9soiCDa2_K54-OGoc4MCHe&oh=00_AYB7GVFjptoWsQueX5wBlz3EQpjI45NWz8Z9iQdhXTLv8A&oe=67B2CE8E"
//       ,"permis1526","3ans experience "));


//        Conducteur conducteurUpdate = new Conducteur(19, "xs", "xs", "ghofrane@gmail.com", "newpassword123", 12345678,
//                "https://scontent.ftun9-1.fna.fbcdn.net/v/t39.30808-6/476973258_479698575190094_7338888870042912014_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=6ee11a&_nc_ohc=Pp-S5DxMB6QQ7kNvgEKM2Jr&_nc_oc=Adhd_oxVUY92NYtQRlxEJuEhDwnGxgC-UERiarqQqg-AW94Q-U9VeC33G6oJ7pq-N98&_nc_zt=23&_nc_ht=scontent.ftun9-1.fna&_nc_gid=A9soiCDa2_K54-OGoc4MCHe&oh=00_AYB7GVFjptoWsQueX5wBlz3EQpjI45NWz8Z9iQdhXTLv8A&oe=67B2CE8E",
//                "permis1526", "5ans experience");
//        System.out.println(conducteurUpdate.getId_user());
//          sc.update(conducteurUpdate);

//          System.out.println(sc.getAll());

//         Conducteur c = sc.getById(2);
//         System.out.println(c);


//         Conducteur conducteurDelete = new Conducteur(21);
//         sc.delete(conducteurDelete);



    }
}
