package projetjava;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Salles {
    private int id_salle;
    private String nom_salle;
    private int capacite;

    public int getId_salle(){
        return id_salle;
    }
    public String getNom_salle(){
        return nom_salle;
    }
    public int getCapacite(){
        return capacite;
    }
    
    public void setId_salle(int id){
        id_salle =id;
    }
    public void setNom_salle(String nom_salle){
        this.nom_salle=nom_salle;
    }
    public void setCapacite(int capacite){
        this.capacite=capacite;
    }

    Salles(String nom_salle,int capacite){
        // setId_salle(id_salle);
        setNom_salle(nom_salle);
        setCapacite(capacite);
    }

    public void ajoutSalle() {
        String query = "insert into salles (nom_salle, capacite) values (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            
            stmt.setString(1, this.getNom_salle());
            stmt.setInt(2, this.getCapacite());
            
            stmt.executeUpdate();
            System.out.println("Salle est ajoute avec succes");
        } catch (SQLException e) {
            System.err.println("Erreur d'ajout de salle ");
        }
    }

    public void afficher() {
        String query = "select * from salles";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            var resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                int id_salle = resultSet.getInt("id_salle");
                String nom_salle = resultSet.getString("nom_salle");
                int capacite = resultSet.getInt("capacite");
    
                System.out.println("id de salle : " + id_salle + ", Nom de salle : " + nom_salle + ", capacite : " + capacite);
            }
        } catch (SQLException e) {
            System.err.println("erreur");
        }
    }
    
    public void supprimer(int id) {
        String query = "DELETE FROM salles WHERE id_salle = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("salle est supprime");
        } catch (SQLException e) {
            System.err.println("Erreur de suppression de salle  ");
        }
    }
}