package projetjava;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Utilisateurs {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String type;

    public Utilisateurs() {
    }

    public Utilisateurs(int id, String nom, String prenom, String email, String type) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.type = type;
    }

    public Utilisateurs(String nom, String prenom, String email, String type) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
    public void ajouterUtilisateur(Utilisateurs utilisateur) {
        if (!utilisateur.getType().equals("etudiant") && !utilisateur.getType().equals("professeur")) {
            System.out.println("Erreur : Le type doit etre 'etudiant' ou 'professeur'.");
            return;
        }

        String sql = "INSERT INTO utilisateurs (nom, prenom, email, type) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, utilisateur.getNom());
            stmt.setString(2, utilisateur.getPrenom());
            stmt.setString(3, utilisateur.getEmail());
            stmt.setString(4, utilisateur.getType());  
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    utilisateur.setId(generatedKeys.getInt(1)); 
                }
            }

            System.out.println("Utilisateur ajoute avec succes.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    

    public List<Utilisateurs> afficherTousUtilisateurs() {
        List<Utilisateurs> utilisateurs = new ArrayList<>();
        String sql = "SELECT * FROM utilisateurs";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Utilisateurs utilisateur = new Utilisateurs(
                        rs.getInt("id_user"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("type") 
                );
                utilisateurs.add(utilisateur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateurs;
    }
    
    public void supprimerParId(int id) {
        String query = "DELETE FROM utilisateurs WHERE id_user = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Utilisateur est supprime");
        } catch (SQLException e) {
            System.err.println("Erreur de suppression de l'utilisateur  ");
        }
    }

    @Override
    public String toString() {
        return "Utilisateur{id=" + id + ", nom='" + nom + "', prenom='" + prenom + "', email='" + email + "', type='" + type + "'}";
    }
}
