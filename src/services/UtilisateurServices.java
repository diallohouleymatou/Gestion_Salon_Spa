/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dao.IUtilisateur;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import models.Utilisateur;

/**
 *
 * @author HP
 */
public class UtilisateurServices implements IUtilisateur{
     private EntityManager em = EntityManagerUtil.getEMF().createEntityManager();

    @Override
    public List<Utilisateur> allUser() {
         Query query = em.createQuery("SELECT u FROM Utilisateur u");
        return query.getResultList();
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Utilisateur getUserByUsername(String Username) {
         Query query = em.createQuery("SELECT u FROM Utilisateur u WHERE u.username = :username");
        query.setParameter("username", Username);
        return (Utilisateur) query.getSingleResult();
       // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Utilisateur getUserById(int id) {
        Query query = em.createQuery("SELECT u FROM Utilisateur u WHERE u.id = :id");
        query.setParameter("id", id);
        return (Utilisateur) query.getSingleResult();
        
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public void saveUser(Utilisateur utilisateur) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(utilisateur); // Persiste l'utilisateur dans la base de données
            transaction.commit();
        } catch (Exception ex) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erreur lors de l'enregistrement de l'utilisateur", ex);
        }
    }
    
}
