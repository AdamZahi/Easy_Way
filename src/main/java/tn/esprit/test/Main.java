package tn.esprit.test;

import tn.esprit.services.ServicePosts;
import tn.esprit.services.ServiceCommentaire;
import tn.esprit.models.Posts;
import tn.esprit.models.Commentaire;

import java.sql.Date;

public class Main {
    public static void main(String[] args) {

        ServicePosts sp = new ServicePosts();
        ServiceCommentaire sc = new ServiceCommentaire();

        // Ajouter un post
        // Posts newPost = new Posts();
        // newPost.setVilleDepart("Tunis");
        // newPost.setVilleArrivee("Sfax");
        // newPost.setDate(java.sql.Date.valueOf("2025-02-15"));
        // newPost.setMessage("Covoiturage pour demain");
        // sp.add(newPost);
        // System.out.println("Post ajouté!");

        // Mettre à jour un post
        // Posts postToUpdate = new Posts();
        // postToUpdate.setId_post(1);  // Assurez-vous que l'ID du post est valide
        // postToUpdate.setId_user(3);  // Ajoutez un ID utilisateur valide
        // postToUpdate.setVilleDepart("Tunis");
        // postToUpdate.setVilleArrivee("Nabeul");
        // postToUpdate.setDate(java.sql.Date.valueOf("2025-02-15")); // Ajoutez une date valide
        // postToUpdate.setMessage("Message mis à jour");
        // sp.update(postToUpdate);

        // Supprimer un post
        // Posts postToDelete = new Posts();
        // postToDelete.setId_post(1);  // Assurez-vous que l'ID du post est valide
        // sp.delete(postToDelete);
        // System.out.println("Post supprimé!");

        // Récupérer tous les posts
        System.out.println("Liste des posts :");
        sp.getAll().forEach(post -> System.out.println(post));


        Commentaire newComment = new Commentaire();
        newComment.setId_post(1);
        newComment.setId_user(2);
        newComment.setContenu("C'est un commentaire test !");
        newComment.setDate_creat(new java.sql.Date(System.currentTimeMillis()));  // Date actuelle

        sc.add(newComment);
        System.out.println("Commentaire ajouté!");


        System.out.println("Liste des commentaires ");
        sc.getAll().forEach(commentaire -> System.out.println(commentaire));


        System.out.println(" Test de la mise à jour ");
        Commentaire commentaireToUpdate = new Commentaire();
        commentaireToUpdate.setId_com(1);
        commentaireToUpdate.setId_post(1);
        commentaireToUpdate.setId_user(1);
        commentaireToUpdate.setContenu("Commentaire mis à jour !");
        commentaireToUpdate.setDate_creat(new Date(System.currentTimeMillis()));


        sc.update(commentaireToUpdate);
        System.out.println("Commentaire mis à jour !");


        System.out.println("Liste des commentaires après mise à jour ");
        sc.getAll().forEach(commentaire -> System.out.println(commentaire));


        System.out.println("Test de suppression ");
        Commentaire commentaireToDelete = new Commentaire();
        commentaireToDelete.setId_com(1);
        sc.delete(commentaireToDelete);
        System.out.println("Commentaire supprimé !");


        System.out.println("Liste des commentaires après suppression ");
        sc.getAll().forEach(commentaire -> System.out.println(commentaire));
    }
}


