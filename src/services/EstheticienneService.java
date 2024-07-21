package Services;

import dao.EstheticienneDao;
import java.util.List;
import javax.persistence.EntityManager;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import models.Estheticienne;
import services.EntityManagerUtil;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Ouly DIALLO
 */
public class EstheticienneService implements EstheticienneDao{

   private EntityManager em = EntityManagerUtil.getEMF().createEntityManager();

    @Override
    public List<Estheticienne> findByNom(String nom) {
        Query query = em.createQuery("SELECT e FROM Estheticienne e WHERE e.nom = :nom");
        query.setParameter("nom", nom);
        List<Estheticienne> estheticiennes = query.getResultList();
        //closeEntityManager();
        return estheticiennes;
    }

    @Override
    public List<Estheticienne> findBySpecialite(String specialite) {
        Query query = em.createQuery("SELECT e FROM Estheticienne e WHERE e.specialite = :specialite");
        query.setParameter("specialite", specialite);
        List<Estheticienne> estheticiennes = query.getResultList();
        //closeEntityManager();
        return estheticiennes;
    }

    public List<Estheticienne> getAllEstheticiennes() {
        Query query = em.createQuery("SELECT e FROM Estheticienne e");
        List<Estheticienne> estheticiennes = query.getResultList();
       // closeEntityManager();
        return estheticiennes;
    }

    
    public Estheticienne getEstheticienneById(Long id) {
        Estheticienne estheticienne = em.find(Estheticienne.class, id);
        //closeEntityManager();
        return estheticienne;
    }

   // @Override
    public void saveEstheticienne(Estheticienne estheticienne) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(estheticienne);
        transaction.commit();
       // closeEntityManager();
    }

   // @Override
    public void updateEstheticienne(Estheticienne estheticienne) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(estheticienne);
        transaction.commit();
        //closeEntityManager();
    }

   // @Override
    public void deleteEstheticienne(Long id) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Estheticienne estheticienne = em.find(Estheticienne.class, id);
        if (estheticienne != null) {
            em.remove(estheticienne);
        }
        transaction.commit();
       // closeEntityManager();
    }

    @Override
    public Estheticienne findById(int id) {
        Query query = em.createQuery("SELECT e FROM Estheticienne e WHERE e.id = :id");
        query.setParameter("id", id);
        Estheticienne estheticienne = (Estheticienne) query.getSingleResult();
        //closeEntityManager();
        return estheticienne;
       // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteById(int id) {
         Estheticienne estheticienne = findById(id);
        if (estheticienne != null) {
            em.getTransaction().begin();
            em.remove(estheticienne);
            em.getTransaction().commit();
        }
        
       // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void save(Estheticienne estheticienne) {
         em.getTransaction().begin();
        if (estheticienne.getId() == 0) {
            em.persist(estheticienne);
        } else {
            em.merge(estheticienne);
        }
        em.getTransaction().commit();
    }
        
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


