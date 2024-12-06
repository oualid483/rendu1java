package projetjava;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Utilisateurs utilisateur1 = new Utilisateurs("Adam", "Elmernissi", "adam@gmail.com", "etudiant");
        Utilisateurs utilisateur2 = new Utilisateurs("hamza", "hdraf", "hamza@gmail.com", "professeur");
        Utilisateurs utilisateur3 = new Utilisateurs("oualid", "lahbal", "oualid@gmail.com", "etudiant");
        Utilisateurs utilisateur4 = new Utilisateurs("yahya", "elmhdder", "yahya@gmail.com", "professeur");
        
        Evenements evenement1 = new Evenements("Concert de Musique", "2024-12-15", "Concert de musique classique avec un orchestre", 19);
        Evenements evenement2 = new Evenements("Conférence Informatique", "2024-12-20", "Conférence sur l'avenir de l'IA", 20);
        
        
        Terrains terrain1 = new Terrains("Terrain A", "Football");
        Terrains terrain2 = new Terrains("Terrain B", "Basketball");
        Terrains terrain3 = new Terrains("Terrain C", "Tennis");
        Terrains terrain4 = new Terrains("Terrain D", "Rugby");
        
        Salles salle1 = new Salles("Salle A", 50);
        Salles salle2 = new Salles("Salle B", 100);

        utilisateur1.ajouterUtilisateur(utilisateur1);
        utilisateur2.ajouterUtilisateur(utilisateur2);
        utilisateur3.ajouterUtilisateur(utilisateur3);
        utilisateur4.ajouterUtilisateur(utilisateur4);

        Utilisateurs utilisateursObj = new Utilisateurs();
        List<Utilisateurs> utilisateurs = utilisateursObj.afficherTousUtilisateurs();
        System.out.println("Liste des utilisateurs :");
        for (Utilisateurs u : utilisateurs) {
            System.out.println(u);
        }

       int idUtilisateurASupprimer = 20;  
       utilisateursObj.supprimerParId(idUtilisateurASupprimer);

        utilisateurs = utilisateursObj.afficherTousUtilisateurs();
        System.out.println("\nListe des utilisateurs après suppression :");
        for (Utilisateurs u : utilisateurs) {
            System.out.println(u);
        }
        
        
        
      
        
       evenement1.ajouterEvenement(evenement1);
       evenement2.ajouterEvenement(evenement2);

        Evenements eventObj = new Evenements();
        List<Evenements> evenements = eventObj.afficheToutEvenements();
        System.out.println("Liste des evenements :");
        for (Evenements ev : evenements) {
            System.out.println(ev);
        }
        
        Evenements evenementModifie = new Evenements(1, "Concert de Musique Classique", "2024-12-16", "Concert de musique classique avec orchestre et chanteurs", 18);
        eventObj.modiferEvenement(evenementModifie);

        System.out.println("\nListe des evenements apres modification :");
        evenements = eventObj.afficheToutEvenements();
        for (Evenements ev : evenements) {
            System.out.println(ev);
        }
        
        
    
        
        salle1.ajoutSalle();
        salle2.ajoutSalle();

        System.out.println("Liste des salles apres ajout :");
        salle1.afficher();


        terrain1.ajoutTerrains();
        terrain2.ajoutTerrains();
        terrain3.ajoutTerrains();
        terrain4.ajoutTerrains();

        System.out.println("Affichage des terrains : ");
        terrain1.afficher();

       terrain1.supprimer(10);
        
        System.out.println("Affichage des terrains apres suppression : ");
        terrain1.afficher();
        
        Reservations reservation = new Reservations(28, 4, 3,6, "2024-12-06 10:00");
        reservation.ajouterReservation(); 
        System.out.println("\nTest d'ajout d'une reservation pour une date deja rserve");
        Reservations reservation2 = new Reservations(22, 4,0,24, "2024-12-06 12:00");
        reservation2.ajouterReservation(); 
    }
}
