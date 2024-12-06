package projetjava;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Evenements {
    private int id_event;
    private String nom_event;
    private String date_event;
    private String description;
    private int id_user;
    
    public Evenements() {}

    public Evenements(String nom, String date, String description, int userId) {
        this.nom_event = nom;
        this.date_event = date;
        this.description = description;
        this.id_user = userId;
    }

    public Evenements(int id, String nom, String date, String description, int userId) {
        this.id_event = id;
        this.nom_event = nom;
        this.date_event = date;
        this.description = description;
        this.id_user = userId;
    }

    public int getId() {
        return id_event;
    }

    public void setId(int id_event) {
        this.id_event = id_event;
    }

    public String getNom() {
        return nom_event;
    }

    public void setNom(String nom) {
        this.nom_event = nom;
    }

    public String getDate() {
        return date_event;
    }
    
    public void setDate(String date) {
    	this.date_event=date;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String desc) {
    	this.description=desc;
    }

    public int getUserId() {
        return id_user;
    }

    public void ajouterEvenement(Evenements event) {
        String checkUserSql = "SELECT COUNT(*) FROM utilisateurs WHERE id_user = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkUserSql)) {
            checkStmt.setInt(1, event.getUserId());
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {  
                    String sql = "INSERT INTO evenements (nom_event, date_event, description, id_user) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, event.getNom());
                        stmt.setString(2, event.getDate());
                        stmt.setString(3, event.getDescription());
                        stmt.setInt(4, event.getUserId());
                        stmt.executeUpdate();
                        System.out.println("evenement ajoute avec succes.");
                    }
                } else {
                    System.out.println("L'ID de l'utilisateur n'existe pas dans la base de donnees.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   
    public List<Evenements> afficheToutEvenements() {
        List<Evenements> events = new ArrayList<>();
        String sql = "SELECT * FROM evenements";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Evenements event = new Evenements(
                        rs.getInt("id_event"),
                        rs.getString("nom_event"),
                        rs.getString("date_event"),
                        rs.getString("description"),
                        rs.getInt("id_user")
                );
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    

    public void modiferEvenement(Evenements event) {
        String sql = "UPDATE evenements SET nom_event = ?, date_event = ?, description = ?, id_user = ? WHERE id_event = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, event.getNom());
            stmt.setString(2, event.getDate());
            stmt.setString(3, event.getDescription());
            stmt.setInt(4, event.getUserId());
            stmt.setInt(5, event.getId());
            stmt.executeUpdate();
            System.out.println("evenement mis e jour avec succes.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void supprimerEvenement(int id) {
        String sql = "DELETE FROM evenements WHERE id_event = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Évenement supprimreavec succes.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public String toString() {
        return "Événement [ID: " + id_event + ", Nom: " + nom_event + ", Date: " + date_event + 
               ", Description: " + description + ", ID Utilisateur: " + id_user + "]";
    }
    
}
