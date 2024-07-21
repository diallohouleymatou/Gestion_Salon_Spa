package services;

import dao.Produitdao;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import javax.persistence.EntityTransaction;
import models.Produit;

public class ProduitService implements Produitdao {

   private EntityManager em = EntityManagerUtil.getEMF().createEntityManager();
    

    

    @Override
    public Produit findById(int id) {
        try {
            Query query = em.createNamedQuery("Produit.findById");
            query.setParameter("id", id);
            return (Produit) query.getSingleResult();
        } finally {
           // em.close();
        }
    }

    @Override
    public void save(Produit produit) {
         EntityTransaction transaction = em.getTransaction();
        try {
            em.getTransaction().begin();
            em.persist(produit);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
           // em.close();
        }
    }

    @Override
    public void update(Produit produit) {
         EntityTransaction transaction = em.getTransaction();
        
            em.getTransaction().begin();
            em.merge(produit);
            em.getTransaction().commit();
        
    }

    @Override
    public void delete(int id) {
         EntityTransaction transaction = em.getTransaction();
       
            em.getTransaction().begin();
            Produit produit = em.find(Produit.class, id);
            if (produit != null) {
                em.remove(produit);
                em.getTransaction().commit();
            }
       
    }

    @Override
    public List<Produit> findAll() {
        try {
            return em.createNamedQuery("Produit.findAll", Produit.class).getResultList();
        } finally {
           // em.close();
        }
    }
}
