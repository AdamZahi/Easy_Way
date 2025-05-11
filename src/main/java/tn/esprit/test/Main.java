package tn.esprit.test;

import tn.esprit.models.user.Admin;
import tn.esprit.services.user.ServiceAdmin;



public class Main {
    public static void main(String[] args) {
                Admin admin = new Admin();
                admin.setNom("Eya");
                admin.setPrenom("Ben Ali");
                admin.setEmail("eya.benali@example.com");
                admin.setMot_de_passe("password123");
                admin.setTelephonne(12345678);
                admin.setPhoto_profil(null); // image par défaut

                ServiceAdmin adminService = new ServiceAdmin();
                adminService.add(admin);
            }
        }


