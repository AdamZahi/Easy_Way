package tn.esprit.test;

import tn.esprit.services.ServiceUser ;
import tn.esprit.models.User;

public class Main {
    public static void main(String[] args) {

        ServiceUser su = new ServiceUser();
        su.add(new User("Mejri","Eya","mejrieya384@gmail.com","eya123456",99556332, "https://scontent.ftun9-1.fna.fbcdn.net/v/t39.30808-6/476973258_479698575190094_7338888870042912014_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=6ee11a&_nc_ohc=Pp-S5DxMB6QQ7kNvgEKM2Jr&_nc_oc=Adhd_oxVUY92NYtQRlxEJuEhDwnGxgC-UERiarqQqg-AW94Q-U9VeC33G6oJ7pq-N98&_nc_zt=23&_nc_ht=scontent.ftun9-1.fna&_nc_gid=A9soiCDa2_K54-OGoc4MCHe&oh=00_AYB7GVFjptoWsQueX5wBlz3EQpjI45NWz8Z9iQdhXTLv8A&oe=67B2CE8E"));
        //User userToUpdate = new User(3,"Mejri", "Eya", "eya.updated@gmail.com", "newpassword123", 99556332 ,"https://scontent.ftun9-1.fna.fbcdn.net/v/t39.30808-6/476973258_479698575190094_7338888870042912014_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=6ee11a&_nc_ohc=Pp-S5DxMB6QQ7kNvgEKM2Jr&_nc_oc=Adhd_oxVUY92NYtQRlxEJuEhDwnGxgC-UERiarqQqg-AW94Q-U9VeC33G6oJ7pq-N98&_nc_zt=23&_nc_ht=scontent.ftun9-1.fna&_nc_gid=A9soiCDa2_K54-OGoc4MCHe&oh=00_AYB7GVFjptoWsQueX5wBlz3EQpjI45NWz8Z9iQdhXTLv8A&oe=67B2CE8E" );
       //su.update(userToUpdate);
        //User userToDelete = new User(1,"Mejri", "Eya", "mejrieya384@gmail.com", "eya123456",99556332);
        //su.delete(userToDelete);

        System.out.println(su.getAll());
        //User u = su.getById(2);
        //System.out.println(u);


    }

}
