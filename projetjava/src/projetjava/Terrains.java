package projetjava;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Terrains {


    private int id_terrain;
    private String nom_terrain;
    private String type;

    public int getId_salle(){
        return id_terrain;
    }
    public String getNom_salle(){
        return nom_terrain;
    }
    public String getCapacite(){
        return type;
    }
    
    public void setId_salle(int id){
        id_terrain =id;
    }
    private void setNom_salle(String nom_terrain){
        this.nom_terrain=nom_terrain;
    }
    private void setCapacite(String type){
        this.type=type;
    }

    Terrains(String nom_terrain,String type){
        // setId_salle(id_terrain);
        setNom_salle(nom_terrain);
        setCapacite(type);
    }
    
    public Terrains() {}
    
	public void ajoutTerrains() {
        String query = "insert into terrains (nom_terrain, type) values (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            
            stmt.setString(1, this.getNom_salle());
            stmt.setString(2, this.getCapacite());
            
            stmt.executeUpdate();
            System.out.println("terrain est ajoute avec succes");
        } catch (SQLException e) {
            System.err.println("Erreur d'ajout de terrain ");
        }
    }

    public void afficher() {
        String query = "SELECT * FROM terrains";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            var resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                int id_terrain = resultSet.getInt("id_terrain");
                String nom_terrain = resultSet.getString("nom_terrain");
                String type = resultSet.getString("type"); // Correction ici pour traiter "type" comme String

                System.out.println("ID de terrain : " + id_terrain + ", Nom de terrain : " + nom_terrain + ", Type : " + type);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'affichage des terrains : " + e.getMessage());
        }
    }

    public void supprimer(int id) {
        String query = "DELETE FROM terrains WHERE id_terrain = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("terrain est supprime");
        } catch (SQLException e) {
            System.err.println("Erreur de suppression de terrain  ");
        }
    }
}