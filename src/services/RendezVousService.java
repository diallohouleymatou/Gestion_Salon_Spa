/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dao.RendezVousDao;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import models.Estheticienne;
import models.Rendezvous;

/**
 *
 * @author HP
 */
public class RendezVousService  implements RendezVousDao{
    private EntityManager em = EntityManagerUtil.getEMF().createEntityManager();


    public Rendezvous findById(int id) {
        TypedQuery<Rendezvous> query = em.createQuery("SELECT r FROM Rendezvous r WHERE r.id = :id", Rendezvous.class);
    query.setParameter("id", id);
    Rendezvous rendezvous = query.getSingleResult();
    return rendezvous;
        
        // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void save(Rendezvous rendezVous) {
         EntityTransaction transaction = em.getTransaction();
        em.getTransaction().begin();
        em.persist(rendezVous);
        em.getTransaction().commit();
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(int rendezvousId) {
        
        EntityTransaction transaction = em.getTransaction();
    transaction.begin();
    
    Rendezvous existingRendezvous = em.find(Rendezvous.class, rendezvousId);
    if (existingRendezvous != null) {
        em.merge(existingRendezvous);
        transaction.commit();
    } else {
        System.out.println("Rendez-vous avec l'ID " + rendezvousId + " non trouvé.");
    }
    
    //em.close();
        
       // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

    @Override
    public void delete(int rendezvousId) {
         
       EntityTransaction transaction = em.getTransaction();
    transaction.begin();
    
   Rendezvous rendezvous = em.find(Rendezvous.class, rendezvousId);
    if (rendezvous != null) {
        em.remove(rendezvous); // Supprime l'entité du contexte de persistance
        transaction.commit(); // Valide la transaction
    } else {
        System.out.println("Rendez-vous avec l'ID " + rendezvousId + " non trouvé.");
    }
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Rendezvous> findByDate(String date) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Rendezvous> findAll() {
   EntityTransaction transaction = em.getTransaction();
   String jpql = "SELECT r FROM Rendezvous r";
    Query query = em.createQuery(jpql);  // Utilisation de Query au lieu de TypedQuery

    List<Rendezvous> rendezvousList = query.getResultList();

    return rendezvousList;
    
    
   
  
    
       // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean checkExistingRendezVous(int clientId, int estheticienneId, Date date, Date heure) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(r) FROM Rendezvous r " +
                        "WHERE r.idClient.id = :clientId " +
                        "AND r.idEstheticienne.id = :estheticienneId " +
                        "AND r.date = :date " +
                        "AND r.heure = :heure",
                Long.class);
        query.setParameter("clientId", clientId);
        query.setParameter("estheticienneId", estheticienneId);
        query.setParameter("date", date);
        query.setParameter("heure", heure);

        return query.getSingleResult() > 0;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int countRendezVousByEstheticienne(int estheticienneId, Date date, Date heure) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(r) FROM Rendezvous r " +
                        "WHERE r.idEstheticienne.id = :estheticienneId " +
                        "AND r.date = :date " +
                        "AND r.heure = :heure",
                Long.class);
        query.setParameter("estheticienneId", estheticienneId);
        query.setParameter("date", date);
        query.setParameter("heure", heure);

        return query.getSingleResult().intValue();
        
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Rendezvous> findByEstheticienne(Estheticienne estheticienne) {
         String jpql = "SELECT r FROM Rendezvous r WHERE r.estheticienne = :estheticienne";
        TypedQuery<Rendezvous> query = em.createQuery(jpql, Rendezvous.class);
        query.setParameter("estheticienne", estheticienne);
        return query.getResultList();
        
        
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean confirmRendezVous(int rendezvousId) {
         EntityTransaction transaction = em.getTransaction();
    transaction.begin();

    Rendezvous existingRendezvous = em.find(Rendezvous.class, rendezvousId);
    if (existingRendezvous != null) {
        existingRendezvous.setConfirme(true); // Met à jour le statut de confirmation
        em.merge(existingRendezvous); // Fusionne l'entité mise à jour dans le contexte de persistance
        transaction.commit(); // Valide la transaction
        return true; // Confirmed successfully
    } else {
        System.out.println("Rendez-vous avec l'ID " + rendezvousId + " non trouvé.");
        transaction.rollback(); // Rollback la transaction en cas d'erreur
        return false; // Rendez-vous not found
    }
        
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Rendezvous> findByEstheticienneIdentifiant(String identifiant) {
        EntityTransaction transaction = em.getTransaction();
       
        EntityTransaction tx = null;
        List<Rendezvous> result = null;

        try {
            tx = em.getTransaction();
            tx.begin();

            Query query = em.createQuery("SELECT r FROM Rendezvous r WHERE r.idEstheticienne.identifiant = :identifiant");
            query.setParameter("identifiant", identifiant);
            result = query.getResultList();

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            //em.close();
        }

        return result;
    

        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    
    

   
    
}
