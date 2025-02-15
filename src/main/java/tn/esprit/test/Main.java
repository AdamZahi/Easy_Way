package tn.esprit.test;

import tn.esprit.services.ServiceUser;
import tn.esprit.models.User;
import tn.esprit.models.Role;
import tn.esprit.services.reclamationService;
import tn.esprit.models.reclamations;
import tn.esprit.services.categorieService;
import tn.esprit.models.categories;

public class Main {
    public static void main(String[] args) {

        ServiceUser su = new ServiceUser();
        categorieService cs = new categorieService();
        reclamationService rs = new reclamationService();

        // Ajouter un utilisateur
        //su.add(new User(1, "Bennejma", "ines", "ines@gmail.com", "tayssir123456", 99556332, "https://example.com/image.jpg", Role.ADMINISTRATEUR));

        // Modifier un utilisateur existant
        // User userToUpdate = new User(3, "Mejri", "Eya", "eya.updated@gmail.com", "newpassword123", 99556332, "https://example.com/image.jpg", Role.ADMINISTRATEUR);
        // su.update(userToUpdate);

        // Supprimer un utilisateur
        // User userToDelete = new User(2, "Khalil", "Hlila", "khalilhlila@gmail.com", "kahlil89652", 5236987, "https://example.com/image.jpg", Role.PASSAGER);
        // su.delete(userToDelete);

        // Afficher tous les utilisateurs
        // System.out.println(su.getAll());

        // Afficher un utilisateur par ID
        // System.out.println(su.getById(2));


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Ajouter une réclamation
        int categorieId = 7; // ID de la catégorie choisie
        categories categorieChoisie = cs.getById(categorieId);
        if (categorieChoisie != null) {
            rs.add(new reclamations("sana@gmail.com", categorieChoisie, "Sujet de test", "Description de test", "En attente", "2024-02-11"));
        } else {
            System.out.println("Erreur : La catégorie avec l'ID " + categorieId + " n'existe pas.");
        }
        // Modifier une réclamation
        // rs.update(new reclamations(1, "tayssir@gmail.com", 2, "Sujet modifié", "Description modifiée", "Traitée", "2024-02-11"));

        // Supprimer une réclamation
        // rs.delete(new reclamations(4, "tayssir@gmail.com", 3, "Sujet supprimé", "Description supprimée", "Rejetée", "2024-02-11"));

        // Afficher toutes les réclamations
        //  System.out.println("Liste des réclamations:");
        // System.out.println(rs.getAll());

        // Afficher une réclamation par ID
        // System.out.println(rs.getById(3));


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Ajouter une catégorie
        // cs.add(new categories("Retard"));
        // cs.add(new categories("Problème de paiement"));
        // cs.add(new categories("Chauffeur impoli"));
        // cs.add(new categories("Propreté du véhicule"));
        // cs.add(new categories("Autre"));

        // Modifier une catégorie
        // cs.update(new categories(7, "Retard modifié"));

        // Supprimer une catégorie
        //cs.delete(new categories(7, "Retard modifié"));

        // Afficher toutes les catégories
        // System.out.println("Liste des catégories:");
        // System.out.println(cs.getAll());

        // Afficher une catégorie par ID
        // System.out.println(cs.getById(2));
    }
}
