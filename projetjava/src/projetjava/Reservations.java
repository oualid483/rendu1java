package projetjava;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Reservations {
    private int id_reservation;
    private int id_user;
    private int id_event;
    private int id_salle;
    private int id_terrain;
    private String date_reservation;

    public Reservations() {
    }

    public Reservations(int id_user, int id_salle, int id_event, int id_terrain, String date_reservation) {
        this.id_user = id_user;
        this.id_salle = id_salle;
        this.id_event = id_event;
        this.id_terrain = id_terrain;
        this.date_reservation = date_reservation;
    }

    public Reservations(int id_reservation, int id_user, int id_salle, int id_event, int id_terrain, String date_reservation) {
        this.id_reservation = id_reservation;
        this.id_user = id_user;
        this.id_salle = id_salle;
        this.id_event = id_event;
        this.id_terrain = id_terrain;
        this.date_reservation = date_reservation;
    }

    public int getId_reservation() {
        return id_reservation;
    }

    public void setId_reservation(int id_reservation) {
        this.id_reservation = id_reservation;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public int getId_salle() {
        return id_salle;
    }

    public void setId_salle(int id_salle) {
        this.id_salle = id_salle;
    }

    public int getId_terrain() {
        return id_terrain;
    }

    public void setId_terrain(int id_terrain) {
        this.id_terrain = id_terrain;
    }

    public String getDate_reservation() {
        return date_reservation;
    }

    public void setDate_reservation(String date_reservation) {
        this.date_reservation = date_reservation;
    }

    public boolean verifierDisponibilite() {
        String sql = "SELECT COUNT(*) FROM reservations WHERE ((id_salle = ? OR id_terrain = ?) AND date_reservation = ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, this.id_salle);
            stmt.setInt(2, this.id_terrain);
            stmt.setString(3, this.date_reservation);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0; 
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vrification de la disponibilite : " + e.getMessage());
        }
        return false;
    }

    public void ajouterReservation() {
        if (!verifierDisponibilite()) {
            System.err.println("Erreur : La salle/terrain n'est pas disponible à cette date.");
            return; 
        }

        String checkUserSql = "SELECT COUNT(*) FROM utilisateurs WHERE id_user = ?";
        String insertSql = "INSERT INTO reservations (id_user, id_salle, id_terrain, date_reservation) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            try (PreparedStatement checkUserStmt = conn.prepareStatement(checkUserSql)) {
                checkUserStmt.setInt(1, this.id_user);
                try (ResultSet rs = checkUserStmt.executeQuery()) {
                    if (!rs.next() || rs.getInt(1) == 0) {
                        System.err.println("Erreur : L'utilisateur spécifié n'existe pas.");
                        return;
                    }
                }
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setInt(1, this.id_user);
                insertStmt.setInt(2, this.id_salle);
                insertStmt.setInt(3, this.id_terrain);
                insertStmt.setString(4, this.date_reservation);
                insertStmt.executeUpdate();
                System.out.println("Réservation ajoute avec succes.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de la reservation : " + e.getMessage());
        }
    }


    public static List<Reservations> afficherReservations() {
        List<Reservations> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Reservations reservation = new Reservations(
                        rs.getInt("id_reservation"),
                        rs.getInt("id_user"),
                        rs.getInt("id_salle"),
                        rs.getInt("id_event"),
                        rs.getInt("id_terrain"),
                        rs.getString("date_reservation")
                );
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'affichage des reservations : " + e.getMessage());
        }

        return reservations;
    }

    public void supprimerReservation(int id_reservation) {
        String sql = "DELETE FROM reservations WHERE id_reservation = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id_reservation);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Reservation supprimee avec succes.");
            } else {
                System.err.println("Aucune reservation trouve avec l'ID specifie.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la reservation : " + e.getMessage());
        }
    }

    
    public void modifierReservation() {
        String sql = "UPDATE reservations SET id_user = ?, id_salle = ?, id_event = ?, id_terrain = ?, date_reservation = ? WHERE id_reservation = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, this.id_user);
            stmt.setInt(2, this.id_salle);
            stmt.setInt(3, this.id_event);
            stmt.setInt(4, this.id_terrain);
            stmt.setString(5, this.date_reservation);
            stmt.setInt(6, this.id_reservation);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Reservation mise à jour avec succes.");
            } else {
                System.err.println("Aucune reservation trouvee avec l'ID specifie.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de la reservation : " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Reservations{" +
                "id_reservation=" + id_reservation +
                ", id_user=" + id_user +
                ", id_event=" + id_event +
                ", id_salle=" + id_salle +
                ", id_terrain=" + id_terrain +
                ", date_reservation='" + date_reservation + '\'' +
                '}';
    }
}
