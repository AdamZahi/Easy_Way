package tn.esprit.services.user;

import org.json.JSONArray;
import tn.esprit.interfaces.IService;
import tn.esprit.models.user.User;
import tn.esprit.models.user.User.Role;
import tn.esprit.util.MyDataBase;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class ServiceUser implements IService<User> {
    private Connection cnx;

    public ServiceUser() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void add(User user) {
        String query = "INSERT INTO `user`(`nom`, `prenom`, `email`, `password`, `telephonne`, `photo_profil`, `roles`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, user.getNom());
            pstm.setString(2, user.getPrenom());
            pstm.setString(3, user.getEmail());
            pstm.setString(4, user.getMot_de_passe());
            pstm.setInt(5, user.getTelephonne());
            pstm.setString(6, user.getPhoto_profil());

            // Convertir les rôles en JSON
            JSONArray rolesArray = new JSONArray();
            rolesArray.put(user.getRole().name());  // Si plusieurs rôles, ajoute-les ici
            pstm.setString(7, rolesArray.toString());

            int rowsAffected = pstm.executeUpdate();

            // Récupérer l'ID généré automatiquement
            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);  // ID généré
                user.setId_user(generatedId);  // Mets à jour l'objet utilisateur avec l'ID
            }

            if (rowsAffected > 0) {
                System.out.println("Utilisateur ajouté avec succès !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
        }
    }


    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM `user`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                User u = new User();
                u.setId_user(rs.getInt("id_user"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setEmail(rs.getString("email"));
                u.setMot_de_passe(rs.getString("password"));
                u.setTelephonne(rs.getInt("telephonne"));
                u.setPhoto_profil(rs.getString("photo_profil"));
                // Conversion de la chaîne en enum Role
                u.setRole(Role.valueOf(rs.getString("roles")));

                users.add(u);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    @Override
    public User getById(int id_user) {
        String query = "SELECT * FROM user WHERE id_user = ?";
        User user = null;

        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setInt(1, id_user);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("telephonne"),
                        rs.getString("photo_profil"),
                        Role.valueOf(rs.getString("roles")) // CORRECT : on passe un Role
                );
                user.setId_user(rs.getInt("id_user"));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'utilisateur : " + e.getMessage());
        }
        return user;
    }

    @Override
    public void update(User user) {
        String query = "UPDATE user SET nom = ?, prenom = ?, email = ?, password = ?, telephonne = ?, photo_profil = ?, roles = ? WHERE id_user = ?";

        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setString(1, user.getNom());
            pstm.setString(2, user.getPrenom());
            pstm.setString(3, user.getEmail());
            pstm.setString(4, user.getMot_de_passe());
            pstm.setInt(5, user.getTelephonne());
            pstm.setString(6, user.getPhoto_profil());
            pstm.setString(7, user.getRole().name());
            pstm.setInt(8, user.getId_user());

            int rowsUpdated = pstm.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("L'utilisateur avec ID " + user.getId_user() + " a été mis à jour.");
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID " + user.getId_user());
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de l'utilisateur : " + e.getMessage());
        }
    }

    @Override
    public void delete(User user) {
        String query = "DELETE FROM user WHERE id_user = ?";

        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setInt(1, user.getId_user());

            int rowsDeleted = pstm.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("L'utilisateur avec ID " + user.getId_user() + " a été supprimé.");
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID " + user.getId_user());
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
        }
    }

    // Méthode pour mettre à jour les rôles de l'utilisateur
    public boolean updateRoles(int id_user, List<String> newRoles) {
        String query = "UPDATE user SET roles = ? WHERE id_user = ?"; // `role` est de type string dans la BD

        try (PreparedStatement pstm = cnx.prepareStatement(query)) {
            // Convertir la liste des rôles en JSON (par exemple ["ROLE_USER", "ROLE_PASSAGER"])
            String rolesString = String.join(",", newRoles);  // Convertir la liste en chaîne séparée par des virgules

            pstm.setString(1, rolesString);  // Stocker les rôles sous forme de chaîne
            pstm.setInt(2, id_user);  // ID de l'utilisateur à mettre à jour

            int rowsUpdated = pstm.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Les rôles de l'utilisateur avec ID " + id_user + " ont été mis à jour.");
                return true;
            } else {
                System.out.println("Aucun utilisateur trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour des rôles : " + e.getMessage());
        }
        return false;
    }

    public User getUserByEmail(String email) {
        String query = "SELECT * FROM user WHERE email = ?";
        User user = null;

        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setString(1, email);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                // Nettoyage du champ roles (ex: ["ROLE_PASSAGER"])
                String rawRole = rs.getString("roles");
                String roleClean = rawRole.replace("[", "")
                        .replace("]", "")
                        .replace("\"", "")
                        .split(",")[0]
                        .trim();

                Role role = Role.valueOf(roleClean); // S'assure que c'est un rôle valide dans ton enum

                user = new User(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("telephonne"),
                        rs.getString("photo_profil"),
                        role
                );

                // Si ton User a un ID, pense à le définir aussi :
                user.setId_user(rs.getInt("id_user"));
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL lors de la récupération de l'utilisateur : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Rôle inconnu ou mal formé : " + e.getMessage());
        }

        return user;
    }



    // Mise à jour du mot de passe de l'utilisateur avec un ID
    public boolean updatePassword(int id_user, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt()); // Hachage du mot de passe
        String query = "UPDATE user SET password = ? WHERE id_user = ?";

        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setString(1, hashedPassword);
            stmt.setInt(2, id_user);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Mot de passe mis à jour pour l'utilisateur avec ID: " + id_user);
            } else {
                System.out.println("Aucune mise à jour effectuée. ID utilisateur incorrect ?");
            }

            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Erreur SQL lors de la mise à jour du mot de passe:");
            e.printStackTrace();
            return false;
        }
    }

    // Récupérer l'ID d'un utilisateur par son email
    public int getUserIdByEmail(String email) {
        System.out.println("Recherche de l'ID pour l'email : " + email);

        String query = "SELECT id_user FROM user WHERE email = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(query)) {
            pstm.setString(1, email);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id_user");
                System.out.println("ID trouvé : " + id);
                return id;
            } else {
                System.out.println("Aucun utilisateur trouvé avec cet email.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
        return -1;
    }

    // Récupérer l'ID du dernier utilisateur inséré
    public int getLastInsertedId() {
        int id = -1;
        try (Connection cnx = MyDataBase.getInstance().getCnx();
             PreparedStatement ps = cnx.prepareStatement("SELECT id_user FROM user ORDER BY id_user DESC LIMIT 1");
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                id = rs.getInt("id_user");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
