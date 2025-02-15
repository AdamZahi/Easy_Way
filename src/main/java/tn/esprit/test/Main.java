package tn.esprit.test;

import tn.esprit.models.Events.Evenements;
import tn.esprit.models.Events.StatusEvenement;
import tn.esprit.models.Events.TypeEvenement;
import tn.esprit.models.user.Role;
import tn.esprit.models.user.User;
import tn.esprit.services.ServiceEvenement;
import tn.esprit.services.ServiceUser ;

import java.util.Date;

public class Main {
    public static void main(String[] args) {

        ServiceUser su = new ServiceUser();
        User newUser = new User(5,"Mzoughi","Chiraz","exmp@gmail.com","cm123456",99556332, "https://scontent.ftun9-1.fna.fbcdn.net/v/t39.30808-6/476973258_479698575190094_7338888870042912014_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=6ee11a&_nc_ohc=Pp-S5DxMB6QQ7kNvgEKM2Jr&_nc_oc=Adhd_oxVUY92NYtQRlxEJuEhDwnGxgC-UERiarqQqg-AW94Q-U9VeC33G6oJ7pq-N98&_nc_zt=23&_nc_ht=scontent.ftun9-1.fna&_nc_gid=A9soiCDa2_K54-OGoc4MCHe&oh=00_AYB7GVFjptoWsQueX5wBlz3EQpjI45NWz8Z9iQdhXTLv8A&oe=67B2CE8E" , Role.ADMINISTRATEUR);
        //su.add(newUser);
        //User userToUpdate = new User(3,"Mejri", "Eya", "eya.updated@gmail.com", "newpassword123", 99556332,"https://scontent.ftun9-1.fna.fbcdn.net/v/t39.30808-6/476973258_479698575190094_7338888870042912014_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=6ee11a&_nc_ohc=Pp-S5DxMB6QQ7kNvgEKM2Jr&_nc_oc=Adhd_oxVUY92NYtQRlxEJuEhDwnGxgC-UERiarqQqg-AW94Q-U9VeC33G6oJ7pq-N98&_nc_zt=23&_nc_ht=scontent.ftun9-1.fna&_nc_gid=A9soiCDa2_K54-OGoc4MCHe&oh=00_AYB7GVFjptoWsQueX5wBlz3EQpjI45NWz8Z9iQdhXTLv8A&oe=67B2CE8E" , Role.ADMINISTRATEUR);
        //su.update(userToUpdate);
        //User userToDelete = new User(2,"Khalil", "Hlila", "khalilhlila@gmail.com", "kahlil89652",5236987,"https://upload.wikimedia.org/wikipedia/commons/5/56/00_2103_Pinguin_-_Petermann_Island_%28Antarktische_Halbinsel%29.jpg" , Role.PASSAGER);
        //su.delete(userToDelete);

        //System.out.println(su.getAll());
        //User u = su.getById(2);
        //System.out.println(u);


        ServiceEvenement se = new ServiceEvenement();
        Evenements e = new Evenements(12,TypeEvenement.GREVE,1, "lol"  ,new java.sql.Date(2025,02,02),new java.sql.Date(2025,02,10), StatusEvenement.ANNULE);
        //se.add(e);
        //se.update(e);
        //System.out.println(se.getById(1));
        //se.delete(e);
        System.out.println(se.getAll());

    }

}
