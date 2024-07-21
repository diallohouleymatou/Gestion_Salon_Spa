/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.Date;
import java.util.List;
import models.Estheticienne;
import models.Rendezvous;

/**
 *
 * @author HP
 */
public interface RendezVousDao {
    Rendezvous findById(int id);

    void save(Rendezvous rendezVous);

    void update(int rendezvousId);

    void delete(int id);
    List<Rendezvous> findByDate(String date);

    List<Rendezvous> findAll();
    
    boolean checkExistingRendezVous(int clientId, int estheticienneId, Date date, Date heure);
    int countRendezVousByEstheticienne(int estheticienneId, Date date, Date heure);
     List<Rendezvous> findByEstheticienne( Estheticienne estheticienne);
     boolean confirmRendezVous(int rendezvousId);
     List<Rendezvous> findByEstheticienneIdentifiant(String identifiant);
}